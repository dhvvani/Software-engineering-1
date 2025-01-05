# Iteration 3

## Features

Finsished:

* Add Cars with Existing Dealers
* Add Cars with New Dealers
* Added Buy Car functionality

Started but not finished:

* Although we also planned for a User Login page, we did not have enough time to make the login page. This is due for further releases.

User Stories: 

* As a new seller, I want to be able to add my details to a car in the market to let individuals know that the vehicle is up for sale by me.
* As a seller, I need to be able to remove vehicles which have been sold out.
* As a seller, I want to be able to place my car onto the market to let individuals know that the vehicle is up for sale.

Note for the Marker:

* We have add the add car/add dealer feature which enables the user to add cars with an existing dealer or add cars with a new dealer (which can be created).
* We have added a feature to buy a car which is then removed from the database if the user confirms the purchase.
* We have added the favorite feature for cars which favorites/saves a car (go into favourites to see it and long press on them to remove them from favorites).
* We also have Filter Feature implemented but later on added a feature to sort based max kilometers driven.
* To switch between database and stub just change the boolean fromDB to false in [Services.java](../app/src/main/java/application/Services.java).
* When using the filters section each field is mandatory and cannot be left empty, if left empty then it gives a warning to fill the fields.
* Our App is meant to run in Portrait Mode and is not completely supported in the Landscape Mode.
* System Tests for all of our major features have been implemented.
* Earlier in the last iteration our tests were only running correctly from the AllTests.java file and when run from package a few of them were failing. This has been fixed in this iteration and now tests run from both locations and all of them pass.


## Architecture Document

We have the [Architecture Diagram](images/ARCHITECTURE3.md) in the Images folder

## Tests

* The Unit tests and Integration Tests are located in [Tests](../app/src/test/java/com/lightsoutbugsout/tests) and can be run by running the [AllTests.java](../app/src/test/java/com/lightsoutbugsout/tests/AllTests.java) file.
* The System tests are located in [AndroidTest](../app/src/androidTest/java/com/example/myapplication)
* We have achieved a high test coverage for business layer which is around 92% coverage including the Integration tests. 
* The integration tests will also run on running AllTests.java
* The system tests will run either using the package or by running [AllSystemTests.java](../app/src/androidTest/java/com/example/myapplication/sysTests)
