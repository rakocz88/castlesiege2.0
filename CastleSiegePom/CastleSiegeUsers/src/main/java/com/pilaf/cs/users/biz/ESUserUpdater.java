package com.pilaf.cs.users.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pilaf.cs.users.model.ESUser;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.ESUserRepository;

@Component
public class ESUserUpdater {

	@Autowired
	private ESUserRepository esUserRepository;

	public void updateUser(User user) {
		ESUser esUser = esUserRepository.findOne(user.getId());
		updateAllFieldsOnUser(esUser, user);
	}

	private void updateAllFieldsOnUser(ESUser esUser, User user) {
		esUser = new ESUser(user);
		esUserRepository.save(esUser);
	}

}
