//package com.myron.db.jdbc.generator;
//
//
//import com.myron.common.util.StringUtils;
//import com.myron.db.jdbc.bean.Column;
//import com.myron.db.jdbc.bean.Table;
//import com.myron.db.jdbc.datasource.MyronDataSource;
//import com.myron.db.jdbc.factory.SqlSessionFactory;
//import com.myron.db.jdbc.factory.SqlSessionFactoryBean;
//import com.myron.db.jdbc.template.MyronJdbcTemplate;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
//
//import java.io.*;
//import java.util.*;
//
//public class MyronGenerator_backup {
//	private static Logger logger=LoggerFactory.getLogger(MyronGenerator_backup.class);
//	//后端源代码类型
//	private static final String BEAN="bean";
//	private static final String DAO="dao";
//	private static final String SERVICE="service";
//	private static final String SERVICE_IMPL="service.impl";
//	private static final String CONTROLLER="controller";
//	private static final String MAPPER_XML="dao.mapper";
//	private static final String TEST="test";
//
//	//前端源代码类型
//	private static final String EXT_JSP="jsp";
//	private static final String EXT_APP="application";
//	private static final String EXT_CONTROLLER="app.controller";
//	private static final String EXT_MODEL="app.model";
//	private static final String EXT_STORE="app.store";
//	private static final String EXT_VIEW_PANEL="app.view.panel";
//	private static final String EXT_VIEW_WIN="app.view.win";
//	private static final String EXT_VIEW_WIN_IMPORT="app.view.win.import";
//
//	private String url = "jdbc:mysql://10.8.203.41:63751/EasyScale";
//	private String username = "sbs";
//	private String password = "1jcSGbl723YOJV2wZaGWirM9r";
//	private String driverClassName="com.mysql.jdbc.Driver";
//
//	/**生源码路径*/
//	private String filePath = "D:"+ File.separator+"temp";
//	/**生成后端的项目名*/
//	private String basePackageName = "com.cnc.cloud";
//
//	// 是否过滤第一个_前缀
//	private Boolean ignorePrefix = true;
//
//
//
//	public static void main(String[] args) {
//		//提交连接参数
////		jdbc.driverClass=com.mysql.jdbc.Driver
////		jdbc.jdbcUrl=jdbc:mysql://10.8.203.41:63751/EasyScale?useUnicode=true&amp;characterEncoding=utf8
////		jdbc.username=sbs
////		jdbc.password=1jcSGbl723YOJV2wZaGWirM9r
//
////		String url = "jdbc:mysql://192.168.15.71:63751/console";
////		String username = "super_root_ws";
////		String password = "adminwangsu";
////		String driverClassName="com.mysql.jdbc.Driver";
//
//		String url = "jdbc:mysql://10.8.203.41:63751/EasyScale";
//		String username = "sbs";
//		String password = "1jcSGbl723YOJV2wZaGWirM9r";
//		String driverClassName="com.mysql.jdbc.Driver";
//
//		String tableName="T_POLICY";				//选择表名
//		String filePath="D:"+File.separator+"temp";		//生成文件的目录
//		String[] codeTypes={TEST,BEAN,MAPPER_XML,DAO,SERVICE,SERVICE_IMPL,CONTROLLER};		//生成源代码的类型(取决模板类型)
//		String[] frontCodeTypes={EXT_JSP, EXT_APP, EXT_CONTROLLER, EXT_MODEL, EXT_STORE, EXT_VIEW_PANEL,EXT_VIEW_WIN,EXT_VIEW_WIN_IMPORT};
//		String projectName="com.cnc.cloud";				//生成后端的项目名
//
//		logger.info("连接数据库:{} driverClassName:{}",url,driverClassName);
//		logger.info("用户:{}",username);
//		logger.info("密码:{}",password);
//
//		//查询数据库表的列信息
//		MyronDataSource dataSource=new MyronDataSource(url, username, password, driverClassName);
//		Table table=getTableInfo(dataSource,tableName, 143);
//		table.setProjectName(projectName);
//		table.setJspContextPath("/"+StringUtils.uncapitalize(table.getClassName()));
//		logger.info("获取元数据:{}",table);
//
//
//		logger.info("开始生成'{}'的后端{}源代码",table.getClassName(), Arrays.toString(codeTypes));
//		for (String type: codeTypes) {
//			table.setPackageName(projectName+"."+type);
//			try {
//				exportJavaSourceCode(table,filePath,type);
//				logger.info("生成'{}'", type);
//			} catch (TemplateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		logger.info("完成'{}'源代码",table.getClassName());
//
///*		logger.info("开始生成'{}'的前端{}源代码",table.getClassName(), Arrays.toString(frontCodeTypes));
//		for (String type : frontCodeTypes) {
//			table.setPackageName(projectName+"."+type);
//			try {
//				exportFrontSourceCode(table,filePath,type);
//				logger.info("生成'{}'", type);
//			} catch (TemplateException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		logger.info("完成'{}'源代码",table.getClassName());*/
//	}
//
//	/**
//	 * 根据数据源获取tableName表的信息
//	 * @param dataSource
//	 * @param tableName
//	 * @return
//	 */
//	private static Table getTableInfo(MyronDataSource dataSource,
//			String tableName) {
//		SqlSessionFactory sessionFactory = new SqlSessionFactoryBean(dataSource);
//		MyronJdbcTemplate jdbcTemplate = new MyronJdbcTemplate();
//		jdbcTemplate.setSessionFactory(sessionFactory);
//		List<Column> columnList = jdbcTemplate.queryColumn(tableName);
//
//		// 数据库表信息
//		Table table = new Table();
//		table.setTableName(tableName);
//
//		//int i = tableName.indexOf("_");
//		//tableName = tableName.substring(i + 1);
//		System.out.println(tableName);
//		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
//		table.setColumnList(columnList);
//		return table;
//	}
//
//	/**
//	 * 根据数据源获取tableName表的信息，及获取一条测试用例数据
//	 * @param dataSource
//	 * @param tableName
//	 * @param id
//	 * @return
//	 */
//	private static Table getTableInfo(MyronDataSource dataSource,
//									  String tableName, Object id) {
//		SqlSessionFactory sessionFactory = new SqlSessionFactoryBean(dataSource);
//		MyronJdbcTemplate jdbcTemplate = new MyronJdbcTemplate();
//		jdbcTemplate.setSessionFactory(sessionFactory);
//		List<Column> columnList = jdbcTemplate.queryColumn(tableName);
//		// 构造测试用例
//		if (id != null) {
//			jdbcTemplate.buildTestDataById(tableName, columnList, id);
//		}
//		// 数据库表信息
//		Table table = new Table();
//		table.setTableName(tableName);
//
//		// 是否过滤第一个前缀
//		int i = tableName.indexOf("_");
//		tableName = tableName.substring(i + 1);
//
//		System.out.println(tableName);
//		table.setClassName(StringUtils.toCapitalizeCamelCase(tableName));
//		table.setColumnList(columnList);
//		return table;
//	}
//
//
//	/**
//	 * 输出fileType类型的java源代码
//	 * @param table 根据表信息
//	 * @param rootPath 文件输出根路径
//	 * @param codeType 源代码类型
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public static void exportJavaSourceCode(Table table, String rootPath,String codeType) throws TemplateException, IOException{
//		//生成源代码文件目录
//		String path=rootPath+javaPackageToPath(table,codeType);
//		File filePath = new File(path);
//	    if (!filePath.exists()){
//	    	filePath.mkdirs();
//		}
//		logger.info("SourceCode Location:{}", filePath.getPath());
//
//		//创建源文件
//		File sourceFile =createJavaSourceFile(table,filePath,codeType);
//		FileOutputStream fos = new FileOutputStream(sourceFile);
//		Writer out = new OutputStreamWriter(fos, "UTF-8");
//
//		//根据源代码类型获取模板
//		 String templateName=getTemplateName(codeType);
//		Template template=getTemplate(templateName);
//		logger.debug("'{}'类型源代码 使用模板:{}",codeType,template.getName());
//
//		//根据模板及"元数据"往源文件中输入源代码
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("table", table);
//		template.process(map, out);
//
//		//弹出生成的源代码文件夹
//		//Desktop.getDesktop().open(filePath);
//	}
//
//
//	/**
//	 * 生成前端源js/jsp代码
//	 * @param table
//	 * @param rootPath
//	 * @param codeType
//	 * @throws TemplateException
//	 * @throws IOException
//	 */
//	public static void exportFrontSourceCode(Table table, String rootPath,
//			String codeType) throws TemplateException, IOException {
//		// 生成源代码文件目录
//		String path = rootPath
//				+ jspContextPathToPath(table.getJspContextPath(), codeType);
//		File filePath = new File(path);
//		if (!filePath.exists()) {
//			filePath.mkdirs();
//		}
//		logger.info("SourceCode Location:{}", filePath.getPath());
//
//		// 创建源文件
//		File sourceFile = createFrontSourceFile(table.getClassName(), filePath,
//				codeType);
//		FileOutputStream fos = new FileOutputStream(sourceFile);
//		Writer out = new OutputStreamWriter(fos, "UTF-8");
//
//		// 根据源代码类型获取模板
//		String templateName = getTemplateName(codeType);
//		Template template = getTemplate(templateName);
//		logger.debug("'{}'类型源代码 使用模板:{}", codeType, template.getName());
//
//		// 根据模板及"元数据"往源文件中输入源代码
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("table", table);
//		template.process(map, out);
//
//		// 弹出生成的源代码文件夹
//		//Desktop.getDesktop().open(filePath);
//	}
//
//	/**
//	 * 根据文件类型获取对应模板
//	 * @param
//	 * @return
//	 * @throws IOException
//	 * @throws TemplateException
//	 */
//	private static Template getTemplate(String templateName) throws IOException, TemplateException {
//		FreeMarkerConfigurer freeMarkerConfigurer=new FreeMarkerConfigurer();
//
//        freeMarkerConfigurer.setTemplateLoaderPaths("classpath:templates/java/mybatis","classpath:templates/mybatis");
//        Properties freemarkerSettings = new Properties();
//        freemarkerSettings.put("default_encoding", "UTF-8");
//        freemarkerSettings.put("locale", "zh_CN");
//        freeMarkerConfigurer.setFreemarkerSettings(freemarkerSettings);
//		freeMarkerConfigurer.afterPropertiesSet();
//
//        //String templateName="defaulteTemplate.ftl";// 装载模板
//		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
//		return template;
//	}
//
//	/**
//	 *  创建源代码文件
//	 * @param table 对象名
//	 * @param filePath 文件路径
//	 * @param fileType 类型
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createJavaSourceFile(Table table, File filePath,
//			String fileType) throws IOException {
//		File sourceFile = null;
//		String fileName = "undefined";
//		StringBuffer fileformat = new StringBuffer();
//
//		if (TEST.equals(fileType)) {
//			fileformat.append("java");
//			fileName = table.getClassName()
//					+ StringUtils.toCapitalizeCamelCase(fileType);
//		} else if(BEAN.equals(fileType)) {
//			fileformat.append("java");
//			fileName = table.getClassName();
//		} else if (DAO.equals(fileType) || SERVICE.equals(fileType)
//				|| CONTROLLER.equals(fileType)) {
//			fileformat.append("java");
//			fileName = table.getClassName()
//					+ StringUtils.toCapitalizeCamelCase(fileType);
//		} else if (SERVICE_IMPL.equals(fileType)) {
//			fileformat.append("java");
//			fileType = fileType.replace(".", "_");// "service.impl"=>service_impl
//			fileName = table.getClassName()
//					+ StringUtils.toCapitalizeCamelCase(fileType);
//		} else if (MAPPER_XML.equals(fileType)) {
//			fileformat.append("xml");
//			fileName = table.getClassName() + "Mapper";
//		} else {
//			fileformat.append("undefined");
//		}
//		sourceFile = new File(filePath.getPath() + File.separator + fileName
//				+ "." + fileformat.toString());
//		if (sourceFile.exists()) {
//			sourceFile.delete();
//		}
//		sourceFile.createNewFile();
//		logger.info("Create source file:{}", sourceFile);
//
//		return sourceFile;
//	}
//
//	/**
//	 *  创建前端源代码文件
//	 * @param table 对象名
//	 * @param filePath 文件路径
//	 * @param fileType 类型
//	 * @return
//	 * @throws IOException
//	 */
//	private static File createFrontSourceFile(String className, File filePath,
//			String fileType) throws IOException {
//		File sourceFile = null;
//		String fileName = "undefined";
//		StringBuffer fileformat = new StringBuffer();
//
//		if (EXT_JSP.equals(fileType)) {
//			fileformat.append("jsp");
//			fileName = "index";
//		} else if (EXT_APP.equals(fileType)) {
//			fileformat.append("js");
//			fileName = "Main";
//		} else if (EXT_CONTROLLER.equals(fileType)) {
//			fileformat.append("js");
//			fileName = className + "Controller";
//		} else if (EXT_MODEL.equals(fileType) || EXT_STORE.equals(fileType)) {
//			fileformat.append("js");
//			fileName = className;
//		} else if (EXT_VIEW_PANEL.equals(fileType)) {
//			fileformat.append("js");
//			fileName = className + "Panel";
//		} else if (EXT_VIEW_WIN.equals(fileType)) {
//			fileformat.append("js");
//			fileName = className + "Win";
//		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
//			fileformat.append("js");
//			fileName = "ImportWin";
//		} else {
//			fileformat.append("undefined");
//		}
//		sourceFile = new File(filePath.getPath() + File.separator + fileName
//				+ "." + fileformat.toString());
//		if (sourceFile.exists()) {
//			sourceFile.delete();
//		}
//		sourceFile.createNewFile();
//		logger.info("Create source file:{}", sourceFile);
//
//		return sourceFile;
//	}
//
//	/**
//	 * java包名 转换 为文件路径名
//	 * @param packageName 对象包名
//	 * @param fileType	     对象生成源代码文件类型
//	 * @return
//	 */
//	private static String javaPackageToPath(Table table, String fileType) {
//		// 如果java类型,添加src目录及子包目录
//		if (BEAN.equals(fileType) || DAO.equals(fileType)
//				|| MAPPER_XML.equals(fileType) || SERVICE.equals(fileType)
//				|| SERVICE_IMPL.equals(fileType) || CONTROLLER.equals(fileType)
//				||TEST.equals(fileType)) {
//
//			String filePath = "";
//			String[] paths = table.getPackageName().split("\\.");
//			for (int i = 0; i < paths.length; i++) {
//				filePath = filePath + File.separator + paths[i];
//			}
//			filePath = File.separator + "src/main/java" + filePath;
//			return filePath;
//		}
//		return "";
//	}
//
//	/**
//	 * jsp相对路径转 转绝对路径
//	 * @param jspContextPath
//	 * @param fileType
//	 * @return
//	 */
//	private static String jspContextPathToPath(String jspContextPath,String fileType){
//		String filePath = "";
//		if (EXT_JSP.equals(fileType) || EXT_APP.equals(fileType)) {
//			filePath = jspContextPath;
//		} else if (EXT_CONTROLLER.equals(fileType)) {
//			filePath = jspContextPath + File.separator + "app" + File.separator+"controller";
//		} else if (EXT_MODEL.equals(fileType)) {
//			filePath = jspContextPath+File.separator+"app"+File.separator+"model";
//		} else if (EXT_STORE.equals(fileType)) {
//			filePath = jspContextPath+File.separator+"app"+File.separator+"store";
//		} else if (EXT_VIEW_PANEL.equals(fileType)) {
//			filePath = jspContextPath+File.separator+"app"+File.separator+"view";
//		} else if (EXT_VIEW_WIN.equals(fileType)) {
//			filePath = jspContextPath+File.separator+"app"+File.separator+"view";
//		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
//			filePath = jspContextPath+File.separator+"app"+File.separator+"view";
//		}
//		filePath = File.separator+"webapp"+filePath;
//		return filePath;
//	}
//
//	/**
//	 * 根据源文件类型 获取对应的模板
//	 * @param fileType
//	 * @return
//	 */
//	private static String getTemplateName(String fileType) {
//		//TODO 文件类型对应模板的映射可通过配置文件定义
//		String templateName = "";
//		if (BEAN.equals(fileType)) {
//			templateName = "Bean.java";
//		} else if (TEST.equals(fileType)) {
//			templateName = "BeanTest.java";
//		} else if (DAO.equals(fileType)) {
//			templateName = "BeanMapper.java";
//		} else if (MAPPER_XML.equals(fileType)) {
//			templateName = "BeanMapper.xml";
//		} else if (SERVICE.equals(fileType)) {
//			templateName = "BeanService.java";
//		} else if (SERVICE_IMPL.equals(fileType)) {
//			templateName = "BeanServiceImpl.java";
//		} else if (CONTROLLER.equals(fileType)) {
//			templateName = "BeanController.java";
//		} else {
//			templateName = "undefined";
//		}
//
//		//后端
//		if (EXT_JSP.endsWith(fileType)){
//			templateName = "ExtjsJsp.jsp";
//		} else if (EXT_APP.equals(fileType)) {
//			templateName = "ExtjsApp.js";
//		} else if (EXT_CONTROLLER.equals(fileType)) {
//			templateName = "ExtjsController.js";
//		} else if (EXT_MODEL.equals(fileType)) {
//			templateName = "ExtjsModel.js";
//		} else if (EXT_STORE.equals(fileType)) {
//			templateName = "ExtjsStore.js";
//		} else if (EXT_VIEW_PANEL.equals(fileType)) {
//			templateName = "ExtjsPanel.js";
//		} else if (EXT_VIEW_WIN.equals(fileType)) {
//			templateName = "ExtjsWin.js";
//		} else if (EXT_VIEW_WIN_IMPORT.equals(fileType)) {
//			templateName = "ExtjsImportWin.js";
//		}
//		return templateName;
//	}
//
//
//}
