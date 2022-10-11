package com.expastudios.blogweb.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	  name = "UUID",
	  strategy = "org.hibernate.id.UUIDGenerator",
	  parameters = {
		@org.hibernate.annotations.Parameter(
		  name = "uuid_gen_strategy_class",
		  value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
		)
	  }
	)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@NotNull
	@Size(max = 128)
	@Column(length = 128)
	private String title;

	private boolean published;

	private LocalDateTime createdAt;

	private LocalDateTime publishedAt;

	@NotNull
	@Size(max = 1024)
	@Column(length = 1024)
	private String content;
}
