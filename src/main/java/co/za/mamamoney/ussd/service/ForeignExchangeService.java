package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.model.ForeignExchangeRate;
import co.za.mamamoney.ussd.repository.ForeignExchangeRateRepository;
import org.springframework.stereotype.Service;

@Service
public class ForeignExchangeService {
    private final ForeignExchangeRateRepository foreignExchangeRateRepository;

    public ForeignExchangeService(ForeignExchangeRateRepository foreignExchangeRateRepository) {
        this.foreignExchangeRateRepository = foreignExchangeRateRepository;
    }

    public ForeignExchangeRate getForeignExchangeRate(String currency) {
        return foreignExchangeRateRepository.findForeignExchangeRateByDestinationCurrency(currency);
    }
}
