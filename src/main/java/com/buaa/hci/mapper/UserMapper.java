package com.buaa.hci.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buaa.hci.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @Author T-bk
 * @Date 2020/11/27 16:31
 * @Version 1.0
 */


// 在对应的Mapper上面继承基本的接口 BaseMapper
@Repository // 代表持久层
public interface UserMapper extends BaseMapper<User> {
    void selectByMap(HashMap<String, String> loginUser);
    // 所有的CRUD操作都已经基本完成了
    // 你不需要像以前的配置一大堆文件了
}

