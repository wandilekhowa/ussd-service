package co.za.mamamoney.ussd.model;

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
@Table(name = "prompt")
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String name;
    @Column private String message;

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
