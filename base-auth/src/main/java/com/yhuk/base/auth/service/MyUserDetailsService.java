package com.yhuk.base.auth.service;

import com.yhuk.account.object.response.ResourceBo;
import com.yhuk.account.object.response.RoleBo;
import com.yhuk.account.object.response.UserRolesBo;
import com.yhuk.account.object.utils.JsonUtils;
import com.yhuk.account.object.utils.ResponseUtils;
import com.yhuk.base.auth.model.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/5/21 17:44
 * @Version 1.0
 **/
@Component
public class MyUserDetailsService implements UserDetailsService {
    public static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    public static Map<String,UserRolesBo> usersMap;

    static {
        usersMap = new HashMap<>();
        UserRolesBo userRolesBo = new UserRolesBo();
        userRolesBo.setLoginName("admin");
        userRolesBo.setPassword("admin");
        List<RoleBo> roleBos = new ArrayList<>();
        RoleBo roleBo = new RoleBo();
        List<ResourceBo> resourceList = new ArrayList<>();
        resourceList.add(new ResourceBo("test1","/resource/test1"));
        roleBo.setResources(resourceList);
        roleBos.add(roleBo);
        userRolesBo.setRoles(roleBos);
        usersMap.put("admin",userRolesBo);


    }
    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        logger.info("当前登录人:{}",loginName);

        UserRolesBo userRolesBo = usersMap.get(loginName);

        if (userRolesBo == null) {
            throw new UsernameNotFoundException("LoginName " + loginName + " not found");
        }
        logger.info("当前登录人详细信息：{}",JsonUtils.toJson(userRolesBo));

        return new MyUserDetails(userRolesBo);
    }
}
