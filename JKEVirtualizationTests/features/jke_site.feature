Feature: JKEVirtualizationTests
    
  	Scenario: A user tries to log in to the JKE website
    Given I have a jke web server
	    And I have a user account
    Then I should be able to login to the user account
    
    Scenario: A user gets a stock quote for ticker symbol 'IBM' with the value '1000000.00'
	Given I have a jke web server
		And I have a user account
	Then I should be able to get a stock quote for 'IBM' with the value '1000000.00'
