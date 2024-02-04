package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysDict;
import com.castle.drive.system.query.SysDictAppQuery;
import com.castle.drive.system.query.SysDictQuery;
import com.castle.drive.system.vo.AppSysDictVo;
import com.castle.drive.system.vo.SysDictVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/4 10:04
 * @Classname SysDictMapper
 * @Description 字典数据 Mapper 接口
 */
@Mapper
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 字典数据详情
     *
     * @param id
     * @return
     */
    SysDictVo getSysDictById(Long id);

    /**
     * 字典数据分页列表
     *
     * @param query
     * @return
     */
    List<SysDictVo> getSysDictPage(SysDictQuery query);

    /**
     * App字典数据列表
     *
     * @param query
     * @return
     */
    List<AppSysDictVo> getAppSysDictList(SysDictAppQuery query);

}
