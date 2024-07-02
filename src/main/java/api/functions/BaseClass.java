package api.functions;

public class BaseClass {

	protected static String createUserURL() {
        return Config.getBaseUrl() + Config.CreateUserEndpoint();
    }

    protected static String getUserByUsernameURL() {
        return Config.getBaseUrl() + Config.getUserEndpoint();
    }
    
    protected static String updateUserURL() {
        return Config.getBaseUrl() + Config.updateUserEndpoint();
    }
    
    protected static String deleteUserURL() {
        return Config.getBaseUrl() + Config.deleteUserEndpoint();
    }
}
