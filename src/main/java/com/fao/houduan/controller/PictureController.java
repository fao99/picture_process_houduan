package com.fao.houduan.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.fao.houduan.common.Result;
import com.fao.houduan.entity.Picture;
import com.fao.houduan.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * @Author fao
 * @Create 2021--12--11 16:36
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;

    //插入图片
    @PostMapping
    public Result addPicture(@RequestBody Picture picture){
        picture.setCreateTime(new Date());
        System.out.println(picture.toString());
        Result result = pictureService.addPicture(picture);
        return result;
    }


    //分页查询图像+根据id查询
    @GetMapping
    public Result listPicture(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "6") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        Result result = pictureService.listPicture(pageNum,pageSize,search);
        return result;
    }


    //删除图片
    @DeleteMapping("/{id}")
    public Result deletePicture(@PathVariable Long id){
        Result result = pictureService.deletePicture(id);
        return Result.success(result);
    }

    //上传图片
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        //1获取源文件的名称
        String originalFilename = file.getOriginalFilename();

        //定义文件的唯一前缀
        String flag = IdUtil.fastSimpleUUID();

        //2获取上传的路径
        String rootFilePath = System.getProperty("user.dir")+"/src/main/resources/static/images/"+flag+"_"+originalFilename;
        System.out.println(rootFilePath);

        //3文件的写入操作
        FileUtil.writeBytes(file.getBytes(),rootFilePath);

        //4返回结果url
        return Result.success("http://" + "localhost" + ":" + 8081 + "/picture/download/" + flag);//这是文件下载的接口

    }

    //下载
    @GetMapping("/download/{flag}")
    public void getFiles(HttpServletResponse response, @PathVariable String flag){
        //解决canvas的跨域问题
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");

        OutputStream os;  // 新建一个输出流对象
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";  // 定义文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

}
