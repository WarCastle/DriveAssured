package com.castle.drive.user.controller;

import com.castle.drive.auth.annotation.Permission;
import com.castle.drive.common.enums.SysLogType;
import com.castle.drive.framework.annotation.Log;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.framework.response.ApiResult;
import com.castle.drive.user.dto.UserRoleDto;
import com.castle.drive.user.query.UserRoleQuery;
import com.castle.drive.user.service.UserRoleService;
import com.castle.drive.user.vo.UserRoleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author YuLong
 * @Date 2024/2/1 17:19
 * @Classname UserRoleController
 * @Description 用户角色控制器
 */
@Slf4j
@RestController
@Tag(name = "用户角色")
@RequestMapping("/admin/userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    /**
     * 添加用户角色
     *
     * @param dto
     * @return
     */
    @Log(value = "添加用户角色", type = SysLogType.ADD)
    @Operation(summary = "添加用户角色")
    @PostMapping("/addUserRole")
    @Permission("user:role:add")
    public ApiResult<Boolean> addUserRole(@Valid @RequestBody UserRoleDto dto) {
        log.info("添加用户角色：{}", dto);
        boolean flag = userRoleService.addUserRole(dto);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户角色
     *
     * @param dto
     * @return
     */
    @Log(value = "修改用户角色", type = SysLogType.UPDATE)
    @Operation(summary = "修改用户角色")
    @PostMapping("/updateUserRole")
    @Permission("user:role:update")
    public ApiResult<Boolean> updateUserRole(@Valid @RequestBody UserRoleDto dto) {
        log.info("修改用户角色：{}", dto);
        boolean flag = userRoleService.updateUserRole(dto);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户角色
     *
     * @param id
     * @return
     */
    @Log(value = "删除用户角色", type = SysLogType.DELETE)
    @Operation(summary = "删除用户角色")
    @PostMapping("/deleteUserRole/{id}")
    @Permission("user:role:delete")
    public ApiResult<Boolean> deleteUserRole(@PathVariable Long id) {
        log.info("删除用户角色：{}", id);
        boolean flag = userRoleService.deleteUserRole(id);
        return ApiResult.result(flag);
    }

    /**
     * 用户角色详情
     *
     * @param id
     * @return
     */
    @Operation(summary = "用户角色详情")
    @PostMapping("/getUserRole/{id}")
    @Permission("user:role:info")
    public ApiResult<UserRoleVo> getUserRole(@PathVariable Long id) {
        log.info("用户角色详情：{}", id);
        UserRoleVo userRoleVo = userRoleService.getUserRoleById(id);
        return ApiResult.success(userRoleVo);
    }

    /**
     * 用户角色分页列表
     *
     * @param query
     * @return
     */
    @Operation(summary = "用户角色分页列表")
    @PostMapping("/getUserRolePage")
    @Permission("user:role:page")
    public ApiResult<Paging<UserRoleVo>> getUserRolePage(@Valid @RequestBody UserRoleQuery query) {
        log.info("用户角色分页列表：{}", query);
        Paging<UserRoleVo> paging = userRoleService.getUserRolePage(query);
        return ApiResult.success(paging);
    }

}
