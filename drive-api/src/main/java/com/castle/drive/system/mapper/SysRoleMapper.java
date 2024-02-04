package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysRole;
import com.castle.drive.system.query.SysRoleQuery;
import com.castle.drive.system.vo.SysRoleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/2 16:55
 * @Classname SysRoleMapper
 * @Description 系统角色 Mapper 接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 系统角色详情
     *
     * @param id
     * @return
     */
    SysRoleVo getSysRoleById(Long id);

    /**
     * 系统角色分页列表
     *
     * @param query
     * @return
     */
    List<SysRoleVo> getSysRolePage(SysRoleQuery query);

}