package com.file.upload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.file.util.JpegReader;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ThumbAdd {

	@RequestMapping("/thumbAdd") 
	public String singleFileUpload(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String filePath = request.getParameter("filePath");
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
			//path 경로 가져오기
			 List<String> thumbList = getPath(br);
			 redirectAttributes.addFlashAttribute("thumbList",thumbList);			 
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("list",e.getMessage());
			
		}
		return "redirect:/thumb-success";
	}

	private List<String> getPath(BufferedReader br) throws Exception {
		String line;
		int cnt=0;
		List<String> list = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			//라인 구분자 치환
			String[] arrayLine;
			arrayLine = line.split("\t");
			String imagePatch = arrayLine[0];
			String imageThumbPatch = arrayLine[1];
			
			cnt++;
			//파일 유무 확인
			if(fileCheck(new File(imagePatch))) {
				//추출한 경로 썸네일 작업
				thumbAdd(imagePatch, imageThumbPatch);
				list.add(cnt+"::"+imageThumbPatch+"::성공");
			}else {
				list.add(cnt+"::"+imageThumbPatch+"::실패");
			}
		}
		return list;
	}
	
	//파일 있는지 체크
	public static boolean fileCheck(File file) {
		boolean fileChek = false;
		if (file.isFile()) {
			fileChek = true;
		}else {
			fileChek = false;
		}
		return fileChek;
	}

	private void thumbAdd(String imagePatch, String imageThumbPatch) throws Exception {
		
		File files = new File(imagePatch);
		
		Image srcImg = null;
		JpegReader jpegReader = new JpegReader();
		srcImg = jpegReader.readImage(files);
		
		BufferedImage bimage = new BufferedImage(srcImg.getWidth(null), srcImg.getHeight(null), BufferedImage.TYPE_INT_ARGB);	
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(srcImg, 0, 0, null);
		bGr.dispose();
		
		Thumbnails.of(bimage).size(1024, 768).outputFormat("jpg").toFile(imageThumbPatch);
	}

	@RequestMapping("/thumb-success")
	public String listFile() {
		return "thumb-success";
	}
}