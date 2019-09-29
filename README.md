[![Build Status](https://travis-ci.com/lewandowskidawid/simpleWebServer.svg?branch=master)](https://travis-ci.com/lewandowskidawid/simpleWebServer)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=simpleWebServer&metric=alert_status)](https://sonarcloud.io/dashboard?id=simpleWebServer)

## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Testing](#testing)
* [Usage](#usage)
  * [Run Parameters](#run-parameters)


## About The Project
A multi-threaded file-based web server. The server supports only GET (read) operations.

### Built With
* [Java 1.8](https://www.java.com)
* [Maven 3.6.1](https://maven.apache.org/)
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Apache Commons CLI](https://commons.apache.org/proper/commons-cli/)
* [Karate](https://github.com/intuit/karate)
* [Exec Maven Plugin](https://www.mojohaus.org/exec-maven-plugin/)
* [JUnit 5](https://junit.org/junit5/)
* [Jacoco](https://www.eclemma.org/jacoco/)
* [Sonar Cloud](https://sonarcloud.io/dashboard?id=simpleWebServer)
* [Travis CI](https://travis-ci.com/lewandowskidawid/simpleWebServer)



<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

1. Installed Java 8
2. Installed Maven (tested with version 3.6.1)

### Installation

Execute following command to compile and run the program: `mvn clean package exec:java`. 
The server runs on `65535` port and  resources from `/src/test/resources` directory will be exposed.


### Testing
 
 Unit tests are run as part of build process. 
 
 Integration tests might be called with `mvn test -Dtest=ServerTest` command. For testing purposes default server
 configuration exposes a demo page downloaded from: https://colorlib.com/preview/theme/atomic/


## Usage
The server supports `GET` requests only. 
* When existing resource is requested then the file is returned by the server with `200` status code
* When existing directory is requested and the directory contains `index.html` file then the file is returned with `200` status code
* When existing directory is requested but it doesn't contain `index.html` file then `403` status code is returned
* When nonexisting resource is requested then `404` status code is returned

In case of other requests `501 NOT IMPLEMENTED` response is returned.

### Run Parameters 
* `-d <arg>` - allows to specify server root directory. Default value: `.`
* `-p <arg>` - HTTP port number to run the server. Default value: 65535
* `-t <arg>` - number of threads used by server incoming requests. Default value: 20