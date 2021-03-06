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
				System.out.println("Upload error:" + e.getMessage());
    			  System.err.println("Error: " + e.getMessage());
    	    		return false;
    		}
    	} else {
			System.out.println("No uploads");
    		return false;
    	} 
    }
    
    public static boolean isPDF(String fileName){
    	if(fileName.length()<=4){
    		return false;
    	} else if((fileName.substring(fileName.length()-4, fileName.length())).contentEquals(".pdf")){
    		return true;
    	} else {
    		return false;
    	}
    }
    
	public static boolean isDoc(String fileName) {
		if(fileName.length()<=4){
    		return false;
    	} 
		if((fileName.substring(fileName.length()-4, fileName.length())).contentEquals(".doc")){
    		return true;
    	} 
		if((fileName.substring(fileName.length()-5, fileName.length())).contentEquals(".docx")){
    		return true;
    	} 
		return false;
	}
    
    public static boolean isLaTEX(String fileName){
    	if(fileName.length()<=4){
    		return false;
    	} else if((fileName.substring(fileName.length()-4, fileName.length())).contentEquals(".tex")){
    		return true;
    	} else {
    		return false;
    	}
    }
    
	public static void download() {
		  
		  /*
		HttpResponse res = WS.url("http://localhost:9000/public/files/articles/testing%20again1.pdf").get();
		
		res.setContentType("APPLICATION/OCTET-STREAM");
		res.setContentLength((int)file.length());
		res.setHeader("Content-Disposition", 
						   "attachment; filename=\"Article.pdf\"");
		 
		FileInputStream is = res.getStream();
						
		ServletOutputStream out = res.getOutputStream();
		 
		int i;
		while((i = is.read()) != -1)
		{
			out.write(i);
		}
		 
		is.close();
		out.close();
		*/
		
		
		
		
		 
		
		
		
		
		
		
		
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
