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
        List<Volume> volumes = Volume.find(
            "order by ID desc"
        ).fetch(5); //this repeats db values if not enough entries in db. Solutions?
		String user = Security.connected();
		Volume selectedVolume = volumes.get(0);
		List<Edition> editions = selectedVolume.getEditions();
		Edition selectedEdition = editions.get(0);
		List<Published> publishedArticles = selectedEdition.getPublished();
        render(volumes, editions, publishedArticles);
    }
    
    public static void discussionShow(int id){
        /**if (getUser().user_ID = (Discussion.recievers_ID || Discussion.senders_ID)){ 
         need to find how to get user id **/ 
        Discussion comment = Discussion.findById(id);
        render (comment);}
    
    public static void unpublishedShow(){
        /*add user login check*/
        /*add month check for tw0 months to force these to be chosen */
       List unpublished = Published.find("published", false).fetch();
        render ("unpublished/index.html", unpublished);
    } 
    
}
