package api.functions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	 private static Properties properties = new Properties();

	    static {
	        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
	            if (input == null) {
	                System.out.println("Sorry, unable to find config.properties");
	               
	            }
	            properties.load(input);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	    public static String getBaseUrl() {
	        return properties.getProperty("baseUrl");
	    }

	    public static String CreateUserEndpoint() {
	        return properties.getProperty("createUser");
	    }
	    
	    public static String getUserEndpoint() {
	        return properties.getProperty("getUserByUsername");
	    }

	    public static String updateUserEndpoint() {
	        return properties.getProperty("updateUser");
	    }

	    public static String deleteUserEndpoint() {
	        return properties.getProperty("deleteUser");
	    }
}
