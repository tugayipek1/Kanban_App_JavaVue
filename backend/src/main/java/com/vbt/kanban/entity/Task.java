package com.vbt.kanban.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@NotNull
	private String title;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;
	
	@ManyToOne()
	@JoinColumn(name="project")
	@NotBlank
	@NotNull
	private Project project;


	@ManyToOne()
	@JoinColumn(name="assignee")
	@NotBlank
	@NotNull
	private Account assignee;

	@OneToOne()
	@NotBlank
	@NotNull
	private Account lastUpdaterPerson;

	@ManyToOne()
	@NotBlank
	@NotNull
	private Account reporter;

	@Enumerated(EnumType.STRING)
	@NotBlank
	@NotNull
	private TaskPriority priority;

	@Enumerated(EnumType.STRING)
	@NotBlank
	@NotNull
	private TaskIssueType issueType;
	
	@Column(name="created_date", updatable = false)
	@NotBlank
	@NotNull
	private LocalDateTime createdDate;
	
	@Column(name="due_date")
	@NotBlank
	@NotNull
	private LocalDate dueDate;

	@NotBlank
	@NotNull
	private String description;

	@OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@ElementCollection
	@OrderBy("at DESC")
	private List<Comment> comments;
}
