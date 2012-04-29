package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import play.data.Upload;

/**
 * @author Ioan
 * This model manages the uploading and moving of files
 */
public class FileManagment {
	
	/**
     * Upload method for files.  Returns true if file was successfully uploaded
     * @param uploads A list of file parts
     * @param destinationPrefix The directory to where the file is to be stored
     * @return true if successfully uploaded
     */
    public static boolean upload(List<Upload> uploads, String destinationPrefix, String filename) {
		if(uploads != null){
    		//save file
			// Create file output stream
			String fileDestination = destinationPrefix + filename;
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
        		return true;
    		}catch (Exception e){//Catch exception if any
    			  System.err.println("Error: " + e.getMessage());
    	    		return false;
    		}
    	} else {
    		return false;
    	} 
    }
    
    /**
     * Deletes files
     * @param path Path to the file to delete
     */
    public static void delete(String path){
    	String fileDestination = path;
		File file = new File(fileDestination);
		file.delete();
    }
}
