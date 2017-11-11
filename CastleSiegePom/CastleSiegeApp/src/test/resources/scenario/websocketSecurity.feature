Feature: Web socket secutiry 

#Scenario: I want to log in as a valid user and get a token from the oauth2 service
#	Given I am a user that is not loged in
#	When I try to get a token using the oauth login endpoint and using username = "filip" and password "filip"
#	Then the oauth token should not be empty

Scenario: I want to use the websocket as a loged in user 
	Given I am a user that is not loged in -websocketSecurity 
	When I try to get a token with the username "filip" and password "filip" -websocketSecurity 
	Then I should get a request with status code 200 and a token in it - websocketSecurity 
	When I try to create a connection to the websocket "game" and subscribe to the channel "/topic/board/78a5f77a-abc4-4e87-97b7-2cceabb142f3" on the host "localhost" 
	And I try to send a test message with the text "test1" to the chanel "/app/create/78a5f77a-abc4-4e87-97b7-2cceabb142f3" 
	Then I should get the response with the text "test1" 
	
	
Scenario: I want to use the websocket as a not loged in user 
	Given I am a user that is not loged in -websocketSecurity 
	When I try to create a connection to the websocket "game" and subscribe to the channel "/topic/board/78a5f77a-abc4-4e87-97b7-2cceabb142f3" on the host "localhost" 
	Then The operation should fail
	
	
	