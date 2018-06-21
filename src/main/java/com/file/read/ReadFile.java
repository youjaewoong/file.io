package com.file.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {


	public ArrayList<Object> readFile(File inFile, String patch) {
		BufferedReader br = null;
		ArrayList<Object> dataList =  new ArrayList<Object>();
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile),"utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("line::" + line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) try {br.close(); } catch (IOException e) {}
		}
		
		
		return dataList;
	}

	public List<String> allRead(Path path) {
		
		Charset cs = StandardCharsets.UTF_8;
		List<String> list = new ArrayList<String>();
		try{
			list = Files.readAllLines(path, cs);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}