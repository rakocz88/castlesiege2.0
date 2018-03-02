package com.pilaf.cs.users.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(indexName = "user")
public class ESUser {
	
	@Id
	private Long id;

	private String username;


	private String firstname;

	private String lastname;

	private String email;

	private Boolean enabled;
	
	public ESUser(User user){
		this.id = user.getId();
		this.username = user.getUsername();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.enabled = user.getEnabled();
	}

}
