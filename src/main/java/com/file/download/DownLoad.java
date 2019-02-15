package com.file.download;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DownLoad {
	
	@RequestMapping("/download1") 
	public String singleFileUpload(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		return "downLoad";
	}
}
