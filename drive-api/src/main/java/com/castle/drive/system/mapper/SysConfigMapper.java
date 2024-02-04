package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysConfig;
import com.castle.drive.system.query.SysConfigQuery;
import com.castle.drive.system.vo.SysConfigVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/2 17:07
 * @Classname SysConfigMapper
 * @Description 系统配置Mapper接口
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 系统配置详情
     *
     * @param id
     * @return
     */
    SysConfigVo getSysConfigById(Long id);

    /**
     * 系统配置分页列表
     *
     * @param query
     * @return
     */
    List<SysConfigVo> getSysConfigPage(SysConfigQuery query);

}