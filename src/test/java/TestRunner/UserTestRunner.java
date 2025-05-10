package TestRunner;

import Setup.Setup;
import Controller.userController;
import Setup.UserModel;
import Utils.Utils;
import Setup.ItemModel;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;

import static Utils.Utils.faker;
import static org.hamcrest.Matchers.containsString;

public class UserTestRunner extends Setup {

    private userController userController;

    @BeforeMethod
    public void initUserController(){
        userController = new userController(prop);
    }


    @Test(priority = 1, description = "User Registration Positive Test")
    public void doRegistration() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        String firstName = Utils.getRandomFirstName();
        String lastName = Utils.getRandomLastName();
        String email = Utils.getRandomEmail();
        String password = Utils.getPassword();
        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(true);

        Response res = userController.doRegister(userModel);
        System.out.println(res.asString());

        JsonPath jsonPath = res.jsonPath();

        String userId = jsonPath.get("_id"); //for user info update purposes
        Utils.setEnvVar("UserID", userId);

        String userFirstName = jsonPath.get("firstName"); //for user info update purposes
        Utils.setEnvVar("userFirstName", userFirstName);

        String userLastName = jsonPath.get("lastName");  //for user info update purposes
        Utils.setEnvVar("userLastName", userLastName);

        String userEmail = jsonPath.get("email");
        Utils.setEnvVar("userEmail", userEmail); //for duplicate user negative test

        Utils.setEnvVar("userPassword", password);

    }

    @Test(priority = 2, description = "User Registration Negative Test (Missing Fields)")
    public void doRegistrationNegative_1() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        String firstName = Utils.getRandomFirstName();
        String lastName = Utils.getRandomLastName();
//        String email = Utils.getRandomEmail();
//        String password = Utils.getPassword();
//        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
//        userModel.setEmail(email);
//        userModel.setPassword(password);
//        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(true);

        Response res = userController.doRegister(userModel);
        System.out.println(res.asString());

        Assert.assertEquals(res.getStatusCode(), 500);
        Assert.assertTrue(res.asString().contains("Server error"));
        System.out.println("User Cannot Login Without Filling Up Necessary Fields!");
    }

    @Test(priority = 3, description = "User Registration Negative Test (Duplicate Email)")
    public void doRegistrationNegative_2() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        String firstName = Utils.getRandomFirstName();
        String lastName = Utils.getRandomLastName();
        String email = prop.getProperty("userEmail");
        String password = Utils.getPassword();
        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        userModel.setAddress(address);
        userModel.setGender(gender);
        userModel.setTermsAccepted(true);

        Response res = userController.doRegister(userModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 400); // Expecting failure
        Assert.assertTrue(res.asString().contains("User already exists with this email address"));
    }

    @Test(priority = 4, description = "User Login Negative Test (Invalid Credentials)")
    public void userLoginNegative() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

//        UserModel userModel = new UserModel();
//        userModel.setEmail(prop.getProperty("userEmail"));
//        userModel.setPassword("4321");
//        Response res = userController.doLogin(userModel);
//        System.out.println(res.asString());
//        Assert.assertEquals(res.getStatusCode(), 401);
//        Assert.assertTrue(res.asString().contains("Invalid email or password"));

        // Scenario 1: Correct email, incorrect password
        UserModel userModel1 = new UserModel();
        userModel1.setEmail(prop.getProperty("userEmail"));
        userModel1.setPassword(prop.getProperty("invalidPassword", "4321"));

        userController.doLogin(userModel1)
                .then()
                .statusCode(401)
                .body(containsString("Invalid email or password"));

        // Scenario 2: Incorrect email, correct password
        UserModel userModel2 = new UserModel();
        userModel2.setEmail(prop.getProperty("invalidEmail", "nonexistent@example.com"));
        userModel2.setPassword(prop.getProperty("userPassword"));

        userController.doLogin(userModel2)
                .then()
                .statusCode(401)
                .body(containsString("Invalid email or password"));

    }

    @Test(priority = 5, description = "User Login Positive Test")
    public void userLogin() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        UserModel userModel = new UserModel();
        userModel.setEmail(prop.getProperty("userEmail"));
        userModel.setPassword(prop.getProperty("userPassword"));

        Response res = userController.doLogin(userModel);
        JsonPath jsonPath = res.jsonPath();
        String userToken = jsonPath.get("token");
        Utils.setEnvVar("userToken",userToken);
        Assert.assertEquals(res.getStatusCode(), 200);
    }

    @Test(priority = 6, description = "Get Item List")
    public void getItemList(){

        Response res = userController.getItemList();
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 7, description = "Add Item Positive Test")
    public void addItemPositive() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ItemModel itemModel = new ItemModel();

        itemModel.setItemName("From Intellij");
        itemModel.setQuantity("5");
        itemModel.setAmount("20");
        itemModel.setPurchaseDate("2025-05-05");
        itemModel.setMonth("May");
        itemModel.setRemarks("Automated TestNG Item");

        Response res = userController.addItem(itemModel);
        Assert.assertEquals(res.getStatusCode(), 201);

        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();

        String itemID = jsonPath.get("_id"); //for further customization
        Utils.setEnvVar("ItemID", itemID);
        String itemName = jsonPath.get("itemName");
        Utils.setEnvVar("ItemName", itemName);

        getItemList();

    }

    @Test(priority = 7, description = "Add Item Negative Test (Missing Necessary Fields)")
    public void addItemNegative() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ItemModel itemModel = new ItemModel();

//        itemModel.setItemName("From Intellij");
        itemModel.setQuantity("5");
        itemModel.setAmount("20");
        itemModel.setPurchaseDate("2025-05-05");
        itemModel.setMonth("May");
        itemModel.setRemarks("Automated TestNG Item");

        Response res = userController.addItem(itemModel);
        Assert.assertEquals(res.getStatusCode(), 500); //item cant be added

        System.out.println(res.asString());
        JsonPath jsonPath = res.jsonPath();

        String itemID = jsonPath.get("_id"); //for further customization
        Utils.setEnvVar("ItemID", itemID);
        String itemName = jsonPath.get("itemName");
        Utils.setEnvVar("ItemName", itemName);

//        getItemList();

    }

    @Test(priority = 8, description = "Edit Item Name Positive Test")
    public void editItem(){

        ItemModel itemModel = new ItemModel();

        itemModel.setItemName("From Intellij EDITED");
        itemModel.setQuantity("4");
        itemModel.setAmount("30");
        itemModel.setPurchaseDate("2025-05-05");
        itemModel.setMonth("May");
        itemModel.setRemarks("EDITED VIA INTELLIJ");
        Response res = userController.editItem(prop.getProperty("ItemID"), itemModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 9, description = "Delete Item Positive Test")
    public void deleteItem(){

        Response responseNew = userController.deleteItem(prop.getProperty("ItemID"));

        System.out.println(responseNew.asString());
    }









}
