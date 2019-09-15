Feature: run different requests to webserver and check responses

  Scenario:server returns 200 code when text file is requested
    Given url INSTANCE_URL +'/test.txt'
    When method get
    Then status 200

  Scenario:server returns 404 code when non existing resource is requested
    Given url INSTANCE_URL +'/nonexisting'
    When method get
    Then status 404

  Scenario:server returns 501 code when directory is requested
    Given url INSTANCE_URL
    When method get
    Then status 501

  Scenario:server returns 201 code when post request executed
    Given url INSTANCE_URL + '/newfile.txt'
    And multipart file myFile = { read: 'lorem_ipsum.txt', filename: 'lorem_ipsum.txt', contentType: 'text/plain' }
    When method post
    Then status 501

  Scenario:server returns 501 code when put request executed
    Given url INSTANCE_URL + '/updatedfile.txt'
    And multipart file myFile = { read: 'lorem_ipsum.txt', filename: 'lorem_ipsum.txt', contentType: 'text/plain' }
    When method put
    Then status 501

  Scenario:server returns 501 code when delete request executed
    Given url INSTANCE_URL
    When method delete
    Then status 501