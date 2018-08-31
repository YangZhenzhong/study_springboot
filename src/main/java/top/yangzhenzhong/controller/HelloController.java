package top.yangzhenzhong.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/file")
public class HelloController {
	static final org.slf4j.Logger logger = LoggerFactory.getLogger(HelloController.class);
	/**
	 * 单文件上传
	 * @param file
	 * @param request
	 * @return
	 */
	@PostMapping("/upload")
	public YzzResult upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        if (!file.isEmpty()) {
            String saveFileName = file.getOriginalFilename();
            File saveFile = new File(request.getSession().getServletContext().getRealPath("/upload/") + saveFileName);
            logger.info("fileContext:{} file",request.getSession().getServletContext());
            logger.info("fileRealPath{}",request.getSession().getServletContext().getRealPath("/upload/"));
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(saveFile));
                out.write(file.getBytes());
                out.flush();
                out.close();
                return YzzResult.ok("上传成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return YzzResult.errorException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                return YzzResult.errorException(e.getMessage());
            }
        } else {
            return YzzResult.errorMsg("文件为空");
        }
	}
}
