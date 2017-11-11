Feature: User module feature Feature

Scenario: I want to log in as a customer user
	Given I am a user that is not loged in
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 500
	When I try to get a token with the username "filip" and password "filip"
	Then I should get a request with status code 200 and a token in it
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 200 and a  user "filip" in the response
	When I try to get the restEndpoint for information for user "filip2" with the token
	Then I should get the response status 403
	When I try to get all the users with non admin user
	Then I should get the response status 403
	
Scenario: I want to log in as a admin user
	Given I am a user that is not loged in
	When I try to get a token with the username "admin" and password "admin"
	Then I should get a request with status code 200 and a token in it
	When I try to get the restEndpoint for information for user "admin" with the token
	Then I should get the response status 200 and a  user "admin" in the response
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 200 and a  user "filip" in the response
	When I try to get all the users 
	Then I should get the response status 200
	And The list of users should not be empty
	
Scenario: I want to register a new user
	Given I am a person who wants to create a account
	When A user with username "filip" exists in the database
	When I register a user with login  "filip" and password "filip" and firstname  "filip" and surname "filip" and login "filip@gmail.com"
	Then I should get a response with status 400
	When User "flap" does not exist in the database
	And I register a user with login  "flap" and password "flap" and firstname  "flap" and surname "flap" and login "rakocz88@gmail.com"
	Then I should get the response status 200
	And User "flap" should be in the database
	And User "flap" enabled flag should be "false"
	And in the output msg there should be an entry for user "rakocz88@gmail.com" and it should not be empty
	When I try to perform a log in with the user "flap" with password "flap"
	Then I should get the response 401
	When I click the link then was send to "rakocz88@gmail.com"
	Then I should get the response 200
	And User "flap" enabled flag should be "true"
	When I try to perform a log in with the user "flap" with password "flap"
	Then I should get the response 200
	And the response should contain a not empty token
	
	