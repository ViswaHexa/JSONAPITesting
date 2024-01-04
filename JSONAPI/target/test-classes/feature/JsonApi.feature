Feature: Json Api Testing
	
	Background:
		Given Set base uri "https://jsonplaceholder.typicode.com"
	
	@getAllResources
  Scenario Outline: Get All Resources
  	When I Send the GET request with <resources>
  	Then validating the statuscode 200 
  	
  	Examples:
  	|resources |
  	|"posts"   |
  	|"comments"|
  	|"todos"   |
  	|"users"   |
  	
  @getPaticularDetails
  Scenario Outline: Get Posts Deatils
  	When Send the get request <endPoints> and id <id>
  	Then validating the statuscode 200 and id <id>
		And validate Json Schema
  	
  	Examples:
  	|endPoints|id|
  	|"users"  |1 | 			
  	# endPoints = posts,comments,todos,users
  	
  @createResources
  Scenario Outline: Create New Resources
  	When send the post request <endPoints> with body
  	Then validating the statuscode <code>
		
		Examples:
		|endPoints|code|
  	|"comments"	|201 |
  	
  	
  	
  	