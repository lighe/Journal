package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class BrowseController extends Controller {
	
	public static Edition selectedEdition;

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
	//might be best to move this in application controlelr
	@Before
	static void getBrowseData() {
		List<Volume> volumes = Volume.find("order by ID desc").fetch(5); //this repeats db values if not enough entries in db. Solutions?
		Volume selectedVolume = volumes.get(0);
		List<Edition> editions = selectedVolume.getEditions();
		Edition selectedEd = editions.get(0);
		
		renderArgs.put("volumes", volumes);
		renderArgs.put("editions", editions);
		
		selectedEdition = selectedEd;
	}
	
	
    public static void index(Long selectedVolumeID, Long selectedEditionID) {
		
		String user = Security.connected();
		
		//check for case when no volumes or no editions
		
		//Find all volumes
		List<Volume> volumes = Volume.find("order by ID desc").fetch();
		
		//If no volume id passed than select first found volume
		if(selectedVolumeID==null){
			selectedVolumeID = volumes.get(0).id;	
		}
		
		Edition selectedEdition;
		
		//If no edition id passed, select first edition of selected volume
		if(selectedEditionID==null) {
			selectedEdition = volumes.get(0).getEditions().get(0); 
			selectedEditionID = selectedEdition.id;
		}
		else selectedEdition = Edition.findById(selectedEditionID);
		
		List<Published> publishedArticles = selectedEdition.getPublished();
		
		render(volumes, publishedArticles, selectedVolumeID, selectedEditionID);
	}	
}
