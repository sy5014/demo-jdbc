package ${table.projectName}.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import ${table.projectName}.bean.${table.className};
import ${table.projectName}.mapper.${table.className}Mapper;
import ${table.projectName}.service.${table.className}Service;

@Service("${table.className?uncap_first}Service")
@Transactional(rollbackFor=Exception.class)
public class ${table.className}ServiceImpl  implements ${table.className}Service {
	private static final Logger LOGGER=LoggerFactory.getLogger(${table.className}ServiceImpl.class);
	
	@Autowired
	private ${table.className}Mapper ${table.className?uncap_first}Mapper;
	
	@Override
	public Map<String, Object> create${table.className}(${table.className} ${table.className?uncap_first}) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		int flag = this.${table.className?uncap_first}Mapper.insertSelective(${table.className?uncap_first});
		if (flag != 1) {
			LOGGER.error("创建${table.className}失败! flag={}", flag);
			throw new Exception("创建${table.className}失败!");
		}
		resultMap.put("success", true);
		resultMap.put("msg", "创建${table.className} 成功!");
		LOGGER.info("创建${table.className} 成功 " + ${table.className?uncap_first}.toString());
		return resultMap;
	}
	
	@Override
	public Map<String, Object> create${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();

		if (${table.className?uncap_first}List == null || ${table.className?uncap_first}List.isEmpty()) {
			throw new Exception("无批量新增的数据");
		}
		
		for (${table.className} ${table.className?uncap_first} : ${table.className?uncap_first}List) {
			//TODO 可修改主键生成Id方式
			<#list table.columnList as column>
		    <#if column.key == "PRI">
		    //${table.className?uncap_first}.set${column.capitalizeCamelField}(UuidUtils.creatUUID());
		    <#break>
		    </#if>	
			</#list>
			if (this.${table.className?uncap_first}Mapper.insertSelective(${table.className?uncap_first}) != 1) {
				throw new Exception("新增数据失败!");
			}
		}
		resultMap.put("success", true);
		resultMap.put("msg", "批量创建${table.className} 成功!");
		return resultMap;

	}
	
	@Override
	public Map<String, Object> update${table.className}(${table.className} ${table.className?uncap_first}) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		int flag = this.${table.className?uncap_first}Mapper.updateByPrimaryKeySelective(${table.className?uncap_first});
		if (flag != 1) {
			LOGGER.error("更新${table.className} 失败! flag={}", flag);
			throw new Exception("update${table.className} failure!");
		}
		resultMap.put("success", true);
		resultMap.put("msg", "修改${table.className} 成功!");
		LOGGER.info("修改${table.className} 成功! " + ${table.className?uncap_first}.toString());
		return resultMap;
	}
	
	@Override
	public Map<String, Object> update${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		if (${table.className?uncap_first}List == null || ${table.className?uncap_first}List.isEmpty()) {
			throw new Exception("无批量新增的数据");
		}
		
		for (${table.className} ${table.className?uncap_first} : ${table.className?uncap_first}List) {
			if (this.${table.className?uncap_first}Mapper.updateByPrimaryKeySelective(${table.className?uncap_first}) != 1){
				throw new Exception("修改数据失败!");
			}
		}
		resultMap.put("success", true);
		resultMap.put("msg", "批量修改${table.className} 成功!");
		return resultMap;
	}


	@Override
	public Map<String, Object> delete${table.className}(${table.className} ${table.className?uncap_first}) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		//TODO 设置主键ID
		<#list table.columnList as column>
	    <#if column.key == "PRI">
	    String id = ${table.className?uncap_first}.get${column.capitalizeCamelField}();
	    <#break>
	    </#if>	
		</#list>
		int flag = this.${table.className?uncap_first}Mapper.deleteByPrimaryKey(id);
		if (flag != 1) {
			LOGGER.error("删除${table.className} 失败! flag={}", flag);
			throw new Exception("delete${table.className} failure!");
		}
		resultMap.put("success", true);
		resultMap.put("msg", "删除${table.className} 成功!");
		LOGGER.info("删除${table.className} 成功 key:{}", id);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> delete${table.className}(List<${table.className}> ${table.className?uncap_first}List) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		int count = 0;
		
		if (${table.className?uncap_first}List == null || ${table.className?uncap_first}List.isEmpty()) {
			throw new Exception("无批量删除的数据!");
		}
		
		for (${table.className} ${table.className?uncap_first} : ${table.className?uncap_first}List) {
			//TODO 设置主键ID
			<#list table.columnList as column>
		    <#if column.key == "PRI">
		    String id = ${table.className?uncap_first}.get${column.capitalizeCamelField}();
		    <#break>
		    </#if>	
			</#list>
			if (this.${table.className?uncap_first}Mapper.deleteByPrimaryKey(id) != 1) {
				throw new Exception("删除数据失败!");
			}
			count++;
		}
		resultMap.put("success", true);
		resultMap.put("msg", "批量删除${table.className} 成功!");
		resultMap.put("count", count);
		return resultMap;
	}
	
	@Override
	public ${table.className} find${table.className}ByPrimaryKey(String id) {
		return this.${table.className?uncap_first}Mapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<Map<String, Object>> findMapListByPage(${table.className} ${table.className?uncap_first}, Page<Map<String, Object>> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.${table.className?uncap_first}Mapper.selectMapList(${table.className?uncap_first});
		return page;
	}
	
	@Override
	public Page<${table.className}> findListByPage(${table.className} ${table.className?uncap_first}, Page<${table.className}> page) {
		page = PageHelper.startPage(page.getPageNum(), page.getPageSize());
		this.${table.className?uncap_first}Mapper.selectList(${table.className?uncap_first});
		return page;

	}

	@Override
	public List<Map<String, Object>> findMapList(${table.className} ${table.className?uncap_first}) {
		return this.${table.className?uncap_first}Mapper.selectMapList(${table.className?uncap_first});
	}
	
	@Override
	public List<${table.className}> findList(${table.className} ${table.className?uncap_first}){
		return this.${table.className?uncap_first}Mapper.selectList(${table.className?uncap_first});
	}






}
