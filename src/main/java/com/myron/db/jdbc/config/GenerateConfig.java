package com.myron.db.jdbc.config;

import java.io.File;

/**
 * @author linrx1
 * @date 2018/4/26.
 */
public class GenerateConfig {
    private String url = "jdbc:mysql://10.8.203.41:63751/EasyScale";
    private String username = "sbs";
    private String password = "1jcSGbl723YOJV2wZaGWirM9r";
    private String driverClassName="com.mysql.jdbc.Driver";

    /**生源码路径*/
    private String filePath = "D:"+ File.separator+"temp";
    /**生成后端的项目名*/
    private String basePackageName = "com.cnc.cloud";

    // 是否过滤第一个_前缀
    private Boolean ignorePrefix = true;
    /**数据库表*/
    private String tableName = "T_POLICY";

}
