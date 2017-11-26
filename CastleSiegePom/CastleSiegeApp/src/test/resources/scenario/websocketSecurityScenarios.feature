Feature: Web socket secutiry 

Scenario: ADa- I want to use the websocket as a loged in user 
	Given ADa- I am a user that is not loged in 
	When ADa- I try to get a token with the username "filip" and password "filip" 
	Then ADa- I should get a request with status code 200 and a token in it 
	When ADa- I try to create a connection to the websocket "game" and subscribe to the channel "/topic/board/78a5f77a-abc4-4e87-97b7-2cceabb142f3" on the host "localhost" 
	And ADa- I try to send a test message with the text "test1" to the chanel "/app/create/78a5f77a-abc4-4e87-97b7-2cceabb142f3" 
	Then ADa- I should get the response with the text "test1" 
	
	
Scenario: ADb- I want to use the websocket as a not loged in user 
	Given ADb- I am a user that is not loged in 
	When ADb- I try to create a connection to the websocket "game" and subscribe to the channel "/topic/board/78a5f77a-abc4-4e87-97b7-2cceabb142f3" on the host "localhost" 
	Then ADb- The operation should fail
	
	
	