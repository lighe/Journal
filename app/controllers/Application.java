package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
    public static void index() {
        List<Articles> articles = Articles.find(
            "order by title desc"
        ).fetch(10);
		String user = Security.connected();
        render(articles);
    }
    
    public static void discussionShow(int id){
        /**if (getUser().user_ID = (Discussion.recievers_ID || Discussion.senders_ID)){ 
         need to find how to get user id **/ 
        Discussion comment = Discussion.findById(id);
        render (comment);}
      
}
