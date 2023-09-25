package com.rba.cc.fileManager;

import java.io.File;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Autowired;

import com.rba.cc.logger.CreditCardLogger;

public class FileManager {
	
	@Autowired
	CreditCardLogger creditCardLogger;

	private String filename;
	private String dirName;
	private String content;
	
	private static String cardReqDir = "card_requests_files"; 
	
	public FileManager(String filename, String dirName, String content) {
		super();
		this.filename = filename;
		this.dirName = dirName;
		this.content = content;
	}

	public void saveFile() {
//		creditCardLogger.logInfo("saveFile", getClass());		
		createDirectory();
		if(isDirEmpty()) {
			saveFileToDirectory();
		}
	}
	
    public void deleteFilesFromDir() {
    	File dir = new File(getPathDir());
    	for (File file: dir.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }
	}
	
	private void createDirectory() {
//		creditCardLogger.logInfo("createDirectory", getClass());
		if (!directoryExists()) {
			try {
				File dir = new File(getPathDir());
				boolean dirCreated = dir.mkdir();				
//				creditCardLogger.logInfo("createDirectory dirCreated = " + dirCreated , getClass());
			} catch (Exception e) {
//				creditCardLogger.logError("createDirectory error = " + e.toString(), getClass());
			}	
		}
	}
	
	private void saveFileToDirectory() {
		try {
		      FileWriter writer = new FileWriter(getPathDir() + File.separator + this.filename);
		      writer.write(this.content);
		      writer.close();
		    } catch (Exception e) {
		    	
		    }
	}
	
	
	private boolean isDirEmpty() {
		boolean dirEmpty = true;
		try {
			 File directory = new File(getPathDir());
			  
		        if (directory.isDirectory()) {		            
		            String arr[] = directory.list();
		            if (arr.length > 0) {
		            	dirEmpty = false;
		            }		            
		        }
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return dirEmpty;
	}
	
	
	private boolean directoryExists() {
//		creditCardLogger.logInfo("directoryExists", getClass());
		boolean dirExists = false;
		
		try {
			File dir = new File(getPathDir());
			dirExists = dir.exists();
			
		} catch (Exception e) {
//			creditCardLogger.logError("directoryExists error = " + e.toString(), getClass());
		}		
//		creditCardLogger.logInfo("directoryExists = " + dirExists , getClass());
		return dirExists;
	}
	
	public String getPathDir () {
		String pathDir = System.getProperty("user.dir") + File.separator + cardReqDir + File.separator + this.dirName;
		System.out.println(pathDir);
		return pathDir;
	}

}
