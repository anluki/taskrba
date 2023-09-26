package com.rba.cc.fileManager;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.rba.cc.Util.Constants;
import com.rba.cc.logger.CreditCardLogger;

public class FileManager {
	
	private String filename;
	private String dirName;
	private String content;
	private CreditCardLogger creditCardLogger;
	
	private static String cardReqDir = "card_requests_files"; 
	
	public FileManager(String filename, String dirName, String content, CreditCardLogger creditCardLogger) {
		super();
		this.filename = filename;
		this.dirName = dirName;
		this.content = content;
		this.creditCardLogger = creditCardLogger;
	}
	

	public FileManager(String dirName, CreditCardLogger creditCardLogger) {
		super();
		this.dirName = dirName;
		this.creditCardLogger = creditCardLogger;
	}



	public void saveFile() {
		creditCardLogger.logInfo("saveFile", getClass());		
		createDirectory();
		saveFileToDirectory();
	}
	
	public void setActiveFileToInactive() {
		String activeFile = getActiveFile();
		String inactiveFileName = Constants.NEAKTIVNA + "_" + activeFile;		
		try {
			File oldFileName = new File(getFullPathWithDir(activeFile));
			File newFileName = new File(getFullPathWithDir(inactiveFileName));
			
			boolean renameSuccess = oldFileName.renameTo(newFileName);
			
			if(renameSuccess) {
				creditCardLogger.logInfo("setActiveFileToInactive renamed " + activeFile + ">>>" + inactiveFileName , getClass());	
			} else {
				creditCardLogger.logWarn("setActiveFileToInactive rename file not successfull!", getClass());
			}
			
		} catch (Exception e) {
			creditCardLogger.logError("setActiveFileToInactive error = " + e.toString(), getClass());
		}
		
	}
	
	private String getActiveFile() {
		String ret = null; 
		List<String> filenames = getFileNamesFromDir();
		for (String string : filenames) {
			if(string.startsWith(this.dirName)) {
				ret = string;
				break;
			}
		}
		return ret;
	}
	
	public List<String> getFileNamesFromDir (){
		List<String> filenames = new ArrayList<String>();
		if(!isDirEmpty()) {
			filenames = Stream.of(new File(getPathDir()).listFiles())
					.filter(file -> !file.isDirectory())
					.map(File::getName)				
				    .collect(Collectors.toList());				
		}		
		return filenames;		
	}
	
	
	
 	
	private void createDirectory() {
		creditCardLogger.logInfo("createDirectory", getClass());
		if (!directoryExists()) {
			try {
				File dir = new File(getPathDir());
				boolean dirCreated = dir.mkdir();				
				creditCardLogger.logInfo("createDirectory dirCreated = " + dirCreated , getClass());
			} catch (Exception e) {
				creditCardLogger.logError("createDirectory error = " + e.toString(), getClass());
			}	
		}
	}
	
	private void saveFileToDirectory() {
		try {
			  String path = getPathDir() + File.separator + this.filename;
		      FileWriter writer = new FileWriter(path);
		      writer.write(this.content);
		      writer.close();
		      creditCardLogger.logInfo("saveFileToDirectory = " + path, getClass());
		    } catch (Exception e) {
		    	creditCardLogger.logError("saveFileToDirectory error = " + e.toString(), getClass());
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
			creditCardLogger.logError("isDirEmpty error = " + e.toString(), getClass());
		}	
		
		creditCardLogger.logInfo("isDirEmpty = " + dirEmpty, getClass());
		return dirEmpty;
	}
	
	
	private boolean directoryExists() {
		creditCardLogger.logInfo("directoryExists", getClass());
		boolean dirExists = false;
		
		try {
			File dir = new File(getPathDir());
			dirExists = dir.exists();
			
		} catch (Exception e) {
			creditCardLogger.logError("directoryExists error = " + e.toString(), getClass());
		}		
		creditCardLogger.logInfo("directoryExists = " + dirExists , getClass());
		return dirExists;
	}
	
	private String getPathDir () {		
		return System.getProperty("user.dir") + File.separator + cardReqDir + File.separator + this.dirName;
	}
	
	private String getFullPathWithDir (String filename) {
		return getPathDir() + File.separator + filename;
	}
	
}
