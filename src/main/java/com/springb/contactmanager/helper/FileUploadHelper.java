package com.springb.contactmanager.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadHelper {
	
	public String FILE_PATH ="";
	
	public boolean fileUpload(MultipartFile file) {
//		setting flag for file
		boolean isFileCreated = false;
		
		try {
//			read file 
			InputStream inputStream = file.getInputStream();
			byte file_data[] = new byte[inputStream.available()];
			inputStream.read(file_data);
			
//			write file
			FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH+ File.separator+ file.getOriginalFilename());
			fileOutputStream.write(file_data);
//			close file
			fileOutputStream.close();
			isFileCreated= true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isFileCreated= false;
		}
		return isFileCreated;
		
	}

}
