package co.za.mamamoney.ussd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UssdRequest {
    private String sessionId;
    private String msisdn;
    private String userEntry;
}
