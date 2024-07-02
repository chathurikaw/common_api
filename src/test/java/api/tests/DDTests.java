package api.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.UserAPI;
import api.payload.User;
import api.utilities.ExcelDataProvider;
import api.utilities.ExtentReportListener;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.restassured.response.Response;

public class DDTests {

	private static final Logger logger = LogManager.getLogger(DDTests.class);

	@BeforeClass
	public void setUp() {
		ExtentReportListener.initializeExtentReport();
		logger.info("Starting tests in DDTests");
	}

	@Test(priority = 1, dataProvider = "UserData", dataProviderClass = ExcelDataProvider.class)
	public void createUserTest(String idStr, String username, String firstName, String lastName, String email,
			String password, String phone) {

		ExtentTest test = ExtentReportListener.createTest("Create User Test");

		try {
			logger.debug(
					"Received user data: id={}, username={}, firstName={}, lastName={}, email={}, password={}, phone={}",
					idStr, username, firstName, lastName, email, password, phone);

			int id = 0;
			if (idStr != null && !idStr.isEmpty()) {
				id = Integer.parseInt(idStr);
				logger.debug("Parsed user ID: {}", id);
			}

			User userPayload = new User();
			userPayload.setId(id);
			userPayload.setUsername(username);
			userPayload.setFirstName(firstName);
			userPayload.setLastName(lastName);
			userPayload.setEmail(email);
			userPayload.setPassword(password);
			userPayload.setPhone(phone);

			logger.info("Creating user: {}", username);
			test.pass("Creating user: " + username);

			Response response = UserAPI.createUser(userPayload);
			response.then().log().all();
			response.then().statusCode(200); // Assert response status code
			test.pass("User created successfully");
			logger.info("User created successfully");

		} catch (Exception e) {
			logger.error("Failed to create user", e);
			test.fail("Failed to create user: " + e.getMessage());
			test.log(Status.FAIL, e);
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
	}

	@Test(dataProvider = "Usernames", dataProviderClass = ExcelDataProvider.class, priority = 2, dependsOnMethods = "createUserTest")
	public void deleteUserTest(String username) {
		ExtentTest test = ExtentReportListener.createTest("Delete User Test");
		try {
			logger.info("Deleting user: {}", username);
			test.info("Deleting user: " + username);
			Response response = UserAPI.deleteUser(username);
			response.then().log().all();
			Assert.assertEquals(response.getStatusCode(), 200); // Assert response status code

			// Verify user is deleted
			Response getResponse = UserAPI.getUser(username);
			Assert.assertEquals(getResponse.getStatusCode(), 404); // User should not be found
			logger.info("User deleted successfully");
			test.pass("User deleted successfully");
		} catch (Exception e) {
			logger.error("Failed to delete user", e);
			test.fail("Failed to delete user: " + e.getMessage());
			test.log(Status.FAIL, e);
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
	}

	@Test(priority = 3)
	public void skippedTest() {
		ExtentTest test = ExtentReportListener.createTest("Skipped Test");
		logger.warn("This test was skipped intentionally or due to dependencies");
		test.skip("This test was skipped intentionally or due to dependencies");
	}

	@AfterClass
	public void tearDown() {
		ExtentReportListener.flushExtentReports();
		logger.info("Completed tests in DDTests");
	}
}
