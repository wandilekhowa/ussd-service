package co.za.mamamoney.ussd.model;

import co.za.mamamoney.ussd.common.PromptResponseConvertor;
import co.za.mamamoney.ussd.dto.PromptResponse;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_external_id")
    private String sessionId;

    @Column private String msisdn;

    @Column(name = "prompt_response", columnDefinition = "json")
    @Convert(converter = PromptResponseConvertor.class)
    private PromptResponse promptResponse;

    @CreationTimestamp
    @Column(
            name = "create_date",
            columnDefinition = "TIMESTAMP",
            nullable = false,
            updatable = false,
            insertable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(
            name = "last_modified_date",
            columnDefinition = "TIMESTAMP",
            nullable = false,
            updatable = false,
            insertable = false)
    private LocalDateTime lastModifiedDate;

    @Column private String status;
}
