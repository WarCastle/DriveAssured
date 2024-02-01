package com.castle.drive.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.drive.user.entity.UserRole;
import com.castle.drive.user.query.UserRoleQuery;
import com.castle.drive.user.vo.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YuLong
 * @Date 2024/2/1 16:21
 * @Classname UserRoleMapper
 * @Description 用户角色Mapper接口
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 用户角色详情
     *
     * @param id
     * @return
     */
    UserRoleVo getUserRoleById(Long id);

    /**
     * 用户角色分页列表
     *
     * @param query
     * @return
     */
    List<UserRoleVo> getUserRolePage(UserRoleQuery query);

}