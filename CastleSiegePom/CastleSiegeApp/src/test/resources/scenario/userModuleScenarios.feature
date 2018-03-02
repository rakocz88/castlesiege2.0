Feature: User module feature Feature

Scenario: AA- I want to log in as a customer user
	Given AA- I am a user that is not loged in
	When AA- I try to get the restEndpoint for information for user "filip" with the token
	Then AA- I should get the response status 500
	When AA- I try to get a token with the username "filip" and password "filip"
	Then AA- I should get a request with status code 200 and a token in it
	When AA- I try to get the restEndpoint for information for user "filip" with the token
	Then AA- I should get the response status 200 and a  user "filip" in the response
	When AA- I try to get the restEndpoint for information for user "filip2" with the token
	Then AA- I should get the response status 403
	When AA- I try to get all the users with non admin user
	Then AA- I should get the response status 403
	
Scenario: AE- I want to log in as a admin user
	Given AE- I am a user that is not loged in
	When AE- I try to get a token with the username "admin" and password "admin"
	Then AE- I should get a request with status code 200 and a token in it
	When AE- I try to get the restEndpoint for information for user "admin" with the token
	Then AE- I should get the response status 200 and a  user "admin" in the response
	When AE- I try to get the restEndpoint for information for user "filip" with the token
	Then AE- I should get the response status 200 and a  user "filip" in the response
	When AE- I try to get all the users 
	Then AE- I should get the response status 200
	And AE- The list of users should not be empty
	
Scenario: AB- I want to register a new user
	Given AB- I am a person who wants to create a account
	When AB- A user with username "filip" exists in the database
	When AB- I register a user with login  "filip" and password "filip" and firstname  "filip" and surname "filip" and email "filip@gmail.com"
	Then AB- I should get a response with status 400
	When AB- User "flap" does not exist in the database
	And AB- I register a user with login  "flap" and password "flap" and firstname  "flap" and surname "flap" and email "rakocz88@gmail.com"
	Then AB- I should get the response status 200
	And AB- User "flap" should be in the database
	And AB- User "flap" enabled flag should be "false"
	And AB- in the output msg there should be an entry for user "rakocz88@gmail.com" and it should not be empty
	When AB- I try to perform a log in with the user "flap" with password "flap"
	Then AB- I should get the response 401
	When AB- I click the link then was send to "rakocz88@gmail.com"
	Then AB- I should get the response 200
	And AB- User "flap" enabled flag should be "true"
	When AB- I try to perform a log in with the user "flap" with password "flap"
	Then AB- I should get the response 200
	And AB- the response should contain a not empty token
	
Scenario: AC- I want to register a new user and see him in Elastic Search
	Given AC- User "estest" does not exist in the database
	When AC- I register a user with login  "estest" and password "estest" and firstname  "name1" and surname "surname1" and email "gangan921312314@gmail.com"
	Then AC- I should get the response status 200
	When AC- I try to find a user in Elastic Search with the login "estest"
	Then AC- I should get a user with the login "estest" and firstname "name1" and surname "surname1" and email "gangan921312314@gmail.com" and the enabled flag should be set to "false"
	When B- I click the link then was send to "gangan921312314@gmail.com"
	And AC- I try to find a user in Elastic Search with the login "estest"
	Then AC- I should get a user with the login "estest" and firstname "name1" and surname "surname1" and email "gangan921312314@gmail.com" and the enabled flag should be set to "true" 
	
	 
	