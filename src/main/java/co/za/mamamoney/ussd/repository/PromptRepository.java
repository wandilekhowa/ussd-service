package co.za.mamamoney.ussd.repository;

import co.za.mamamoney.ussd.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Prompt findPromptByName(String name);
}
