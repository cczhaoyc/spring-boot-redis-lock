CREATE TABLE sys_user (
  id bigint(24) NOT NULL,
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  age int(4) DEFAULT NULL COMMENT '年龄',
  birthday timestamp NULL DEFAULT NULL '出生年月',
  create_time timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time timestamp NULL DEFAULT NULL '修改时间',
  PRIMARY KEY (id),
  KEY ind_name (`name`)
);