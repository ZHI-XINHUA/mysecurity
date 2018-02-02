package org.zxh.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zxh.constant.RoleConstant;
import org.zxh.mapper.UserMapper;
import org.zxh.pojo.UserPojo;
import org.zxh.service.UserService;

/**
 * Created by zhixinhua on 18/2/2.
 */
@Service
@Primary
public class BaseUserService implements UserService {

    private final UserMapper userMapper;

    public BaseUserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public boolean insert(UserPojo userPojo) {
        String userName = userPojo.getUsername();
        if(exist(userName))
            return false;
        userPojo.setRoles(RoleConstant.ROLE_USER);//角色
        userPojo.setPassword(encryptPassword(userPojo.getPassword()));//加密
        int result = userMapper.insert(userPojo);
        return result==1;
    }

    @Override
    public UserPojo getByUsername(String userName) {
        return userMapper.getUserByName(userName);
    }

    /**
     * 判断用户是否存在
     * @param username 账号
     * @return 密码
     */
    private boolean exist(String username){
        UserPojo userPojo = userMapper.getUserByName(username);
        return (userPojo != null);
    }

    /**
     * 加密
     * @param password 明文
     * @return
     */
    private String encryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }
}
