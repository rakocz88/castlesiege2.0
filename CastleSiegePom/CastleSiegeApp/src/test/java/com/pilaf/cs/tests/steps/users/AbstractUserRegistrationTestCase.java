package com.pilaf.cs.tests.steps.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.pilaf.cs.notification.biz.EmailBiz;
import com.pilaf.cs.tests.SpringIntegrationTest;
import com.pilaf.cs.tests.builder.UserRegistrationTestState;
import com.pilaf.cs.tests.helper.LoginTestHelper;
import com.pilaf.cs.tests.helper.RestEndpoints;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

public class AbstractUserRegistrationTestCase extends SpringIntegrationTest implements RestEndpoints {

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected EmailBiz emailBiz;

	@Autowired
	protected TestRestTemplate testRestTemplate;

	@Autowired
	protected LoginTestHelper loginTestHelper;

	protected void registerUser(String login, String password, String firstname, String surname, String email) {
		User user = new User(login, password);
		user.setEmail(email);
		user.setLastname(surname);
		user.setFirstname(firstname);
		UserRegistrationTestState.getInstance().setReturnedUser(user);
		String url = String.format(REGISTRATION_ENDPOINT, port);
		ResponseEntity<String> response = testRestTemplate.postForEntity(url, user, String.class);
		UserRegistrationTestState.getInstance().setCurrentHttpStatus(response.getStatusCodeValue());
	}

	protected void clickTheLinkThatWasSendOnEmail(String email) throws InterruptedException {
		String activationLink = emailBiz.getLastEmailForUser(email).getMessage();
		String path = String.format(ACTIVATION_ENDPOINT, port, activationLink);
		ResponseEntity<String> response = testRestTemplate.getForEntity(path, String.class);
		Thread.sleep(1000l);
		User user = userRepository
				.findByUsername(UserRegistrationTestState.getInstance().getReturnedUser().getUsername());
		UserRegistrationTestState.getInstance().setReturnedUser(user);
		UserRegistrationTestState.getInstance().setCurrentHttpStatus(response.getStatusCodeValue());
	}
}
