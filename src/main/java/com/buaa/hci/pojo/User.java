package com.buaa.hci.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author T-bk
 * @Date 2020/11/27 16:32
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户实体")
public class User {  // 这里的类名要和数据库中的表名一致（驼峰-->下划线是可以的）
    // 对应数据库中的主键（uuid、自增id、雪花算法、redis、zookeeper）
    @TableId(type = IdType.AUTO)  // 数据库对应字段必须也是自增的
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("用户手机")
    private String phone;
    @ApiModelProperty("用户性别")
    private String gender;
    @ApiModelProperty("用户城市")
    private String city;
    @ApiModelProperty("用户地址")
    private String address;
    @ApiModelProperty("用户邮编")
    private String mailcode;

    @Version // 乐观锁的version注解
    @ApiModelProperty("乐观锁")
    private Integer version;

    //    @TableLogic // 逻辑删除 全局配置了逻辑删除字段，这里就不用写注解了
    @ApiModelProperty("是否删除")
    private Integer deleted;

    //字段添加填充内容
    @ApiModelProperty("添加时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;




}
