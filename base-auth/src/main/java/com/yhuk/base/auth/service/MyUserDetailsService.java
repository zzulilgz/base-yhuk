package com.yhuk.base.auth.service;

import com.yhuk.account.client.service.UserClient;
import com.yhuk.account.object.response.RoleBo;
import com.yhuk.account.object.response.UserRolesBo;
import com.yhuk.account.object.utils.JsonUtils;
import com.yhuk.base.auth.dao.AccountUserDao;
import com.yhuk.base.auth.dao.RoleDao;
import com.yhuk.base.auth.model.MyUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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

    @Autowired
    private UserClient userClient;
    @Autowired
    private AccountUserDao accountUserDao;

    @Autowired
    private RoleDao roleDao;


    public static Map<String,UserRolesBo> usersMap;

    static {
//        usersMap = new HashMap<>();
//        UserRolesBo userRolesBo = new UserRolesBo();
//        userRolesBo.setLoginName("admin");
//        userRolesBo.setPassword("admin");
//        List<RoleBo> roleBos = new ArrayList<>();
//        RoleBo roleBo = new RoleBo();
//        List<ResourceBo> resourceList = new ArrayList<>();
//        resourceList.add(new ResourceBo("resource_1","read"));
//        resourceList.add(new ResourceBo("resource_2","write"));
//        roleBo.setResources(resourceList);
//        roleBos.add(roleBo);
//        userRolesBo.setRoles(roleBos);
//        usersMap.put("admin",userRolesBo);
//
//        UserRolesBo userRolesBo2 = new UserRolesBo();
//        userRolesBo2.setLoginName("test");
//        userRolesBo2.setPassword("test");
//        List<RoleBo> roleBos2 = new ArrayList<>();
//        RoleBo roleBo2 = new RoleBo();
//        List<ResourceBo> resourceList2 = new ArrayList<>();
//        resourceList2.add(new ResourceBo("resource_1","read"));
//        roleBo2.setResources(resourceList2);
//        roleBos2.add(roleBo2);
//        userRolesBo2.setRoles(roleBos2);
//
//        usersMap.put("test",userRolesBo2);
    }

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        logger.info("当前登录人:{}",loginName);
        UserRolesBo userRolesBo = accountUserDao.findUserByLogin(loginName);
        List<RoleBo> roles = roleDao.findByUser(userRolesBo.getId());
        userRolesBo.setRoles(roles);

        if (userRolesBo == null) {
            throw new UsernameNotFoundException("LoginName " + loginName + " not found");
        }
        logger.info("当前登录人详细信息：{}",JsonUtils.toJson(userRolesBo));

        return new MyUserDetails(userRolesBo);
    }
}
