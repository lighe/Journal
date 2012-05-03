package controllers;

import java.util.*;
import models.*;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With({Secure.class, Security.class, ApplicationController.class})
public class PublishedController extends Controller {

    @Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
    
    /*public static void unpublishedShow(Long id){
        User user = Security.getConnectedUser();
        
        if (Security.isReviewer()){
            List<Article> unpublished = Article.find("published", false).fetch();                 
                render("unpublished/index.html", unpublished);
                    }
                        
                    }
                } 
                */
    public static void unpublishedShow(Long id){
       
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
            render("unpublished/index.html", unpublished);
                    }
                        
                    }
            }
    

  /*  public static void unpublishedreviewedShow(){
        /*Shows a list of possible unpublished articles that could be published 
         * seen only by editor
         * Needs author to review at three articles first 
         * As well as the article recieving at least three reviews, with their score shown.
         * A button that says publish that adds the article into the next journal.
         */ 
    
