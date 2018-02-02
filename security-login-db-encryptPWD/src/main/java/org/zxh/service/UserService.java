package org.zxh.service;


import org.zxh.pojo.UserPojo;

public interface UserService {

    /**
     * 添加新用户
     *
     * username 唯一， 默认 USER 权限
     */
    boolean insert(UserPojo userPojo);

    /**
     * 查询用户信息
     * @param username 账号
     * @return UserEntity
     */
    UserPojo getByUsername(String username);

}
