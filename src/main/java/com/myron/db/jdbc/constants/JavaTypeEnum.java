package com.myron.db.jdbc.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * java源码类型枚举
 * @author linrx1d
 * @date 2018/4/26.
 */
public enum JavaTypeEnum {

    /**表对应实体对象*/
    BEAN("bean","JavaBean.java", "表对应实体对象"),
    /**dao 源码*/
    DAO("dao", "JavaDao.java", "dao对象"),
    /**mapper xml 源码*/
    MAPPER_XML("dao.mapper", "JavaMapper.xml", "dao.mapper"),
    /**controller 源码*/
    CONTROLLER("controller", "JavaController.java", "controller"),
    /**service 源码*/
    SERVICE("service", "JavaService.java", "service接口"),
    /**serviceImpl 源码*/
    SERVICE_IMPL("service.impl", "JavaServiceImpl.java", "service接口实现类"),
    /**test对象*/
    TEST("test", "JavaTest.java", "单元测试");

    /**包前缀*/
    private String prefix;
    /**类型对应模板名称*/
    private String templateName;
    private String description;

    JavaTypeEnum(String prefix, String templateName, String description) {
        this.prefix = prefix;
        this.templateName = templateName;
        this.description = description;
    }

    /**
     * 获取所有枚举对象集合
     * @return
     */
    public static Set<JavaTypeEnum> allJavaType(){
        Set<JavaTypeEnum> set = new HashSet<>();
        set.add(JavaTypeEnum.BEAN);
        set.add(JavaTypeEnum.DAO);
        set.add(JavaTypeEnum.MAPPER_XML);
        set.add(JavaTypeEnum.SERVICE);
        set.add(JavaTypeEnum.SERVICE_IMPL);
        set.add(JavaTypeEnum.CONTROLLER);
        set.add(JavaTypeEnum.TEST);
        return set;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
