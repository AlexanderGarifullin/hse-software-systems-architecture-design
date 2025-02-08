package hse_paps_code.paps.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "parameters", schema = "test_generation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private Integer minValue;
    private Integer maxValue;
    private Integer length;
    private String description;

    @Column(nullable = false)
    private Boolean isInput;

    @Column(nullable = false)
    private Integer orderIndex;

    @Column(nullable = false)
    private Boolean newLine;
}
