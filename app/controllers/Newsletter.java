package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.mail.EmailException;

import models.Emailer;
import models.Users;
import play.*;
import play.data.Upload;
import play.mvc.*;

public class Newsletter extends Controller {

    public static void index() {
    	String files[][] = getAvailableNewsletters();
    	String filesTable = "<div id='fileTable' name='fileTable'> \n<Table>\n";
    	for(int x = 0; x < files.length-1; x++ ){
    		filesTable += "<tr><td width=20px><center><input type='radio' name="+files[x][0]+" value="+files[x][1]+" onchange='newSelection(this);' /></center></td><td><a href='" + files[x][1] + "'> " + files[x][0] + "</a></td>\n";
    		filesTable += "</tr>\n";
    	}
		filesTable += "</table>\n</div>\n";
    	render(filesTable);
    }
    
    public static void upload(Upload file) {
    	//get the uploaded file parts
		List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
		if(uploads != null){
    		//save file
			// Create file output stream
			String fileDestination = "public\\files\\newsletters\\" + uploads.get(0).getFileName();
    		try{
    			
    			FileOutputStream fos = new FileOutputStream(fileDestination);			
    			//write to the file output stream all the data contained in the upload parts
    			for(int x=0; uploads.size()>x; x++){
    				FileInputStream fis = new FileInputStream(uploads.get(x).asFile());
    				int c;
    	            while ((c = fis.read()) != -1) {
    	               fos.write(c);
    	            }
    			}
    			//Close the output stream
    			fos.close();
    			render(); //render upload page to demonstrate success 
    		}catch (Exception e){//Catch exception if any
    			  System.err.println("Error: " + e.getMessage());
    	    	  index();  //if error, send back to upload page
    		}
    	} else {
    		index(); //if no upload found, send back to upload page
    	} 
    }

	//Email it too... (needs testing with Users email addresses, has worked with hard coded emails)
    public static void sendEmail(String newsletter){
    	String fileDestination = newsletter;
		File newFile = new File(fileDestination);
		for(int x = 0; x < Users.count(); x++){
			Users user = Users.findById(x);
			try {
				Emailer.sendNewsletterTo(user.email, newFile);
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
    }
    
    public static void delete(String newsletter){
    	String fileDestination = newsletter;
		File newFile = new File(fileDestination);
		newFile.delete();
    }
    
    //Returns a list of all files uploaded to the newsletter directory (
    private static String[][] getAvailableNewsletters(){
    	//Directory path here
    	String path = "public\\files\\newsletters\\";
    	 
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles(); 
    	String[][] files = new String[listOfFiles.length+1][2];
    	
    	for (int i = 0; i < listOfFiles.length; i++){
    	   if (listOfFiles[i].isFile()){
    		   files[i][0] = listOfFiles[i].getName();
    		   String uri = listOfFiles[i].getPath();
    		   files[i][1] = uri.replaceAll("\\\\", "/");
    	   }
    	}
    	return files;
    }    
    
}
