package com.vbt.kanban.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    private String comment;

    @NotBlank
    @NotNull
    @OneToOne
    private Account commentOwner;

    @Column(name="comment_date")
    @NotBlank
    @NotNull
    private Date at;

    @ManyToOne
    @JoinColumn(name="task_id")
    @NotBlank
    @NotNull
    @JsonBackReference
    private Task task;
}
