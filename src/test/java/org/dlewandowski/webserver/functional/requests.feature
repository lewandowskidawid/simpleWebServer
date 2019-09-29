Feature: run different requests to webserver and check responses

  Scenario:server returns 200 code when text file is requested
    Given url INSTANCE_URL +'/index.html'
    When method get
    Then status 200
    Then match header Content-Type == 'text/html'
    Then match response contains 'Design a better website template.'

  Scenario:server returns proper resource when query string is present
    Given url INSTANCE_URL +'/js/main.js?queryString=123'
    When method get
    Then status 200
    Then match header Content-Type == 'text/javascript'
    Then match response contains 'jQuery(document).ready(function($){'

  Scenario:server returns index.html when dir is requested
    Given url INSTANCE_URL
    When method get
    Then status 200
    Then match header Content-Type == 'text/html'
    Then match response contains 'Design a better website template.'

  Scenario:server returns 404 code when non existing resource is requested
    Given url INSTANCE_URL +'/nonexisting'
    When method get
    Then status 404
    Then match header Content-Type == 'text/plain'
    Then match response contains 'Requested resource dost not exist'

  Scenario:server returns 404 when dir doesn't contain index.html file
    Given url INSTANCE_URL + '/images'
    When method get
    Then status 403

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