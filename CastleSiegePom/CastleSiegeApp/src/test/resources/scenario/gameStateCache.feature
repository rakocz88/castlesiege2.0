Feature: Testing if the cache works corectly
Scenario: BA- Test if the game state cache works
	Given BA- I create a game 1 state with the uuid "063f2a6b-400f-43d8-a41a-cf9121b5ccc9" and a description "desc1"
	And BA- I create a game 2 state with the uuid "fec8db38-f61e-4a80-93b5-140cff5d1a0f" and a description "desc2"
	When BA- I try to get the object from the cache with the uuid "063f2a6b-400f-43d8-a41a-cf9121b5ccc9"
	Then BA- I should get the game state with the description value "desc1" for the uuid "063f2a6b-400f-43d8-a41a-cf9121b5ccc9"
	When BA- I try to get the object from the cache with the uuid "fec8db38-f61e-4a80-93b5-140cff5d1a0f"
	Then BA- I should get the game state with the description value "desc2" for the uuid "fec8db38-f61e-4a80-93b5-140cff5d1a0f"
	When BA- I change the the description of the of the "063f2a6b-400f-43d8-a41a-cf9121b5ccc9" game state to "changedDescription1"
	And BA- I try to get the object from the cache with the uuid "063f2a6b-400f-43d8-a41a-cf9121b5ccc9"
	Then BA- I should get the game state with the description value "changedDescription1" for the uuid "063f2a6b-400f-43d8-a41a-cf9121b5ccc9"
	When BA- I remove the game state with the uuid  "fec8db38-f61e-4a80-93b5-140cff5d1a0f"
	And BA- I try to get the object with the uuid "fec8db38-f61e-4a80-93b5-140cff5d1a0f"
	Then BA- I should get a null value for the uuid "fec8db38-f61e-4a80-93b5-140cff5d1a0f"
	
	