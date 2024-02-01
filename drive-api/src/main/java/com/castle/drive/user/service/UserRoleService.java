package com.castle.drive.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.user.dto.UserRoleDto;
import com.castle.drive.user.entity.UserRole;
import com.castle.drive.user.query.UserRoleQuery;
import com.castle.drive.user.vo.UserRoleVo;

/**
 * @author YuLong
 * @Date 2024/2/1 16:59
 * @Classname UserRoleService
 * @Description 用户角色服务接口
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 添加用户角色
     *
     * @param dto
     * @return
     */
    boolean addUserRole(UserRoleDto dto);

    /**
     * 修改用户角色
     *
     * @param dto
     * @return
     */
    boolean updateUserRole(UserRoleDto dto);

    /**
     * 删除用户角色
     *
     * @param id
     * @return
     */
    boolean deleteUserRole(Long id);

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
    Paging<UserRoleVo> getUserRolePage(UserRoleQuery query);
    
}