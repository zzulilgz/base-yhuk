package com.yhuk.base.zuul.config;

import com.yhuk.account.client.service.RoleClient;
import com.yhuk.account.object.response.ResourceBo;
import com.yhuk.account.object.response.RoleBo;
import com.yhuk.account.object.utils.JsonUtils;
import com.yhuk.account.object.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description 权限配置
 * @Author gaozhi.liu
 * @Date 2019/5/24 9:57
 * @Version 1.0
 **/
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = LoggerFactory.getLogger(MySecurityMetadataSource.class);
    public static final String MERCHANT_CENTER_ROLES_ALL = "MERCHANT_CENTER_ROLES_ALL_";
    private PathMatcher pathMatcher = new AntPathMatcher();

    private RoleClient roleClient;
    //private CacheComponent cacheComponent;

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public MySecurityMetadataSource(RoleClient roleClient) {
        super();
        this.roleClient = roleClient;
        //this.cacheComponent = cacheComponent;
    }

    private List<RoleBo> loadResourceWithRoles() {
        ResponseUtils.Response<List<RoleBo>> roleListRes = roleClient.findList();
        List<RoleBo> list = roleListRes.getData();

//        if(list != null) {
//            cacheComponent.put(MERCHANT_CENTER_ROLES_ALL, "LIST", list, 180);
//        }
        return list;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();

        logger.info("请求资源：" + url);
        //先从缓存中取角色列表
        //Object objects = cacheComponent.get(MERCHANT_CENTER_ROLES_ALL, "LIST");
        List<RoleBo> roleBoList = loadResourceWithRoles();
        logger.info("roleBoList:{}",JsonUtils.toJson(roleBoList));
//        if (CommonUtils.isNull(objects)) {
//            roleQoList = loadResourceWithRoles();//如果缓存不存在，从API中读取角色列表
//        } else{
//            roleQoList = ( List<RoleQo>)objects;
//        }

        Collection<ConfigAttribute> roles = new ArrayList<>();//有权限的角色列表

        //检查每个角色的资源，如果跟请求资源匹配，则加入角色列表。为后面权限检查提供依据
        if(roleBoList != null && roleBoList.size() > 0) {
            for (RoleBo roleBo : roleBoList) {//循环角色列表
                List<ResourceBo> resourceQos = roleBo.getResources();
                if(resourceQos != null && resourceQos.size() > 0) {
                    for (ResourceBo resourceBo : resourceQos) {//循环资源列表
                        if (resourceBo.getPath() != null && pathMatcher.match("**/"+resourceBo.getPath()+"/**", url)) {
                            ConfigAttribute attribute = new SecurityConfig(roleBo.getName());
                            roles.add(attribute);
                            logger.info("加入权限角色列表===角色资源：{}，角色名称：{}===", resourceBo.getPath(), roleBo.getName());
                            break;
                        }
                    }
                }
            }
        }
        logger.info("roles:{}", JsonUtils.toJson(roles));
        return roles;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
