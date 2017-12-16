package com.pilaf.cs.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AUTHORITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Authority {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_seq")
	@SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq", allocationSize = 1)
	private Long id;

	@Column(name = "NAME", length = 50)
	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthorityName name;

	public Authority(AuthorityName name) {
		super();
		this.name = name;
	}

}