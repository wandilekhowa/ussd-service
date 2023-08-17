package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.common.PromptsName;
import co.za.mamamoney.ussd.common.Status;
import co.za.mamamoney.ussd.dto.PromptResponse;
import co.za.mamamoney.ussd.dto.UssdRequest;
import co.za.mamamoney.ussd.dto.UssdResponse;
import co.za.mamamoney.ussd.model.*;
import co.za.mamamoney.ussd.util.AmountChecker;
import co.za.mamamoney.ussd.util.SessionChecker;
import co.za.mamamoney.ussd.util.UssdHandlerHelper;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UssdHandlerService {
    private final SessionService sessionService;
    private final TransactionService transactionService;
    private final ForeignExchangeService foreignExchangeService;
    private final PromptService promptService;
    private final CountryService countryService;

    public UssdHandlerService(
            SessionService sessionService,
            TransactionService transactionService,
            ForeignExchangeService foreignExchangeService,
            PromptService promptService,
            CountryService countryService) {
        this.sessionService = sessionService;
        this.transactionService = transactionService;
        this.foreignExchangeService = foreignExchangeService;
        this.promptService = promptService;
        this.countryService = countryService;
    }

    public UssdResponse handleSession(UssdRequest ussdRequest) {
        Session activeSession = sessionService.getSession(ussdRequest.getSessionId());
        Prompt prompt = promptService.getPrompt(PromptsName.COUNTRY);

        if (ussdRequest.getUserEntry() == null && activeSession == null) {

            PromptResponse promptResponse = new PromptResponse();
            activeSession =
                    Session.builder()
                            .sessionId(ussdRequest.getSessionId())
                            .msisdn(ussdRequest.getMsisdn())
                            .status(Status.ACTIVE.toString())
                            .promptResponse(promptResponse)
                            .build();

            sessionService.saveSession(activeSession);

            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else if (ussdRequest.getUserEntry() == null
                && activeSession.getPromptResponse() != null
                && activeSession.getStatus().equals(Status.ACTIVE.toString())) {
            return handleInSession(ussdRequest, activeSession);
        } else if (ussdRequest.getUserEntry() != null && activeSession == null) {
            prompt = promptService.getPrompt(PromptsName.ERROR);
            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else {
            return handleInSession(ussdRequest, activeSession);
        }
    }

    private UssdResponse handleInSession(UssdRequest ussdRequest, Session activeSession) {
        Prompt prompt;
        if (activeSession != null && SessionChecker.isSessionExpired(activeSession)) {
            activeSession.setStatus(Status.EXPIRED.toString());
            sessionService.saveSession(activeSession);

            prompt = promptService.getPrompt(PromptsName.ERROR);
            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else if (activeSession.getPromptResponse().getCountry() == null) {
            if (ussdRequest.getUserEntry() == null) {
                prompt = promptService.getPrompt(PromptsName.COUNTRY);
                return UssdHandlerHelper.printResponse(prompt, ussdRequest);
            }
            Country country =
                    countryService.getCountryById(Long.valueOf(ussdRequest.getUserEntry()));
            if (country == null) {
                log.error(
                        "No country with id: {} could be found in the database.",
                        ussdRequest.getUserEntry());

                prompt = promptService.getPrompt(PromptsName.COUNTRY);
                return UssdHandlerHelper.printResponse(prompt, ussdRequest);
            }

            activeSession.getPromptResponse().setCountry(country.getName());
            sessionService.saveSession(activeSession);
            prompt = promptService.getPrompt(PromptsName.AMOUNT);
            UssdHandlerHelper.setAmountMessage(prompt, activeSession);

            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else if (activeSession.getPromptResponse().getAmount() == null) {
            if (ussdRequest.getUserEntry() == null) {
                prompt = promptService.getPrompt(PromptsName.AMOUNT);
                UssdHandlerHelper.setAmountMessage(prompt, activeSession);

                return UssdHandlerHelper.printResponse(prompt, ussdRequest);
            }

            if (AmountChecker.isInvalidAmount(
                    BigDecimal.valueOf(Long.parseLong(ussdRequest.getUserEntry())))) {
                prompt = promptService.getPrompt(PromptsName.AMOUNT);
                log.info("Country name: {}", activeSession.getPromptResponse().getCountry());
                UssdHandlerHelper.setAmountMessage(prompt, activeSession);

                return UssdHandlerHelper.printResponse(prompt, ussdRequest);
            }

            Country country =
                    countryService.getCountryByName(activeSession.getPromptResponse().getCountry());
            ForeignExchangeRate rate =
                    foreignExchangeService.getForeignExchangeRate(country.getCurrency());

            BigDecimal amount = BigDecimal.valueOf(Long.parseLong(ussdRequest.getUserEntry()));

            BigDecimal finalAmount = amount.multiply(rate.getRate());
            activeSession.getPromptResponse().setAmount(finalAmount);
            sessionService.saveSession(activeSession);

            prompt = promptService.getPrompt(PromptsName.CONFIRMATION);
            UssdHandlerHelper.setConfirmationMessage(prompt, activeSession, country.getCurrency());

            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else if (activeSession.getPromptResponse().getConfirmation() == null) {
            Country country =
                    countryService.getCountryByName(activeSession.getPromptResponse().getCountry());
            if (ussdRequest.getUserEntry() == null) {
                prompt = promptService.getPrompt(PromptsName.CONFIRMATION);
                UssdHandlerHelper.setConfirmationMessage(
                        prompt, activeSession, country.getCurrency());

                return UssdHandlerHelper.printResponse(prompt, ussdRequest);
            }
            activeSession.getPromptResponse().setConfirmation("OK");
            activeSession.setStatus(Status.COMPLETED.toString());
            sessionService.saveSession(activeSession);

            transactionService.recordTransaction(activeSession, country);

            prompt = promptService.getPrompt(PromptsName.END);
            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        } else {
            prompt = promptService.getPrompt(PromptsName.ERROR);
            return UssdHandlerHelper.printResponse(prompt, ussdRequest);
        }
    }
}
