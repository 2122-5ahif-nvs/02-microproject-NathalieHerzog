Feature: StaffEndpoints: CRUD-Operations

  Background:
    * url baseUrl

  Scenario: Post staff
    Given path 'api/staff'
    And request { "name": "thisIsAStaffmember", "salary": "2000", "isOccupied": "true", "engagement":{"id": 3}}
    When method POST
    Then status 201

  Scenario: Post staff - engagement missing
    Given path 'api/staff'
    And header Content-Type = 'application/json'
    And request { "name": "thisIsAStaffmember", "salary": "2000", "isOccupied": "true"}
    When method POST
    Then status 400
    And header reason = 'engagement is missing'



