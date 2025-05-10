package Controller;

import Setup.ItemModel;
import Setup.UserModel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class userController {
    Properties prop;

    public userController(Properties prop){
        this.prop = prop;
    }

    public Response doRegister(UserModel userModel){ //user register

        RestAssured.baseURI = prop.getProperty("baseURL");

        return given().contentType("application/json")
                .body(userModel)
                .when()
                .post("/api/auth/register");
    }

    public Response doLogin(UserModel userModel){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json").body(userModel)
                .when()
                .post("/api/auth/login");

    }

    public Response getItemList() {

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json")
                . header("Authorization","Bearer " + prop.getProperty("userToken"))
                .when()
                .get("/api/costs");
    }

    public Response addItem(ItemModel itemModel) {

        RestAssured.baseURI = prop.getProperty("baseURL");

        return given().contentType("application/json")
                .body(itemModel)
                .header("Authorization", "Bearer " + prop.getProperty("userToken"))
                .when()
                .post("/api/costs");

    }

    public Response editItem(String itemID, ItemModel itemModel){

        RestAssured.baseURI= prop.getProperty("baseURL");

        return given().contentType("application/json")
                .body(itemModel)
                .header("Authorization","Bearer "+ prop.getProperty("userToken"))
                .when()
                .put("/api/costs/"+ itemID);

    }

    public Response deleteItem(String itemID){

        RestAssured.baseURI = prop.getProperty("baseURL");

        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("userToken"))
                .when()
                .delete("/api/costs/"+itemID);
    }
}
