# Iteration 2 

## Features

Finsished:

* Favorite Cars
* Car Monthly Installments calculator
* Adding database to the persistence layer

Started but not finished:

* SOLID Violations are mostly fixed, but some of them are delibrate and prudent as we cannot afford to make the changes (in terms of time) but will try our best to implement in the next iteration

User Stories: 

* Favourite Cars
* Monthly Installment tool.
* View Dealer Contact information

Note for the Marker:

* We have added the favorite feature for cars which favorites/saves a car (go into favourites to see it and long press on them to remove them from favorites).
* We also have Filter Feature implemented but later on added a feature to sort based max kilometers driven.
* To switch between database and stub just change the boolean fromDB to false in [Services.java](app/src/main/java/application/Services.java)
* When using filter with the database, once you click on search the App gives ANR (App Not Responding) due to the main thread doing input from Database. Please wait and do not click on anything and the filter will show up in around 8 seconds. This is happening because the APP NOT RESPONDING pops up once the main thread is waiting for 5 seconds. I looked into the error and seems like the way to prevent this is either reducing calls to Database or making the program multithreaded.
* When using the filters section each field is mandatory and cannot be left empty, if left empty then it gives a warning to fill the fields.
* Our App is meant to run in Portrait Mode and is not completely supported in the Landscape Mode (The filter button has relative distance with respect to the window size instead of having the struts and spring model, we will try to implement it in the next iteration).


## Architecture Document

We have the [Architecture Diagram](documents/ARCHITECTURE2.md) in the Images folder

## Tests

* The tests are located in [Tests](app/src/test/java/com/lightsoutbugsout/tests) and can be run by running the [AllTests.java](app/src/test/java/com/lightsoutbugsout/tests/AllTests.java) file.
* We have achieved a high test coverage for business layer which is around 90% coverage including the Integration tests. 
* The integration tests will also run on running AllTests.java
