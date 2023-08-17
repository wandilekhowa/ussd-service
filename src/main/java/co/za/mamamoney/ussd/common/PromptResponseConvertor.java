package co.za.mamamoney.ussd.common;

import co.za.mamamoney.ussd.dto.PromptResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class PromptResponseConvertor implements AttributeConverter<PromptResponse, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PromptResponse promptResponse) {
        try {
            return objectMapper.writeValueAsString(promptResponse);
        } catch (JsonProcessingException ex) {
            log.error("Failed to convert PromptResponse to json string.", ex);
            return null;
        }
    }

    @Override
    public PromptResponse convertToEntityAttribute(String jsonPromptResponses) {
        try {
            return objectMapper.readValue(jsonPromptResponses, PromptResponse.class);
        } catch (IOException ex) {
            log.error("Failed to convert json string to PromptResponse.", ex);
            return null;
        }
    }
}
