package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;
import java.text.*;
import java.util.logging.Level;

import models.*;
import org.apache.commons.mail.EmailException;

@With({Secure.class, Security.class, ApplicationController.class})
public class VolumeController extends Controller {
	
    public static void index() {
    
		User user = Security.getConnectedUser();
		List<Volume> volumes = Volume.find("order by ID desc").fetch();
		Volume selectedVolume = volumes.get(0);
		List<Edition> editions = selectedVolume.getEditions();
		render(volumes, editions);
    } 

    public static void show(Long id) {
    	Volume volumes = Volume.findById(id);
    	render(volumes);
	}

	public static void add(Long volumeId) {
		//if no volumeid present redirect
		if(validation.required(volumeId).error != null) {
			index();
		}
		render(volumeId);
	}
	
	public static void edit(Long volumeId, Long editionId) {
		
	}

	public static void delete(String id, String volume){
    	int volumeId = Integer.getInteger(id);
    	models.Volume.delete("volume", volumeId);
		FileManagment.delete(volume);
		render();
    }	 

    public static void deleteEdition(String id, String edition){
    	int editionId = Integer.getInteger(id);
    	models.Edition.delete("edition", editionId);
		FileManagment.delete(edition);
		render();
    }	

}
