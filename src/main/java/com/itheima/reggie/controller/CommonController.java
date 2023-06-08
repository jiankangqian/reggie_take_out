package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 实现文件的上传
     * @param file
     * @return
     */
    @PostMapping("upload")
    public R<String> upload(MultipartFile file){ //也可以使用@RequestParam(value = "file")
        log.info("file是：{}",file.toString());
        //通过原始文件名获取文件格式后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString() + suffix;
        //如果文件目录不存在，先创建目录
        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        


        //将临时文件转存
        try {
            file.transferTo(new File((basePath + fileName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 实现文件的下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            //输入流，通过输入流读取文件
            fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将文件写回浏览器
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while(((len = fileInputStream.read(bytes)) != -1)){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
