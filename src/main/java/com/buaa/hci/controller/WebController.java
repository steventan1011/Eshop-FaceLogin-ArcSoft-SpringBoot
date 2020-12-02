package com.buaa.hci.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.buaa.hci.mapper.UserMapper;
import com.buaa.hci.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author T-bk
 * @Date 2020/11/27 15:50
 * @Version 1.0
 */

@Controller
@Slf4j
public class WebController {
    @GetMapping({"/index", ""})
    public String index(HttpServletRequest request, Model model) {
        return "index";
    }

    @GetMapping("/product")
    public String product(Model model) {
        return "product";
    }

    @GetMapping("/shoping-cart")
    public String shopingCart(Model model) {
        return "shoping-cart";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("/faq")
    public String faq(Model model) {
        return "faq";
    }

    @GetMapping("/offer-page")
    public String offerPage(Model model) {
        return "offfaer-page";
    }

    @GetMapping("/order-tracking")
    public String orderTracking(Model model) {
        return "order-tracking";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        return "payment";
    }

    @GetMapping("/review")
    public String review(Model model) {
        return "review";
    }

    @GetMapping("/wishlist")
    public String wishlist(Model model) {
        return "wishlist";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping("/product-detail")
    public String productDetail(Model model) {
        return "product-detail";
    }
}
