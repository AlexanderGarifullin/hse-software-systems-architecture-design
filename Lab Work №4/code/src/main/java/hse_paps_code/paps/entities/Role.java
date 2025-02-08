package hse_paps_code.paps.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles", schema = "test_generation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String role;
}
