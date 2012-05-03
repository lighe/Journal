package controllers;

import java.util.*;
import models.*;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With({Secure.class, Security.class, ApplicationController.class})
public class PublishedController extends Controller {
    
    public static void unpublishedShow(){
       
        User user = Security.getConnectedUser();
        if (Security.isReviewer()){
           // List<Article> forceUnpublished = Arrays.asList();
            ArrayList<Article> forceUnpublished = new ArrayList<Article>();


            Calendar now =Calendar.getInstance();
            now.add(Calendar.MONTH, -2);
            Date cutoffdate = now.getTime(); 
            //int datecheck = latestRev.date.compareTo(cutoffdate);      
            List<Article> unpublished = Article.find("published", false).fetch();                 
                for(int x=0; x< unpublished.size(); x++){
                    Article datecheck = unpublished.get(x);
                    Revision latestRev = datecheck.getLatestRevision(datecheck); 
                        if (latestRev.date.compareTo(cutoffdate) < 0 ){
                            forceUnpublished.add(datecheck);
                        }             
                }
            if(forceUnpublished != null){
                unpublished = forceUnpublished;
            }                  
			
			long allowed = 3- SelectedArticle.count("user = ?", Security.getConnectedUser());
            render("unpublished/index.html", unpublished, allowed);
		}
			
	}
}
    

