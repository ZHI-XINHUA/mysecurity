package org.zxh.service.impl;

import lombok.extern.log4j.Log4j;
import org.zxh.mapper.UserMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.zxh.constant.RoleConstant;
import org.zxh.pojo.UserPojo;
import org.zxh.service.UserService;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@Service
@Primary
@Log4j
public class BaseUserService implements UserService {
    private final UserMapper userMapper;

    public BaseUserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @Override
    public boolean insert(UserPojo user) {
        String userName = user.getUsername();
        if(existUser(userName))
            return  false;
        user.setRoles(RoleConstant.ROLE_USER); //保存角色
        int result = userMapper.insert(user);
        return result==1;
    }

    @Override
    public UserPojo getUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    // 判断用户是否存在
    private boolean existUser(String userName){
        UserPojo user = getUserByName(userName);
        return (user==null)? false:true;
    }
}
