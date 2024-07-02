package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import com.github.javafaker.Faker;

import api.endpoints.UserAPI;
import api.functions.BaseClass;
import api.payload.User;
import api.utilities.ExtentReportListener;
import io.restassured.response.Response;

public class UserTest extends BaseClass{

	private Faker faker;
    private User userPayload;
    private static final Logger logger = LogManager.getLogger(UserTest.class);
    
	@BeforeClass
	public void setup() throws IOException {
	    ExtentReportListener.initializeExtentReport();
	    logger.info("Starting tests in UserTest");
        // Initialize Faker
        faker = new Faker();        
        // Generate fake user data
        userPayload = new User();
        userPayload.setId(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().emailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().phoneNumber());
          
    }
	
	@Test(priority=1)
	public void createUserTest() {
		logger.info("creating User");
		Response response = UserAPI.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=2)
	public void getUserTest() {
		logger.info("Get User details : {}",this.userPayload.getUsername());
		Response response = UserAPI.getUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=3)
	public void updateUserTest() {
		logger.info("Update User : {}",this.userPayload.getUsername());
		String updatedFirstName = faker.name().firstName();
		String updatedLastName = faker.name().lastName();
		String updatedEmail = faker.internet().emailAddress();
		
		userPayload.setFirstName(updatedFirstName);
        userPayload.setLastName(updatedLastName);
        userPayload.setEmail(updatedEmail);
		
		Response response = UserAPI.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		
		Response responseAfterUpdate = UserAPI.getUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
		
		responseAfterUpdate.then().assertThat()
         .body("firstName", equalTo(updatedFirstName))
         .body("lastName", equalTo(updatedLastName))
         .body("email", equalTo(updatedEmail));
	}
	
	@Test(priority=4)
	public void deleteUserTest() {
		logger.info("Delete user : {}",this.userPayload.getUsername());
		Response response = UserAPI.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(),200);
		response.then().log().all();
		
		Response responseAfterUpdate = UserAPI.getUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),404);
	}
	
}

