package jobs;
 
import play.jobs.*;
import org.apache.commons.mail.EmailException;
import java.util.*;
import models.*;

@Every("24h")
public class MyJob extends Job {
    
    public void doJob() {
		List<Article> unpublishedAll = Article.find("published", false).fetch();
		JournalConfiguration jc  = JournalConfiguration.all().first();
		Date today = new Date();
				
		for(int i =0; i<unpublishedAll.size(); i++) {
			List<Review> reviews = unpublishedAll.get(i).getLatestRevision(unpublishedAll.get(i)).getReviews();
			if(reviews.size() > 3) {
				boolean allOldEnoughAndNotVisible = true;
				for(int j=0; j<reviews.size(); j++) {
					Date reviewDate = new Date(reviews.get(j).date);
					if( (today.getTime() - reviewDate.getTime()) /1000 * 60 * 60 * 24 > 7 )	allOldEnoughAndNotVisible = false;
					if(reviews.get(i).visible) allOldEnoughAndNotVisible = false;
				}
				if(allOldEnoughAndNotVisible) {
					try {
						Emailer.sendEmailTo(reviews.get(0).revision.article.user.email, "system@journal",  "Please log in to your "+jc.journalName+" control panel to see the latest reviews for your article",  "New reviews available");
					} catch (EmailException ex) {

					} 
				}
			}
		}
    }
    
}