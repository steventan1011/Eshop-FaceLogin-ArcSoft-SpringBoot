package com.buaa.hci.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.buaa.hci.mapper.UserMapper;
import com.buaa.hci.pojo.User;
import com.buaa.hci.tools.Base64Req;
import com.buaa.hci.tools.baseToImg;
import com.buaa.hci.tools.getFace;
import com.buaa.hci.tools.loadImages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author T-bk
 * @Date 2020/11/29 13:26
 * @Version 1.0
 */

@Controller
@Slf4j
public class RegisterController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        if (session.getAttribute("loginUser")  != null)
            return "redirect:/index";
        return "register";
    }

    @PostMapping("/registerAction")
    public String registerAction(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("photo") String photo,
                                 Model model) throws IOException {
        // 找是否有重复email的
        QueryWrapper<User> userQueryWrapper = Wrappers.query();
        userQueryWrapper.eq("email", username);
        User user0 = userMapper.selectOne(userQueryWrapper);
        if (user0 != null) {
            model.addAttribute("msg", "抱歉，" + username + " 已注册");
            return "register";
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(password);

        if (photo != "") {
            Base64Req base64Req = new Base64Req();
            base64Req.setBase64(photo);
            baseToImg.GenerateImage(base64Req.getBase64(), ResourceUtils.getURL("src\\main\\resources\\static\\faceImage").getPath() + "\\" + username + ".png");

            // 检测图片是否有人脸信息
            getFace getFace = new getFace();
            int faceNum = getFace.registerDetectFaceNum(username + ".png");
            if (faceNum == 0) {
                model.addAttribute("msg", "抱歉，未检测到人脸，请重新注册");
                return "register";
            } else if (faceNum > 1) {
                model.addAttribute("msg", "抱歉，检测到多张人脸，请重新注册");
                return "register";
            }
            user.setPhotoname(username + ".png");
        }

        int insert = userMapper.insert(user);

        if (insert == 1) {
            model.addAttribute("msg", "注册成功");
            return "login";
        }
        else {
            model.addAttribute("msg", "注册失败，数据库出现问题");
            return "register";
        }


    }
}
