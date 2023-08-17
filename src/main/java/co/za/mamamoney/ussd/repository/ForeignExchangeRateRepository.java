package co.za.mamamoney.ussd.repository;

import co.za.mamamoney.ussd.model.ForeignExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForeignExchangeRateRepository extends JpaRepository<ForeignExchangeRate, Long> {
    ForeignExchangeRate findForeignExchangeRateByDestinationCurrency(String currency);
}
