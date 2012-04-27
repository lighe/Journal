package controllers;
import java.util.List;

import play.mvc.Before;
import models.*;
 
public class Security extends Secure.Security {
    		
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("email", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }
	
    static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();
        return user != null && user.password.equals(password);
    }   
    
    //returns true if user is an author
    public static boolean isAuthor() {
    	return isUserOfRole("author");
    }
    
    //returns true if user is a reviewer
    public static boolean isReviewer() {
    	return isUserOfRole("reviewer");
    }
    
    //returns true if user is an editor
    public static boolean isEditor() {
    	return isUserOfRole("editor");
    }
    
    //Returns true if user has admin privalages
    public static boolean isAdmin() {
    	return isUserOfRole("admin");
    }
    
    //Determines if a user is of the inputed role
    private static boolean isUserOfRole(String role){
    	List<Role> roles = getRoles();
        boolean isOfRole = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals(role)){
        		isOfRole = true;
        	}
        }
        return isOfRole;
    }
    
    private static List<Role> getRoles(){
        User user = User.find("email", Security.connected()).first();
        UserRole userRole = UserRole.find("user_ID", user).first();
        List<Role> roles = Role.find("role_ID", userRole.role_ID).fetch();
		return roles;
    }
	
	public static User getConnectedUser() {
		return User.find("email", Security.connected()).first();	
	}

}