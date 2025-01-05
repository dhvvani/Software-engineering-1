# Iteration 1 

## Features

Finsished:

* View Cars
* View Car details like fuel type, transmission etc.
* Car Filters based on Model, Make, Transmission etc.

Started but not finished:

* Car Filter based on Kilometers (an additional feature which was not in the original filter feature)

User Stories: 

* Filter Cars
* Show Details (Dealer End)
* View Details (App User End)

Note for the Marker:

* We have added the view feature for cars which shows different cars.
* We also have Filter Feature implemented but later on added a feature to sort based on kilometers driven but it is not completely functional.
* When using the filters section each field is mandatory and cannot be left empty. 
* Our App is meant to run in Portrait Mode and is not completely supported in the Landscape Mode (The filter button has relative distance with respect to the window size instead of having the struts and spring model, we will try to implement it in the next iteration).


## Architecture Document

We have the [Architecture Diagram](../images/ARCHITECTURE.png) in the Images folder

## Tests

The tests are located in [Tests](../app/src/test/java/com/lightsoutbugsout/tests) and can be run by running the [AllTests.java](../app/src/test/java/com/lightsoutbugsout/tests/AllTests.java) file
