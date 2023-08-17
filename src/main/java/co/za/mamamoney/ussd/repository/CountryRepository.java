package co.za.mamamoney.ussd.repository;

import co.za.mamamoney.ussd.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findCountryByName(String name);
}
