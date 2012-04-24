package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class ApplicationController extends Controller {
	
	private static Edition selectedEdition; //this is populated in getBrowseData()

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
	@Before
	static void getBrowseData() {
		BrowseController.getBrowseData();
		selectedEdition = BrowseController.selectedEdition;
	}
	
    public static void index() {
    
		String user = Security.connected();
		
		List<Published> publishedArticles = selectedEdition.getPublished();
        render(publishedArticles);
    }
    
    public static void discussionShow(int id){
        /**if (getUser().user_ID = (Discussion.recievers_ID || Discussion.senders_ID)){ 
         need to find how to get user id **/ 
        Discussion comment = Discussion.findById(id);
        render (comment);}
      
}
