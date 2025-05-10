package TestRunner;

import Setup.Setup;
import Controller.userController;
import Setup.UserModel;
import Utils.Utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;

import static org.hamcrest.Matchers.containsString;

public class AdminTestRunner extends Setup {

    private userController userController;

    @BeforeMethod
    public void initUserController(){
        userController = new userController(prop);
    }

    @Test(priority = 1, description = "Admin Login Negative Test (Invalid Credentials)")
    public void adminLoginNegative(){

        UserModel userModel = new UserModel();

        userModel.setEmail(prop.getProperty("adminEmail"));
        userModel.setPassword("4321");
        Response res = userController.doLogin(userModel);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 401);

    }

    @Test(priority = 2, description = "Admin Login Positive Test")
    public void adminLoginPositive() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        UserModel userModel = new UserModel();

        userModel.setEmail(prop.getProperty("adminEmail"));
        userModel.setPassword(prop.getProperty("adminPassword"));
        Response res = userController.doLogin(userModel);
        JsonPath jsonPath = res.jsonPath();
        String adminToken = jsonPath.get("token");
        Utils.setEnvVar("adminToken",adminToken);
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 3, description = "Admin Get User List")
    public void adminGetUserList(){

        Response res = userController.getUserList();

        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 7, description = "Admin Search User")
    public void adminSearchUser(){

        Response res = userController.searchUser(prop.getProperty("UserID"));
        System.out.println(res.asString());
        Assert.assertEquals(res.getStatusCode(), 200);

    }

    @Test(priority = 8, description = "Admin Edits User Information")
    public void adminEditUser() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        String userID = prop.getProperty("UserID");
        Response res = userController.searchUser(userID);
        System.out.println(res.asString());

        UserModel updateModel = new UserModel();
        String firstName = "Edited Through Intellij";
        String lastName = Utils.getRandomLastName();
        String email = prop.getProperty("userEmail");
        String password = Utils.getPassword();
        String phoneNumber = Utils.getRandomPhoneNumber();
        String address = Utils.getRandomAddress();
        String gender = Utils.getRandomGender();

        updateModel.setFirstName(firstName);
        updateModel.setLastName(lastName);
        updateModel.setEmail(email);
        updateModel.setPassword(password);
        updateModel.setPhoneNumber(phoneNumber);
        updateModel.setAddress(address);
        updateModel.setGender(gender);
        updateModel.setTermsAccepted(true);

        Response responseNew = userController.editUserInfo(userID, updateModel);
        System.out.println(responseNew.asString());
        Assert.assertEquals(responseNew.getStatusCode(), 200);
        Utils.setEnvVar("userFirstName",firstName);

    }

}
