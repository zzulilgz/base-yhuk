package com.yhuk.base.auth.dao;

import com.yhuk.account.object.response.RoleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/6/27 13:37
 * @Version 1.0
 **/
@Mapper
public interface RoleDao {

    @Select("select * from power_role where id in (" +
            "select role_id from power_role_user where user_id=#{userId})")
    List<RoleBo> findByUser(Integer userId);
}
