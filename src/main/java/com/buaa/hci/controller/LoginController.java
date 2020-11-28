package com.buaa.hci.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.hci.mapper.UserMapper;
import com.buaa.hci.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Author T-bk
 * @Date 2020/11/23 13:34
 * @Version 1.0
 */

@Slf4j
@Controller
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping(value = "/loginAction")
    public String loginAction(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model,
                            HttpSession session) {
        log.info("---------------------");
        log.info(username);
        log.info(password);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("email", username)
                .eq("password", password);
        List<User> userList = userMapper.selectList(userQueryWrapper);   // 查询多个数据 selectList

        if (userList == null || userList.isEmpty()) {  //登陆失败
            model.addAttribute("msg", "用户名密码错误");
            return "login";
        } else {
            session.setAttribute("loginUser", username);
            return "redirect:/index";
        }
    }

    @GetMapping("/logoff")
    public String logoff(HttpSession session) {
        session.setAttribute("loginUser", null);
        return "index";
    }



}
