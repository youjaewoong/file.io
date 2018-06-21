package com.file.write;

import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class WriterFile {
	
	private FileOutputStream fos;

	public void writeFile(MultipartFile mulFile, String path, String fileName) throws Exception{
			byte fileData[] = mulFile.getBytes();
			fos = new FileOutputStream(path + "\\" +  fileName);
			fos.write(fileData);
			if(fos != null){
				try{
					fos.close();
				}catch(Exception e){}
			}
	}
}