package controllers;

import java.util.List;
import models.Discussion;
import models.Review;
import models.Revision;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;
public class DiscussionController extends Controller {
    
    @Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
    
    public static void showDiscussion (Long id){
        User user = Security.getConnectedUser();
        if (Security.isReviewer()){
            Revision rev = Revision.findById(id);
            List<User> users = Discussion.getUserList(rev);
            List<Discussion> comments = Discussion.find("revision", rev).fetch();
                        
            if (users.contains(user)){
                render("DiscussionController/show.html", comments);
            }
            else{
                render("/home.html"); //TODO change to error page
            }
        }
        else{
            render("/home.html"); //TODO change to error page
        }
        
     
    }
    
    public static void addDiscussion (Long id, String comment){
        User user = Security.getConnectedUser();
        if (Security.isReviewer()){
            Revision rev = Revision.findById(id);
            List<User> users = Discussion.getUserList(rev);
            List<Discussion> comments = Discussion.find("revision", rev).fetch();
            Discussion addcomment = new Discussion(rev, comment, user);
            addcomment.save();
            render("DiscussionController/addcomment.html", addcomment);
        }
        else{
            render("/home.html"); //TODO change to error page
        }
    }
}
