package com.buaa.hci.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buaa.hci.mapper.UserMapper;
import com.buaa.hci.pojo.User;
import com.buaa.hci.tools.Base64Req;
import com.buaa.hci.tools.baseToImg;
import com.buaa.hci.tools.getFace;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @PostMapping(value = "/loginAction")
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
            model.addAttribute("msg", "用户名或密码错误，请重新登录");
            return "login";
        } else {
            session.setAttribute("loginUser", username);
            return "redirect:/index";
        }
    }

    @GetMapping(value = "/loginFace")
    public String loginFace(Model model) {
        return "login-face";
    }

    @PostMapping(value = "/loginFaceAction")
    public String loginFaceAction(@RequestParam("photo") String photo,
                                  HttpSession session,
                                  Model model) throws IOException {
        Base64Req base64Req = new Base64Req();
        base64Req.setBase64(photo);
        String face1 = "facetest_" + UUID.randomUUID().toString().substring(5) + ".png";
        String face1Path = ResourceUtils.getURL("src\\main\\resources\\static\\faceImage\\tmp").getPath() + "\\" + face1;
        baseToImg.GenerateImage(base64Req.getBase64(), face1Path);
        getFace getface = new getFace(face1);
        HashMap<String, Object> result = getface.faceCompare();
        System.out.println(result);

        if ((boolean) result.get("flag") == true) {  // 登陆成功
            session.setAttribute("loginUser", (String) result.get("email"));
            model.addAttribute("face1", (String) result.get("face1"));
            model.addAttribute("face2", (String) result.get("face2"));
            model.addAttribute("email", (String) result.get("email"));
            model.addAttribute("similarity", String.valueOf(result.get("similarity")));
            String gender;
            if ((Integer) result.get("gender") == -1)
                gender = "未知性别";
            else if ((Integer) result.get("gender") == 0)
                gender = "男性";
            else
                gender = "女性";
            model.addAttribute("gender", gender);

            String age;
            if ((Integer) result.get("age") == 0)
                age = "未知年龄";
            else
                age = String.valueOf((Integer) result.get("age"));
            model.addAttribute("age", age);

            String dimensionVal = (String) result.get("3D");
            String[] dimensionSplit = dimensionVal.split(",");
            String dimension = "偏航角: " + dimensionSplit[0] + ", 横滚角: " + dimensionSplit[1] + ", 俯仰角: " + dimensionSplit[2];
            model.addAttribute("dimension", dimension);

            Integer livenessInfoInteger = (Integer) result.get("livenessInfo");
            String livenessInfo;
            if (livenessInfoInteger == -1)
                livenessInfo = "未知活体";
            else if (livenessInfoInteger == 0)
                livenessInfo = "非活体";
            else if (livenessInfoInteger == 1)
                livenessInfo = "存在人脸";
            else
                livenessInfo = "超出人脸";
            model.addAttribute("livenessInfo", livenessInfo);

            return "success-login-face";
        }
        else {   // 登录失败
            model.addAttribute("loginFail", "您未注册人脸信息，请注册或重新登陆");
            return "login-face";
        }

    }

    @GetMapping("/successLoginFace")
    public String successLoginFace(@RequestParam("photo") String photo,
                                  HttpSession session,
                                  Model model) throws IOException {
        return "successLoginFace";
    }

    @GetMapping("/logoff")
    public String logoff(HttpSession session) {
        session.setAttribute("loginUser", null);
        return "index";
    }



}
