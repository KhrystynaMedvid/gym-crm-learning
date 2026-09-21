package com.gym.crm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"trainee", "trainer", "trainingType"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainee_id", nullable = false)
    @NotNull
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    @NotNull
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "training_type_id", nullable = false)
    @NotNull
    private TrainingType trainingType;

    @Column(name = "training_name", nullable = false)
    @NotBlank
    private String trainingName;

    @Column(name = "training_date", nullable = false)
    @NotNull
    private LocalDate trainingDate;

    @Column(name = "training_duration", nullable = false)
    @NotNull
    @Positive
    private Integer trainingDuration;
}
