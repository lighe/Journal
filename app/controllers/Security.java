package controllers;
import java.util.List;

import play.mvc.Before;
import models.*;
 
public class Security extends Secure.Security {
    
	@Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            Users user = Users.find("email", Security.connected()).first();
            renderArgs.put("user", user.email);
        }
    }
	
    static boolean authenticate(String username, String password) {
        Users user = Users.find("byEmail", username).first();
        return user != null && user.password.equals(password);
    }   
    
    //returns true if user is an auditor
    static boolean isAuditor() {
    	List<Roles> roles = getRoles();
        boolean isAuditor = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals("auditor")){
        		isAuditor = true;
        	}
        }
        return isAuditor;
    }
    
    //returns true if user is a reviewer
    static boolean isReviewer() {
    	List<Roles> roles = getRoles();
        boolean isReviewer = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals("reviewer")){
        		isReviewer = true;
        	}
        }
        return isReviewer;
    }
    
    //returns true if user is an editor
    static boolean isEditor() {
    	List<Roles> roles = getRoles();
        boolean isEditor = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals("editor")){
        		isEditor = true;
        	}
        }
        return isEditor;
    }
    
    //Returns true if user has admin privalages
    static boolean isAdmin() {
    	List<Roles> roles = getRoles();
        boolean isAdmin = false;
        for(int x = 0; roles.size() > x; x++){
        	if(roles.get(x).role.contentEquals("admin")){
        		isAdmin = true;
        	}
        }
        return isAdmin;
    }
    
    static List<Roles> getRoles(){
        Users user = Users.find("email", Security.connected()).first();
        User_Roles userRole = User_Roles.find("user_ID", user).first();
        List<Roles> roles = Roles.find("role_ID", userRole.role_ID.role_ID).fetch();
		return roles;
    }
    
}