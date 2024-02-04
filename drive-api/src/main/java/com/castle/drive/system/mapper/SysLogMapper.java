package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysLog;
import com.castle.drive.system.query.SysLogQuery;
import com.castle.drive.system.vo.SysLogVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/4 10:19
 * @Classname SysLogMapper
 * @Description 系统日志 Mapper 接口
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 系统日志详情
     *
     * @param id
     * @return
     */
    SysLogVo getSysLogById(Long id);

    /**
     * 系统日志分页列表
     *
     * @param query
     * @return
     */
    List<SysLogVo> getSysLogPage(SysLogQuery query);

}
