package co.za.mamamoney.ussd.service;

import co.za.mamamoney.ussd.model.Prompt;
import co.za.mamamoney.ussd.repository.PromptRepository;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
    private final PromptRepository promptsRepository;

    public PromptService(PromptRepository promptsRepository) {
        this.promptsRepository = promptsRepository;
    }

    public Prompt getPrompt(String name) {
        return promptsRepository.findPromptByName(name);
    }
}
