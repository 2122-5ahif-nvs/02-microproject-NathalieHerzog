Feature: CustomerEndpoints: CRUD-Operations

  Background:
    * url baseUrl

    Scenario: Post customer
      Given path 'api/customer'
      And request { "username": "thisIsAUsername", "email": "thisisanemail@gmail.com", "sponsorship":{"id": 3} }
      When method POST
      Then status 202
      Then match response == 'thisIsAUsername'

  Scenario: Post customer - missing email
    Given path 'api/customer'
    And request { "username": "thisIsAUsername", "sponsorship":{"id": 3} }
    When method POST
    Then status 202
    Then match response == 'thisIsAUsername'

  Scenario: Update username
    Given path 'customer/username/1'
    And request { "newUser": "updatedThisUser" }
    When method UPDATE
    Then status 202
    Then match response == 'updatedThisUser'

  Scenario: Update email
    Given path 'customer/email/1'
    And request { "newMail": "updatedThisEmail" }
    When method UPDATE
    Then status 202
    Then match response == 'updatedThisEmail'