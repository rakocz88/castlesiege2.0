package com.pilaf.cs.users.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "csuser")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	private Long id;

	@Column(name = "USERNAME", length = 50, unique = true)
	@NotNull
	@Size(min = 4, max = 50)
	private String username;

	@Column(name = "PASSWORD", length = 255)
	@NotNull
	@Size(min = 4, max = 255)
	private String password;

	@Column(name = "FIRSTNAME", length = 50)
	@Size(min = 4, max = 50)
	private String firstname;

	@Column(name = "LASTNAME", length = 50)
	@Size(min = 4, max = 50)
	private String lastname;

	@Column(name = "EMAIL", length = 50, unique = true)
	@Size(min = 4, max = 50)
	private String email;

	@Column(name = "ENABLED")
	private Boolean enabled;

	@Column(name = "LASTPASSWORDRESETDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastPasswordResetDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_AUTHORITY", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
	private List<Authority> authorities;

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, List<Authority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

}
