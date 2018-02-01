package org.zxh.service;

import org.zxh.pojo.UserPojo;

/**
 * Created by xh.zhi on 2018-2-1.
 */
public interface UserService {

    //添加用户 username 唯一， 默认 USER 权限
    boolean insert(UserPojo user);

    //查询用户
    UserPojo getUserByName(String userName);
}
