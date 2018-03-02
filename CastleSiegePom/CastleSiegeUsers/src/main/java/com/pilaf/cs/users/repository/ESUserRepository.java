package com.pilaf.cs.users.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.pilaf.cs.users.model.ESUser;

public interface ESUserRepository extends ElasticsearchCrudRepository<ESUser, Long>{
	
	ESUser findByUsername(String username);

}
