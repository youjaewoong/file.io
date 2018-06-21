package com.file.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.file.read.ReadFile;
import com.file.write.WriterFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class Upload {
	
	@Value("${img.path}")
	private String filePath;
	
	@RequestMapping("/")
	public String index() {
		return "upload";
	}
	
	@RequestMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			//파일 업로드
			WriterFile writerFile = new WriterFile();
			writerFile.writeFile(file, filePath, file.getOriginalFilename());
			
			//파일 한번에 읽기
			ReadFile readFile = new ReadFile();
			Path path = Paths.get(filePath+file.getOriginalFilename());
			List<String> list = readFile.allRead(path);
			
			redirectAttributes.addFlashAttribute("list",list);
			redirectAttributes.addFlashAttribute("filePath",filePath+file.getOriginalFilename());
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("list","text 파일이 없거나 파일 포맷이 잘못되었습니다.");
		}
		return "redirect:/list-file";
	}

	@RequestMapping("/list-file")
	public String listFile() {
		return "list-file";
	}
}