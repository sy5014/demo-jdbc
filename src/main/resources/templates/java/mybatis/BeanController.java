package  ${table.projectName}.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.Page;
import ${table.projectName}.bean.${table.className};
import ${table.projectName}.service.${table.className}Service;


@Controller
@RequestMapping("${table.jspContextPath}")
public class ${table.className}Controller {
	//private static final Logger logger = LoggerFactory.getLogger(${table.className}Controller.class);
	
	@Autowired
	private ${table.className}Service ${table.className?uncap_first}Service;
	

	@RequestMapping("/listByPage")
	@ResponseBody
	public  Map<String, Object> listByPage(String filter, ${table.className} ${table.className?uncap_first}, Page<Map<String, Object>> page, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		//设置默认排序属性
		//page.setDefaultSort("createTime", "desc");
		page = this.${table.className?uncap_first}Service.findMapListByPage(${table.className?uncap_first}, page);
		map.put("studentdata", page);
	    map.put("number", page.getTotal());
		return map;
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add${table.className}(${table.className} ${table.className?uncap_first}, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.create${table.className}(${table.className?uncap_first});
		return map;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> update${table.className}(${table.className} ${table.className?uncap_first}, HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.update${table.className}(${table.className?uncap_first});
		return map;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete${table.className}(${table.className} ${table.className?uncap_first}, HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map = this.${table.className?uncap_first}Service.delete${table.className}(${table.className?uncap_first});
		return map;
	}
	
	/**
	 * 导出Excel文件
	 * @param req
	 * @param resp
	 * @param model
	 */
/*	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest req, HttpServletResponse resp, ModelMap model){
		
		resp.setContentType("application/x-msdownload");
		try {
			ServletOutputStream output=resp.getOutputStream();
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = String.valueOf(simpleDateFormat.format(date));
			String fileName = StringUtils.toUtf8String("${table.className}_"+dateStr);
			//String fileName = "Test";
			resp.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".xlsx\"");
			
			List<Map<String,Object>> fieldData = this.${table.className?uncap_first}Service.findMapList(new ${table.className}());
			Map<String,String> fieldName = new LinkedHashMap<String,String>();
			<#list table.columnList as column>
			fieldName.put("${column.camelField}", <#if column.comment!="">"${column.comment}"<#else>"无列名"</#if>);
			</#list>
			ExcelUtil.export(fieldName, fieldData, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * excel导入
	 * TODO 导入成功回调提示
	 * @param req
	 * @param file
	 * @param model
	 * @throws IOException
	 */
/*	@RequestMapping("/importExcel")
	@ResponseBody
	public Map<String, Object> importExcel(HttpServletRequest req, 
			@RequestParam(value="excelFile", required=false) MultipartFile file) throws Exception{
		logger.debug("执行导入操作开始");
		Map<String, Object> map = new HashMap<>();
		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) file;
		try {
			List<Map<String,Object>> list = ExcelUtil.readExcelData(commonsMultipartFile);
			for (Map<String,Object> obj : list) {
				${table.className} ${table.className?uncap_first} = new ${table.className}();
				BeanUtils.populate(${table.className?uncap_first}, obj);
				//TODO 插入主键
				this.${table.className?uncap_first}Service.create${table.className}(${table.className?uncap_first});				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		map.put("tip", "导入成功啦啦啦");
		map.put("success", true);
		return map;
	}*/

	/**
	 * 下载模板
	 * @param model
	 * @param req
	 * @param resp
	 */
/*	@SuppressWarnings("unchecked")
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(String model,HttpServletRequest req, HttpServletResponse resp){
		Map<String, String> fieldName;
		try {
			if (model != null && ! "".equals(model)) {
				ObjectMapper mapper = new ObjectMapper();
				fieldName = mapper.readValue(model, LinkedHashMap.class);								
			} else {
				fieldName = new LinkedHashMap<String,String>();
				<#list table.columnList as column>
				<#if column.key!="PRI">
				fieldName.put("${column.camelField}", <#if column.comment!="">"${column.comment}"<#else>"无列名"</#if>);				
				</#if>
				</#list>
			}
			ServletOutputStream outputStream;
			
			outputStream = resp.getOutputStream();
			String fileName = StringUtils.toUtf8String("${table.className?uncap_first}Template");

			resp.setContentType("application/x-msdownload");
			resp.setHeader("Content-Disposition","attachment;filename=\"" + fileName + ".xlsx\"");

			ExcelUtil.exportTemplate(fieldName, outputStream);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}
	*/
	
	
	
	
}
