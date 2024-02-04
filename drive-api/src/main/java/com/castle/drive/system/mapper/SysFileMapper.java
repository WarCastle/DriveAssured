package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysFile;
import com.castle.drive.system.query.SysFileQuery;
import com.castle.drive.system.vo.SysFileVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/4 10:15
 * @Classname SysFileMapper
 * @Description 系统文件Mapper接口
 */
@Mapper
public interface SysFileMapper extends BaseMapper<SysFile> {

    /**
     * 系统文件详情
     *
     * @param id
     * @return
     */
    SysFileVo getSysFileById(Long id);

    /**
     * 系统文件分页列表
     *
     * @param query
     * @return
     */
    List<SysFileVo> getSysFilePage(SysFileQuery query);

}