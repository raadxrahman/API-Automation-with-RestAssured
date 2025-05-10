# API Automation with RestAssured 
In this project, I have automated APIs in Postman, and Implemented them in Intellij using TestNG following the Page-Object-Model(POM) to automate all features of the [Daily Finance](https://dailyfinance.roadtocareer.net/) website. 

## Technologies Used

 * Java
 * Postman
 * Rest-Assured (https://mvnrepository.com/artifact/io.rest-assured/rest-assuredtestImplementation("io.rest-assured:rest-assured:5.5.1"))
 * TestNG (https://mvnrepository.com/artifact/org.testng/testng testImplementation group: 'org.testng', name: 'testng', version: '7.10.2')
 * Java-Faker (https://mvnrepository.com/artifact/com.github.javafaker/javafaker implementation group: 'com.github.javafaker', name: 'javafaker', version: '1.0.2')
 * Apache-Commons (https://mvnrepository.com/artifact/org.apache.commons/commons-csv implementation group: 'org.apache.commons', name: 'commons-csv', version: '1.12.0')
 * Allure-Testing (https://mvnrepository.com/artifact/io.qameta.allure/allure-testng implementation group: 'io.qameta.allure', name: 'allure-testng', version: '2.29.0')
 * Jakson-Databind (https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind implementation("com.fasterxml.jackson.core:jackson-databind:2.18.3"))

## Prerequisites
 
  * Java Development Kit (JDK) 8 or later
  * IntelliJ IDEA (or your preferred Java IDE)
  * Postman
  * Gradle (if you want to use the provided Gradle build)
  * Allure CLI (for generating reports)

## Setup Instructions

 1.  **Clone the repository:**

     ```bash
     git clone <repository_url>
     cd <repository_name>
     ```

 2.  **Open the project in IntelliJ IDEA:**

     * Open IntelliJ IDEA.
     * Select "Open or Import Project."
     * Navigate to the project directory and select the `build.gradle` file (if you are using Gradle) or the project directory itself.
     * IntelliJ IDEA will import the project.
       
## Tasks Automated via API (Rest Assured)
The following API functionalities have been automated using Java and the Rest Assured library:

* **User Registration:** Automation of the process to register new users, including generating unique user data and verifying successful registration through status code and response body assertions. Negative tests were included to cover scenarios like invalid input data.
* **Admin Login:** Automation of the login process for an administrative user, verifying successful authentication and potentially capturing tokens or session information for subsequent requests.
* **Get User List:** Automation of retrieving a list of users, including verifying the response status and potentially asserting the presence of specific user data.
* **Search User by User ID:** Automation of searching for a specific user using their unique ID, verifying the response status and the correctness of the returned user information.
* **Edit User Info:** Automation of modifying a user's information (e.g., first name, phone number), verifying the success of the update through status codes and response data. Negative tests were included to cover invalid update attempts.
* **User Login:** Automation of the login process for regular users, verifying successful authentication and potentially capturing tokens or session information. Negative tests were included for incorrect email or password scenarios.
* **Get Item List:** Automation of retrieving a list of items, verifying the response status and potentially asserting the presence of specific item data.
* **Add Item:** Automation of adding a new item to the item list, verifying successful creation through status codes and response data. Negative tests were included for invalid item creation attempts.
* **Edit Item Name:** Automation of modifying the name of an existing item, verifying the success of the update through status codes and response data. Negative tests were included for invalid update attempts.
* **Delete Item:** Automation of deleting an item from the item list using its ID, verifying successful deletion through status codes. Negative tests were included for attempting to delete non-existent items.

Each automated test suite includes positive scenarios to verify correct functionality and negative scenarios to ensure the API handles invalid inputs and edge cases appropriately.
