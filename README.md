# Food Recipe
Food Recipe application to add, update, delete the recipe

## Food Recipe
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
Create a single‐page web application which allows users to manage your favourite recipes.
Use a dashboard to show all available recipes and the actions to create, update and delete a recipe.
When the user selects a recipe the application should display on the same screen the following attributes:
 * Date and time of creation (formatted as dd‐MM‐yyyy HH:mm);
 * Indicator if the dish is vegetarian;
 * Indicator displaying the number of people the dish is suitable for;
 * Display ingredients as a list;
 * Cooking instructions;
## Technologies
* Java 11
* Spring Boot 2.4.5
* Rest API
* Maven
* Junit5 Mockito
* JWT Token Authentication
* Swagger 2
* Docker

## Setup
### Build the Application
 ``` mvn clean install ```
### Starting the application
```change to target directory
run java -jar food-recipe-0.0.1-SNAPSHOT.jar
Application will run on port 8085
```
### Use the webapp
#### Authentication to access the Recipe services with username and password
```http://localhost:8085/authenticate/  ```

#### Create Recipe <br/>
``` http://localhost:8085/recipes/  ```

#### Get All Recipes
``` http://localhost:8085/recipes/ ```

#### Get Recipe with ID
``` http://localhost:8085/recipes/{id} ```

#### Update Recipe 
``` http://localhost:8085/recipes/{id} ```

#### Delete Recipe
``` http://localhost:8085/recipes/{id} ```

### Swagger Documentation for accessing the all services <br/>
``` http://localhost:8085/swagger-ui.html#/ ```

### Postman collection For Positive cases
``` https://www.getpostman.com/collections/37575ad63f9b21d8f1db ``` 

### Postman collection for Negative cases
``` https://www.getpostman.com/collections/1855238513b2f1864e9b ``` 

