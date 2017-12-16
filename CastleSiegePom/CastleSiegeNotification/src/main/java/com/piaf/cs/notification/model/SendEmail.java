package com.piaf.cs.notification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SEND_EMAIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SendEmail {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long id;

	@Column(name = "EMAIL")
	@NotNull
	@Size(min = 4, max = 50)
	private String email;

	@Column(name = "MESSAGE")
	@NotNull
	@Size(min = 4, max = 255)
	private String message;

	@Column(name = "TIMESTAMP")
	private Timestamp timestamp;

	public SendEmail(String username, String message) {
		super();
		this.email = username;
		this.message = message;
	}

}
