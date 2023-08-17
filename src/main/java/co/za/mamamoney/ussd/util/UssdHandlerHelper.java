package co.za.mamamoney.ussd.util;

import co.za.mamamoney.ussd.dto.UssdRequest;
import co.za.mamamoney.ussd.dto.UssdResponse;
import co.za.mamamoney.ussd.model.*;

public class UssdHandlerHelper {

    public static UssdResponse printResponse(Prompt prompt, UssdRequest ussdRequest) {
        return UssdResponse.builder()
                .sessionId(ussdRequest.getSessionId())
                .message(prompt.getMessage())
                .build();
    }

    public static void setConfirmationMessage(
            Prompt prompt, Session activeSession, String currency) {
        prompt.setMessage(
                prompt.getMessage()
                        .replace(
                                "<Amount>",
                                activeSession.getPromptResponse().getAmount().toString())
                        .replace("<ForeignCurrencyCode>", currency));
    }

    public static void setAmountMessage(Prompt prompt, Session activeSession) {
        prompt.setMessage(
                prompt.getMessage()
                        .replace("<CountryName>", activeSession.getPromptResponse().getCountry()));
    }
}
