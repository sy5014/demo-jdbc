package ${table.packageName};

import java.util.List;
import java.util.Map;
import ${table.projectName}.bean.${table.className};

//@MyBatisRepository //使用具体项目使用的注解标记接口生成接口代理实现类
public interface ${table.className}Mapper {

	/**
	 * 增加记录(全字段)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	int insert(${table.className} ${table.className?uncap_first});
	/**
	 * 增加记录(可选字段)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	int insertSelective(${table.className} ${table.className?uncap_first});
	/**
	 * 批量增加记录(全字段)
	 * @param list ${table.className}集合列表
	 * @return
	 */
	int insertByBatch(List<${table.className}> list);
	/**
	 * 批量增加记录(可选字段)
	 * @param list ${table.className}集合列表
	 * @return
	 */
	int insertSelectiveByBatch(List<${table.className}> list);
	/**
	 * 修改单条记录(全字段)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	int updateByPrimaryKey(${table.className} ${table.className?uncap_first});
	/**
	 * 修改单条记录(可选字段)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	int updateByPrimaryKeySelective(${table.className} ${table.className?uncap_first});
	/**
	 * 批量修改记录(全字段)
	 * @param list ${table.className}集合列表
	 * @return
	 */
	int updateByBatch(List<${table.className}> list);
	/**
	 * 批量修改记录(可选字段)
	 * @param list ${table.className}集合列表
	 * @return
	 */
	int updateSelectiveByBatch(List<${table.className}> list);

	/**
	 * 删除记录
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);
	/**
	 * 根据key查询记录
	 * @param id
	 * @return
	 */
	${table.className} selectByPrimaryKey(String id);
	/**
	 * 查询记录列表(以对象List返回)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	List<${table.className}> selectList(${table.className} ${table.className?uncap_first});
	/**
	 * 查询记录列表(以Map封装对象信息 以List返回)
	 * @param ${table.className?uncap_first}
	 * @return
	 */
	List<Map<String, Object>> selectMapList(${table.className} ${table.className?uncap_first});


}
