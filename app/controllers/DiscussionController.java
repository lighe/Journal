package controllers;

import java.util.List;
import models.*;
import play.mvc.Before;
import play.mvc.Controller;
public class DiscussionController extends Controller {
    
    @Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
    
    public static void showDiscussion (Long id){
        JournalConfiguration jc = JournalConfiguration.all().first();
        if  (Security.isConnected()){
            User user = Security.getConnectedUser();
            if (Security.isReviewer()){
                Revision rev = Revision.findById(id);
                List<User> users = Discussion.getUserList(rev);
                List<Discussion> comments = Discussion.find("revision", rev).fetch();

                if (users.contains(user)){
                    render("DiscussionController/show.html", comments, jc);
                }
                else{
                    render("/home.html" , jc); //TODO change to error page
                }
            }
            else{
                render("/home.html", jc); //TODO change to error page
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
            if (Security.isReviewer()){
                Revision rev = Revision.findById(id);
                List<User> users = Discussion.getUserList(rev);
                List<Discussion> comments = Discussion.find("revision", rev).fetch();
                Discussion addcomment = new Discussion(rev, comment, user);
                addcomment.save();
                render("DiscussionController/addcomment.html", addcomment, jc);
            }
            else{
                render("/home.html", jc); //TODO change to error page
            }
        }
        else{
            render("/main.html", jc);
        }
	
    }
}