# Technical Challenge

### Objective
* Develop a table of people showing all attributes from a response of the HTTP GET request on endpoint "/people"
* The request on "/people" will return an array of person. 
* Example of person:
```
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "johndoe@email.com",
  "lastLogin": "2020-01-01 00:00:00",
  "active": true
}
```
* All the code must be in these files:
    * src/main/webapp/jsp/people.jsp -> HTML code
    * src/main/webapp/js/people.js -> JavaScript/jQuery scripts
    * src/main/webapp/css/people.css -> CSS styles
* You can use:
    * jQuery (3.5.0)
    * JavaScript (ECMAScript 6 - ECMAScript 2015)
    * Bootstrap (4.5.0)
* It's not necessary to create additional files

### Bonus (Optional)
* Remove an item from the table
* Edit an item from the table
* Table Pagination
* Search 

### Requirements
- JDK 1.8
- Maven 3

### Running the application locally

```
mvn clean install
mvn spring-boot:run
```
- Access localhost:8080 using a recent version of Chrome/Firefox