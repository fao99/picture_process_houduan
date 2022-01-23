package com.fao.houduan.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fao.houduan.common.Result;
import com.fao.houduan.entity.Picture;
import com.fao.houduan.mapper.PictureMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.print.Book;
import java.util.List;

/**
 * @Author fao
 * @Create 2021--12--11 16:35
 */
@Service
public class PictureService {

    @Resource
    private PictureMapper pictureMapper;

    //插入图片
    public Result addPicture(Picture picture){
        pictureMapper.insert(picture);
        return Result.success();
    }

    //查询全部图片 测试使用
//    public Result listPicture(){
//        List<Picture> list = pictureMapper.selectList(null);
//        System.out.println(list);
//        return Result.success(list);
//    }

    //分页查询图像+根据id查询
    public Result listPicture(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Picture> wrapper = Wrappers.<Picture>lambdaQuery();
        if (StringUtils.isNotBlank(search)){//判断search不为空
            wrapper.eq(Picture::getId,search);
        }
        Page<Picture> picturePage = pictureMapper.selectPage(new Page<>(pageNum, pageSize),wrapper);
        return Result.success(picturePage);
    }


    //删除图像
    public Result deletePicture(Long id){
        pictureMapper.deleteById(id);
        return Result.success();
    }

}
