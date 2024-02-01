package com.castle.drive.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.drive.framework.page.Paging;
import com.castle.drive.user.dto.AppUserHeadDto;
import com.castle.drive.user.dto.AppUserNicknameDto;
import com.castle.drive.user.dto.UserDto;
import com.castle.drive.user.entity.User;
import com.castle.drive.user.query.UserQuery;
import com.castle.drive.user.vo.AppUserVo;
import com.castle.drive.user.vo.UserVo;

/**
 * @author YuLong
 * @Date 2024/2/1 15:31
 * @Classname UserService
 * @Description 用户信息服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 根据微信openid获取用户
     *
     * @param openid
     * @return
     * @throws Exception
     */
    User getUserByOpenid(String openid);

    /**
     * 根据账号获取用户
     *
     * @param username
     * @return
     * @throws Exception
     */
    User getUserByUsername(String username);

    /**
     * 添加用户信息
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    boolean addUser(UserDto userDto);

    /**
     * 修改用户信息
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    boolean updateUser(UserDto userDto);

    /**
     * 删除用户信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUser(Long id);

    /**
     * 用户信息详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    UserVo getUserById(Long id);

    /**
     * 用户信息分页列表
     *
     * @param query
     * @return
     * @throws Exception
     */
    Paging<UserVo> getUserPage(UserQuery query);

    /**
     * 获取App用户信息
     *
     * @return
     * @throws Exception
     */
    AppUserVo getProfile();

    /**
     * 修改用户头像
     *
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateHead(AppUserHeadDto dto);

    /**
     * 修改用户昵称
     *
     * @param dto
     * @return
     * @throws Exception
     */
    boolean updateNickname(AppUserNicknameDto dto);
}
