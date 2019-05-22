package com.yhuk.base.auth;

import com.yhuk.account.object.response.UserRolesBo;
import com.yhuk.account.object.utils.ResponseUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description TODO
 * @Author gaozhi.liu
 * @Date 2019/5/22 15:07
 * @Version 1.0
 **/
@FeignClient("accountapi")
public interface UserClient {
    @GetMapping({"/name/{login}"})
    ResponseUtils.Response<UserRolesBo> findByLogin(@PathVariable("login") String var1);
}
