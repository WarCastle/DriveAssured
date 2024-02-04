package com.castle.drive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.system.entity.SysUser;
import com.castle.drive.system.query.SysUserQuery;
import com.castle.drive.system.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/2 16:47
 * @Classname SysUserMapper
 * @Description 系统用户Mapper接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 系统用户详情
     *
     * @param id
     * @return
     */
    SysUserVo getSysUserById(Long id);

    /**
     * 系统用户分页列表
     *
     * @param query
     * @return
     */
    List<SysUserVo> getSysUserPage(SysUserQuery query);

    /**
     * 根据用户名获取登录用户对象
     *
     * @param username
     * @return
     */
    SysUser getSysUserByUsername(String username);

    /**
     * 根据角色ID获取用户数量
     *
     * @param roleId
     * @return
     */
    Integer getCountByRoleId(Long roleId);

}

