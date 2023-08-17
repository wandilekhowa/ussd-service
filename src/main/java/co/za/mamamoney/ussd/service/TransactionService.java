package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.common.TransactionStatus;
import co.za.mamamoney.ussd.model.Country;
import co.za.mamamoney.ussd.model.ForeignExchangeRate;
import co.za.mamamoney.ussd.model.Session;
import co.za.mamamoney.ussd.model.Transaction;
import co.za.mamamoney.ussd.repository.TransactionRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ForeignExchangeService foreignExchangeService;

    public TransactionService(
            TransactionRepository transactionRepository,
            ForeignExchangeService foreignExchangeService) {
        this.transactionRepository = transactionRepository;
        this.foreignExchangeService = foreignExchangeService;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void recordTransaction(Session session, Country country) {
        final BigDecimal amount = session.getPromptResponse().getAmount();
        ForeignExchangeRate rate =
                foreignExchangeService.getForeignExchangeRate(country.getCurrency());

        BigDecimal finalAmount = amount.multiply(rate.getRate());
        String SOURCE_CURRENCY = "ZAR";
        Transaction transaction =
                Transaction.builder()
                        .sessionId(session.getId())
                        .amount(finalAmount)
                        .country(session.getPromptResponse().getCountry())
                        .sourceMsisdn(session.getMsisdn())
                        .sourceCurrency(SOURCE_CURRENCY)
                        .destinationCurrency(country.getCurrency())
                        .status(TransactionStatus.PAID.toString())
                        .build();

        saveTransaction(transaction);
    }
}
