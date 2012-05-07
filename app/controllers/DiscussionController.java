package controllers;

import models.*;

import play.*;
import play.mvc.*;

import java.util.*;

@With({ApplicationController.class, Security.class})
public class DiscussionController extends Controller {
    
    public static void showDiscussion (Long id){
        JournalConfiguration jc = JournalConfiguration.all().first();
        if  (Security.isConnected()){
            User user = Security.getConnectedUser();
            if (Security.isReviewer()){
                Revision rev = Revision.findById(id);
                List<User> users = Discussion.getUserList(rev);
                List<Discussion> comments = Discussion.find("revision", rev).fetch();

                if (users.contains(user)){
                    render(comments, jc);
                }
                else{
                   // render("/home.html" , jc); //TODO change to error page
                }
            }
            else{
               // render("/home.html", jc); //TODO change to error page
            }
        }
        else{
            render("/main.html", jc);
        }
        
     
    }
    
    public static void addDiscussion (Long id, String comment){
        JournalConfiguration jc = JournalConfiguration.all().first();
				
        if  (Security.isConnected()){
            User user = Security.getConnectedUser();
            if (Security.isReviewer() || Security.isAuthor()){
                Revision rev = Revision.findById(id);
                //List<Discussion> comments = Discussion.find("revision", rev).fetch();
                Discussion addcomment = new Discussion(rev, comment, user);
                addcomment.save();
            }
		}
	
    }
}