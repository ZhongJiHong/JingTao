package com.jt.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.service.PropertieService;
import com.jt.common.vo.PicUploadResult;

@Controller
public class PicUploadController {

	private static final Logger log = Logger.getLogger(PicUploadController.class);

	@Autowired
	private PropertieService prop;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadPic(MultipartFile uploadFile) {

		PicUploadResult result = new PicUploadResult();
		// 获取文件名
		String fileName = uploadFile.getOriginalFilename();

		// 判断是否为图片格式
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		if (!suffix.matches("^.*(jpg|png|gif|jpeg)$")) {

			result.setError(1);
			return result;
		}

		// 判断是否为垃圾文件或者病毒
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			String height = image.getHeight() + "";
			String width = image.getWidth() + "";

			String url = prop.IMAGE_BASE_URL + "/images/";
			String path = prop.REPOSITORY_PATH + "/images/";

			String middle = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

			// 判断路径是否真实存在
			path += middle;
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 增加随机数字
			String random = (new Random().nextInt(900) + 100) + "";
			url = url + middle + "/" + random + fileName;
			path = path + "/" + random + fileName;

			// 上传
			uploadFile.transferTo(new File(path));

			result.setHeight(height);
			result.setWidth(width);
			result.setUrl(url);
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage() + "{文件上传失败}");
			result.setError(1);
			return result;
		}
	}
}
