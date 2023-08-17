package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.model.Country;
import co.za.mamamoney.ussd.repository.CountryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country getCountryByName(String name) {
        return countryRepository.findCountryByName(name);
    }

    public Country getCountryById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        return country.orElse(null);
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
}
