package com.castle.drive.user.controller;

import com.castle.drive.auth.annotation.Permission;
import com.castle.drive.common.enums.SysLogType;
import com.castle.drive.framework.annotation.Log;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.framework.response.ApiResult;
import com.castle.drive.user.dto.UserDto;
import com.castle.drive.user.query.UserQuery;
import com.castle.drive.user.service.UserService;
import com.castle.drive.user.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author YuLong
 * @Date 2024/2/1 17:10
 * @Classname UserController
 * @Description 用户信息控制器
 */
@Slf4j
@RestController
@Tag(name = "用户信息")
@RequestMapping("/admin/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 添加用户信息
     *
     * @param userDto
     * @return
     */
    @Log(value = "添加用户信息", type = SysLogType.ADD)
    @PostMapping("/addUser")
    @Operation(summary = "添加用户信息")
    @Permission("user:add")
    public ApiResult<Boolean> addUser(@Valid @RequestBody UserDto userDto) {
        boolean flag = userService.addUser(userDto);
        return ApiResult.result(flag);
    }

    /**
     * 修改用户信息
     *
     * @param userDto
     * @return
     */
    @Log(value = "修改用户信息", type = SysLogType.UPDATE)
    @PostMapping("/updateUser")
    @Operation(summary = "修改用户信息")
    @Permission("user:update")
    public ApiResult<Boolean> updateUser(@Valid @RequestBody UserDto userDto) {
        boolean flag = userService.updateUser(userDto);
        return ApiResult.result(flag);
    }

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @Log(value = "删除用户信息", type = SysLogType.DELETE)
    @PostMapping("/deleteUser/{id}")
    @Operation(summary = "删除用户信息")
    @Permission("user:delete")
    public ApiResult<Boolean> deleteUser(@PathVariable Long id) {
        boolean flag = userService.deleteUser(id);
        return ApiResult.result(flag);
    }

    /**
     * 获取用户信息详情
     *
     * @param id
     * @return
     */
    @PostMapping("/getUser/{id}")
    @Operation(summary = "用户信息详情")
    @Permission("user:info")
    public ApiResult<UserVo> getUser(@PathVariable Long id) {
        UserVo userVo = userService.getUserById(id);
        return ApiResult.success(userVo);
    }

    /**
     * 用户信息分页列表
     *
     * @param query
     * @return
     */
    @PostMapping("/getUserPage")
    @Operation(summary = "用户信息分页列表")
    @Permission("user:page")
    public ApiResult<Paging<UserVo>> getUserPage(@Valid @RequestBody UserQuery query) {
        Paging<UserVo> paging = userService.getUserPage(query);
        return ApiResult.success(paging);
    }

}
