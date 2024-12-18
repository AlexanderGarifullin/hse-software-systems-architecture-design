package com.cf.cfteam.test.entities;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tasks"})
@Entity
@Table(name = "tests", schema = "my")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input", nullable = false)
    private String input;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "test_task", schema = "my",
            joinColumns = @JoinColumn(name = "test_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private List<Task> tasks;
}
