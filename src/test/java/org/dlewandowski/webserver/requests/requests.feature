Feature: run different requests to webserver and check responses

  Scenario:send GET request and check if response has 200 code
    Given url 'http://localhost:65535'
    When method get
    Then status 200


  Scenario:send POST request and check if response has 501 code
    * def randomRequestBody =
    """
    {
      "name": "Test"
    }
    """
    Given url 'http://localhost:65535'
    And request randomRequestBody
    When method post
    Then status 501

  Scenario:send PUT request and check if response has 501 code
    * def randomRequestBody =
    """
    {
      "name": "Test"
    }
    """
    Given url 'http://localhost:65535'
    And request randomRequestBody
    When method put
    Then status 501

  Scenario:send DELETE request and check if response has 501 code
    Given url 'http://localhost:65535'
    When method delete
    Then status 501