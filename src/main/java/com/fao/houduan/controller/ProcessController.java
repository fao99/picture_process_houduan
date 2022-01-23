package com.fao.houduan.controller;

import com.fao.houduan.common.Result;
import com.fao.houduan.entity.Picture;
import com.fao.houduan.service.PictureService;
import com.fao.houduan.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author fao
 * @Create 2021--12--12 17:12
 */
@RestController
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @CrossOrigin    //默认对所有ip都有效,等于 @CrossOrigin(origins = "*")
    @PostMapping("/getById/{id}")//根据id查询图片
    public Result getById(@PathVariable Long id, HttpServletResponse response){
        Result pictureById = processService.getPictureById(id);
        //解决canvas的跨域问题
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Cache-Control","no-cache");
        return pictureById;
    }


}
