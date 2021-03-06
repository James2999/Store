package com.test.store.contoller;

import com.test.store.entity.Consumer;
import com.test.store.entity.User;
import com.test.store.service.LoginService;
import com.test.store.util.StatusCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 登陆控制器，通过identity字段判断是雇员还是顾客
     * @param params
     * @return
     */
    @RequestMapping("login")
    public String login(@RequestBody(required = false) Map<String, Object> params) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        Integer identity = (Integer) params.get("identity");
        User user = new User(username, password, identity);

        //如果以admin进入顾客界面，则直接报USERNAME_ERROR
        if("admin".equals(user.getUsername()) && user.getIdentity()==1) {
            return StatusCodeUtils.getCodeJsonString(StatusCodeUtils.USERNAME_ERROR);
        }

        //admin超级管理员，identity为10
        if("admin".equals(user.getUsername())) {
            user.setIdentity(10);
        }

        return loginService.login(user);
    }
}
