package org.zxh.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.zxh.pojo.UserPojo;

/**
 * Created by xh.zhi on 2018-2-1.
 */
@Mapper
@Component
public interface UserMapper {

    @Insert("insert into user(username, password, nickname, roles) values(#{username},#{password},#{nickname},#{roles})")
    int insert(UserPojo user);

    @Select("select * from user where username =#{userName}")
    UserPojo selectUserByName(@Param("userName") String userName);
}
