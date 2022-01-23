package com.fao.houduan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author fao
 * @Create 2021--12--11 16:31
 */
@TableName("picture")//和数据库的表的字段一一对应
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String src;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
