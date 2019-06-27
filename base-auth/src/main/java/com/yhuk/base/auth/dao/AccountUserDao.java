package com.yhuk.base.auth.dao;

import com.yhuk.account.object.response.UserRolesBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/27 13:32
 * @Version 1.0
 **/
@Mapper
public interface AccountUserDao {

    @Select("select * from power_user where login_name=#{loginName}")
    UserRolesBo findUserByLogin(String loginName);
}
