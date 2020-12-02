DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS photo;

CREATE TABLE user
(
 id BIGINT(20) NOT NULL COMMENT '主键ID' AUTO_INCREMENT,
 email VARCHAR(50) UNIQUE NOT NULL COMMENT '邮箱',
 password VARCHAR(50) NOT NULL COMMENT '密码',
 photoname VARCHAR(100) COMMENT '照片名称',
 name VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
 phone VARCHAR(11) NULL DEFAULT NULL COMMENT '手机',
 gender VARCHAR(5) NULL DEFAULT NULL COMMENT '性别',
 city VARCHAR(50) NULL DEFAULT NULL COMMENT '城市',
 address VARCHAR(50) NULL DEFAULT NULL COMMENT '地址',
 mailcode VARCHAR(50) NULL DEFAULT NULL COMMENT '邮编',
 version int(10) NOT NULL DEFAULT 1 COMMENT '乐观锁',
 deleted int(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
 create_time timestamp NULL DEFAULT NULL COMMENT '创建时间',
 update_time timestamp NULL DEFAULT NULL COMMENT '更新时间',
 PRIMARY KEY (id)
);
-- 真实开发中，version(乐观锁)、deleted(逻辑删除)、gmt_create、gmt_modified 这四个字段也要加 这里先不加了

INSERT INTO user (id, email, password, photoname) VALUES
(1, 'admin@admin.com', 'admin', 'admin@admin.com.png');


create table photo(
id INT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(40)
);