# 说明文档

### 重点是实现了人脸注册和登录的模块

附件给出的是航集市网页的代码。

## 1. 前端页面

`src/resources/templates` 目录下每个文件对应的界面设计：

- index.html：网站主页
- login.html：网站用户名密码登录页面
- login-face.html：网站人脸登录页面
- success-login-face.html：成功登录页面
- register.html：网站注册页面
- product.html：网站集市页面
- shopping-cart.html：网站购物车页面
- faq.html：网站帮助页面
- contact.html：网站联系页面
- commons.html：是提取的网页公共部分，如header、footer等标签

`src/resource/static` 目录下存储 js、css、images 等静态资源



### 2. 技术栈

- SpringBoot
- MyBatis-plus
- Thymeleaf
- MySQL
- ArcSoft 人脸识别
- BootStrap



### 3. 部署

- 第一步，安装java环境jdk1.8
- 第二步，在 `src/resources/application.yml` 文件中填写数据库用户名密码，并通过 `create_table.sql` 在数据库中建库和表
- 第三步，Springboot配置使用ssl，使用https，可参考https://www.cnblogs.com/wangshuo1/p/11362295.html
- 第四步，在ArcSoft官网注册账户并得到appId和sdkKey，在 `src/main/java/com.buaa.hci/tools/getFace.class` 中进行设置
- 第五步，运行 `src/main/java/HciApplication` 中的 main 方法









































