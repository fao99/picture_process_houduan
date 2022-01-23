package com.fao.houduan.service;

import com.fao.houduan.common.Result;
import com.fao.houduan.entity.Picture;
import com.fao.houduan.mapper.PictureMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author fao
 * @Create 2021--12--12 17:12
 */
@Service
public class ProcessService {
    @Resource
    private PictureMapper pictureMapper;

    //根据id查询图片
    public Result getPictureById(Long id){
        Picture picture = pictureMapper.selectById(id);
        return Result.success(picture);
    }
}
