package com.myron.db.jdbc.generator;


import com.myron.common.util.StringUtils;
import com.myron.db.jdbc.bean.Column;
import com.myron.db.jdbc.bean.Table;
import com.myron.db.jdbc.constants.JavaTypeEnum;
import com.myron.db.jdbc.datasource.MyronDataSource;
import com.myron.db.jdbc.factory.SqlSessionFactory;
import com.myron.db.jdbc.factory.SqlSessionFactoryBean;
import com.myron.db.jdbc.template.MyronJdbcTemplate;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.*;

/**
 * @author linrx
 */
public class MyronGenerator {
	private static Logger logger=LoggerFactory.getLogger(MyronGenerator.class);

	//前端源代码类型
	private static final String EXT_JSP="jsp";
	private static final String EXT_APP="application";
	private static final String EXT_CONTROLLER="app.controller";
	private static final String EXT_MODEL="app.model";
	private static final String EXT_STORE="app.store";
	private static final String EXT_VIEW_PANEL="app.view.panel";
	private static final String EXT_VIEW_WIN="app.view.win";
	private static final String EXT_VIEW_WIN_IMPORT="app.view.win.import";

	/**默认工程目录*/
	private static final String DEFAULT_TARGET_PROJECT = "src/main/java";

	private String url;
	private String username;
	private String password;
	private String driverClassName;

	/**生成模板路径*/
	private String[] templateLoaderPaths;
	/**生源码路径*/
	private String filePath;
	/**生成后端的项目名*/
	private String projectName = "com.cnc.cloud";
	/**是否过滤第一个_前缀*/
	private Boolean ignorePrefix = false;
	/**生成java源文件类型*/
	private Set<JavaTypeEnum> javaTypeSet;

	public MyronGenerator(Builder builder) {
		this.url = builder.url;
		this.username = builder.username;
		this.password = builder.password;
		this.driverClassName = builder.driverClassName;
		this.templateLoaderPaths = builder.templateLoaderPaths;
		this.filePath = builder.filePath;
		this.projectName = builder.projectName;
		this.ignorePrefix = builder.ignorePrefix;
		this.javaTypeSet = builder.javaTypeSet;
	}

	/**
	 * build模式构造器
	 */
	public static class Builder {
		private String url;
		private String username;
		private String password;
		private String driverClassName;
		private String[] templateLoaderPaths;
		/**生源码路径*/
		private String filePath;
		/**生成后端的项目名*/
		private String projectName;
		/**是否过滤第一个_前缀*/
		private Boolean ignorePrefix = false;
		private Set<JavaTypeEnum> javaTypeSet;


		public Builder() {
		}

		public MyronGenerator build() {
			MyronGenerator generator = new MyronGenerator(this);
			return generator;
		}

		public Builder url(String url){
			this.url = url;
			return this;
		}
		public Builder username(String username){
			this.username = username;
			return this;
		}
		public Builder password(String password){
			this.password = password;
			return this;
		}
		public Builder driverClassName(String driverClassName){
			this.driverClassName = driverClassName;
			return this;
		}
		public Builder filePath(String filePath){
			this.filePath = filePath;
			return this;
		}
		public Builder projectName(String projectName){
			this.projectName = projectName;
			return this;
		}
		public Builder ignorePrefix(boolean ignorePrefix){
			this.ignorePrefix = ignorePrefix;
			return this;
		}

		public Builder javaTypeSet(Set<JavaTypeEnum> javaTypeSet){
			this.javaTypeSet = javaTypeSet;
			return this;
		}
		public Builder templateLoaderPaths(String[] templateLoaderPaths){
			this.templateLoaderPaths = templateLoaderPaths;
			return this;
		}


	}

	/**
	 * 生成后端代码及单元测试用例
	 * @param tableName
	 * @param primaryKeyValue
	 */
	public void generateAndTestCase(String tableName, Object primaryKeyValue){
		//查询数据库表的列信息
		MyronDataSource dataSource=new MyronDataSource(this.url, this.username, this.password, this.driverClassName);
		Table table=getTableInfo(dataSource,tableName, primaryKeyValue);
		table.setProjectName(projectName);
		table.setJspContextPath("/"+StringUtils.uncapitalize(table.getClassName()));
		logger.info("获取元数据:{}",table);
		try {
			for (JavaTypeEnum javaType : this.javaTypeSet) {
//				String type = javaType.getPrefix();
//				table.setPackageName(projectName + "." + type);
				table.buildPackageByJavaType(javaType);
				this.exportJavaSourceCode(table, filePath, javaType);
				logger.info("生成'{}'", javaType.getPrefix());
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		logger.info("完成'{}'源代码",table.getClassName());
	}

	/**
	 * 根据数据源获取tableName表的信息，及获取一条测试用例数据
	 * @param dataSource 数据源
	 * @param tableName 表名
	 * @param id
	 * @return
	 */
	private Table getTableInfo(MyronDataSource dataSource,
									  String tableName, Object id) {
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBean(dataSource);
		MyronJdbcTemplate jdbcTemplate = new MyronJdbcTemplate();
		jdbcTemplate.setSessionFactory(sessionFactory);
		List<Column> columnList = jdbcTemplate.queryColumn(tableName);
		// 构造测试用例
		if (id != null) {
			jdbcTemplate.buildTestDataById(tableName, columnList, id);
		}
		// 数据库表信息
		Table table = new Table();
		table.setTableName(tableName);

		// 是否过滤第一个前缀
		if (this.ignorePrefix) {
			int i = tableName.indexOf("_");
			tableName = tableName.substring(i + 1);
		}
		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
		table.setColumnList(columnList);
		return table;
	}


	/**
	 * 输出fileType类型的java源代码
	 * @param table 根据表信息
	 * @param rootPath 文件输出根路径
	 * @param codeType 源代码类型
	 * @throws TemplateException
	 * @throws IOException
	 */
	private void exportJavaSourceCode(Table table, String rootPath,JavaTypeEnum codeType) throws TemplateException, IOException{


		//生成源代码文件目录
		String path = rootPath + File.separator + "src/main/java" + table.javaPackageToPath();
		File filePath = new File(path);
		if (!filePath.exists()){
			filePath.mkdirs();
		}
		logger.info("SourceCode Location:{}", filePath.getPath());

		//创建源文件
		File sourceFile = createJavaSourceFile(table,filePath,codeType);
		FileOutputStream fos = new FileOutputStream(sourceFile);
		Writer out = new OutputStreamWriter(fos, "UTF-8");

		//根据源代码类型获取模板
		String templateName = codeType.getTemplateName();
		Template template=this.getTemplate(templateName);
		logger.debug("'{}'类型源代码 使用模板:{}",codeType,template.getName());
		//根据模板及"元数据"往源文件中输入源代码
		Map<String, Object> map = new HashMap<String, Object>(16);
		map.put("table", table);
		template.process(map, out);
		//弹出生成的源代码文件夹
		//Desktop.getDesktop().open(filePath);
	}
	
	
	/**
	 * 生成前端源js/jsp代码
	 * @param table
	 * @param rootPath
	 * @param codeType
	 * @throws TemplateException
	 * @throws IOException
	 */
	public  void exportFrontSourceCode(Table table, String rootPath,
			String codeType) throws TemplateException, IOException {
		// 生成源代码文件目录
		String path = rootPath
				+ jspContextPathToPath(table.getJspContextPath(), codeType);
		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		logger.info("SourceCode Location:{}", filePath.getPath());

		// 创建源文件
		File sourceFile = createFrontSourceFile(table.getClassName(), filePath,
				codeType);
		FileOutputStream fos = new FileOutputStream(sourceFile);
		Writer out = new OutputStreamWriter(fos, "UTF-8");

		// 根据源代码类型获取模板
		String templateName = getTemplateName(codeType);
		Template template = this.getTemplate(templateName);
		logger.debug("'{}'类型源代码 使用模板:{}", codeType, template.getName());

		// 根据模板及"元数据"往源文件中输入源代码
		Map<String, Object> map = new HashMap<String, Object>(16);
		map.put("table", table);
		template.process(map, out);

		// 弹出生成的源代码文件夹
		//Desktop.getDesktop().open(filePath);
	}

	/**
	 * 根据文件类型获取对应模板
	 * @param templateName
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	private  Template getTemplate(String templateName) throws IOException, TemplateException {
		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();

//        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java/mybatis","classpath:templates/mybatis");
        freeMarkerConfigurer.setTemplateLoaderPaths(templateLoaderPaths);
        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("locale", "zh_CN");
        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
		freeMarkerConfigurer.afterPropertiesSet();
	
        //String templateName="defaulteTemplate.ftl";// 装载模板
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
		return template;
	}

	/**
	 * 创建源代码文件
	 * @param table
	 * @param filePath
	 * @param javaType
	 * @return
	 * @throws IOException
	 */
	private File createJavaSourceFile(Table table, File filePath,
									  JavaTypeEnum javaType) throws IOException {
		String tempalteName = javaType.getTemplateName();
		// 替换名称中的"Java"用具体的对象。如: JavaMapper->UserMapper
		File sourceFile = null;
		sourceFile = new File(filePath.getPath() + File.separator + tempalteName.replace("Bean",table.getClassName()));
		if (sourceFile.exists()) {
			sourceFile.delete();
		}
		sourceFile.createNewFile();
		logger.info("Create source file:{}", sourceFile);
		return sourceFile;
	}
	
	/**
	 *  创建前端源代码文件
	 * @param className 对象名
	 * @param filePath 文件路径
	 * @param fileType 类型
	 * @return
	 * @throws IOException
	 */
	private static File createFrontSourceFile(String className, File filePath,
			String fileType) throws IOException {
		File sourceFile = null;
		String fileName = "undefined";
		StringBuffer fileformat = new StringBuffer();

		if (EXT_JSP.equals(fileType)) {
			fileformat.append("jsp");
			fileName = "index";
		} else if (EXT_APP.equals(fileType)) {
			fileformat.append("js");
			fileName = "Main";
		} else if (EXT_CONTROLLER.equals(fileType)) {
			fileformat.append("js");
			fileName = className + "Controller";
		} else if (EXT_MODEL.equals(fileType) || EXT_STORE.equals(fileType)) {
			fileformat.append("js");
			fileName = className;
		} else if (EXT_VIEW_PANEL.equals(fileType)) {
			fileformat.append("js");
			fileName = className + "Panel";
		} else if (EXT_VIEW_WIN.equals(fileType)) {
			fileformat.append("js");
			fileName = className + "Win";
		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
			fileformat.append("js");
			fileName = "ImportWin";
		} else {
			fileformat.append("undefined");
		}
		sourceFile = new File(filePath.getPath() + File.separator + fileName
				+ "." + fileformat.toString());
		if (sourceFile.exists()) {
			sourceFile.delete();
		}
		sourceFile.createNewFile();
		logger.info("Create source file:{}", sourceFile);

		return sourceFile;
	}


	
	/**
	 * jsp相对路径转 转绝对路径
	 * @param jspContextPath
	 * @param fileType
	 * @return
	 */
	private static String jspContextPathToPath(String jspContextPath,String fileType){
		String filePath = "";
		if (EXT_JSP.equals(fileType) || EXT_APP.equals(fileType)) {
			filePath = jspContextPath;
		} else if (EXT_CONTROLLER.equals(fileType)) {
			filePath = jspContextPath + File.separator + "app" + File.separator+"controller";
		} else if (EXT_MODEL.equals(fileType)) {
			filePath = jspContextPath+File.separator+"app"+File.separator+"model";
		} else if (EXT_STORE.equals(fileType)) {
			filePath = jspContextPath+File.separator+"app"+File.separator+"store";			
		} else if (EXT_VIEW_PANEL.equals(fileType)) {
			filePath = jspContextPath+File.separator+"app"+File.separator+"view";	
		} else if (EXT_VIEW_WIN.equals(fileType)) {
			filePath = jspContextPath+File.separator+"app"+File.separator+"view";	
		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
			filePath = jspContextPath+File.separator+"app"+File.separator+"view";	
		}
		filePath = File.separator+"webapp"+filePath;
		return filePath;
	}
	
	/**
	 * 根据源文件类型 获取对应的模板
	 * @param fileType
	 * @return
	 */
	private static String getTemplateName(String fileType) {
		//TODO 文件类型对应模板的映射可通过配置文件定义
		String templateName = "";

		//后端
		if (EXT_JSP.endsWith(fileType)){
			templateName = "ExtjsJsp.jsp";
		} else if (EXT_APP.equals(fileType)) {
			templateName = "ExtjsApp.js";
		} else if (EXT_CONTROLLER.equals(fileType)) {
			templateName = "ExtjsController.js";
		} else if (EXT_MODEL.equals(fileType)) {
			templateName = "ExtjsModel.js";
		} else if (EXT_STORE.equals(fileType)) {
			templateName = "ExtjsStore.js";
		} else if (EXT_VIEW_PANEL.equals(fileType)) {
			templateName = "ExtjsPanel.js";
		} else if (EXT_VIEW_WIN.equals(fileType)) {
			templateName = "ExtjsWin.js";
		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
			templateName = "ExtjsImportWin.js";
		}
		return templateName;
	}


}
