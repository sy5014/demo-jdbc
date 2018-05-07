package com.myron.db.jdbc;

import com.myron.db.jdbc.constants.JavaTypeEnum;
import com.myron.db.jdbc.generator.MyronGenerator;

import java.io.File;

/**
 * ddd
 * @Author linrx1
 * @Date 2018/4/27.
 */
public class JdbcApplication {

    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.15.71:63751/console";
		String username = "super_root_ws";
		String password = "adminwangsu";
		String driverClassName="com.mysql.jdbc.Driver";

        // 配置构建对象
        MyronGenerator generator = new MyronGenerator.Builder()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .templateLoaderPaths(new String[]{"classpath:templates/java/mybatis","classpath:templates/mybatis"})
                .filePath("D:"+ File.separator+"temp")
                .projectName("com.cnc.cloud.console")
                .ignorePrefix(false)
                .javaTypeSet(JavaTypeEnum.allJavaType())
                .build();

        // 生成代码
        generator.generateAndTestCase("dns_domain_share", 1);
    }
}
