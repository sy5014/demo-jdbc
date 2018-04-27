package ${table.packageName};
<#setting number_format="#">
import java.io.Serializable;
import org.apache.commons.lang3.time.DateUtils;
import ${table.projectName}.bean.${table.className};
import ${table.projectName}.dao.${table.className}Dao;
import ${table.projectName}.service.${table.className}Service;

public class ${table.className}Test {

	
	public void testBuildObject() {
		${table.className} ${table.className?uncap_first} = new ${table.className}();
		<#list table.columnList as column>
		<#if column.javaType == 'String'>
		${table.className?uncap_first}.set${column.capitalizeCamelField}("${column.value?default('undefined')}");
		<#elseif column.javaType == 'BigDecimal'>
		${table.className?uncap_first}.set${column.capitalizeCamelField}(${column.value?default(0)});
		<#elseif column.javaType == 'Date'>
		${table.className?uncap_first}.set${column.capitalizeCamelField}(DateUtils.parseDate("${column.value?default(0)}", "yyyy-MM-dd HH:mm:ss"));
		<#else>
		${table.className?uncap_first}.set${column.capitalizeCamelField}(${column.value?default('"undefined"')});
		</#if>
		</#list>
	}
	
	
}