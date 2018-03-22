package com.base.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

import com.base.dao.IBaseDao;
import com.base.mapper.IBaseMapper;
import com.base.util.PageData;
import com.base.util.SQLParamsUtil;



public class BaseDao<T,K> extends SqlSessionTemplate implements IBaseDao<T,K> {
//	@Autowired
//	protected SqlSession sqlSession;
	
	private Class<? extends IBaseMapper<T, K>> mapperClass; 
	
	/**
	 * 获取泛型类型
	 */
	@SuppressWarnings("rawtypes")
	public Class getGenericType(int index) {
		 Type genType = getClass().getGenericSuperclass();
		 if (!(genType instanceof ParameterizedType)) {
		 return Object.class;
		 }
		 Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		 if (index >= params.length || index < 0) {
		 throw new RuntimeException("Index outof bounds");
		 }
		 if (!(params[index] instanceof Class)) {
		  return Object.class;
		 }
		 return (Class) params[index];
	}
	
	protected IBaseMapper<T, K> getMapper(){
		return this.getMapper(mapperClass);
	}
	
	public void setMapperClass(Class<? extends IBaseMapper<T, K>> mapperClass) {
		this.mapperClass = mapperClass;
	}
	
	public BaseDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 保存
	 */
	public T save(T entity) {
		getMapper().insert(entity);
		return entity;
	}
	
	/**
	 * 批量保存
	 */
	@Override
	public int saveList(List<T> list) {
		// TODO Auto-generated method stub
		return getMapper().insertList(list);
	}
	
	/**
	 * 单个删除
	 */
	public int delete(K id) {
		// TODO Auto-generated method stub
		return getMapper().delete(id);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public void deleteList(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		getMapper().deleteList(params);
	}

	/**
	 * 更新整个实体
	 */
	public T update(T entity) {
		// TODO Auto-generated method stub
		getMapper().update(entity);
		return null;
	}
	
	/**
	 * 根据id查询单条记录
	 */
	public T get(K id) {
		return getMapper().get(id);
	}

	/**
	 * 根据参数多条记录
	 */
	@Override
	public List<T> list(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		return getMapper().selectByParams(params);
	}

	/**
	 * 根据分页参数进行分页查询
	 */
	@Override
	public List<T> listByPage(PageData pageData) {
		if(pageData == null)pageData = new PageData();
		pageData.setTotalSize(count(null));
		return getMapper().selectByPage(pageData);
	}
	
	/**
	 * 根据参数和分页属性进行分页查询
	 */
	@Override
	public List<T> listPageByParams(Map<String, Object> params, PageData pageData) {
		if(pageData == null)pageData = new PageData();
		if(params == null)params = new HashMap<String, Object>();
	
		//添加sql条件
		String listWhere = " where 1=1 " + SQLParamsUtil.tran2sql(params);
		params.remove("order_by");
		String countWhere = " where 1=1 " + SQLParamsUtil.tran2sql(params);
		params.put("where", countWhere);
		pageData.setTotalSize(count(params));
		//添加分页功能
		params.put("where", listWhere);
		params.put("pageData", pageData);
		return getMapper().selectPageByParams(params);
	}

	/**
	 * 统计条数
	 */
	@Override
	public Integer count(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		return getMapper().count(params);
	}
	
/**
 * 部分更新
 */
	@Override
	public void updateField(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		//2014-11-04 linjian 判断是否需要添加数据版本号限制，使用乐观锁解决并发问题
		try {
			if(getGenericType(0).getDeclaredField("record_version") != null){
				params.put("update_record_version", "$record_version+1");
			}
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		}
			//添加更新sql条件
		String set_sql = SQLParamsUtil.tran2updateSQL(params);
		if(StringUtils.isNotBlank(set_sql)){
			params.put("set_sql", set_sql);
		}
		getMapper().updateField(params);
	}

	/**
	 * 获取指定字段的值
	 */
	@Override
	public Object getScale(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		return getMapper().getScale(params);
	}

	/**
	 * 获取多条指定字段值
	 */
	@Override
	public List<Map<String, Object>> getScaleList(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		return getMapper().getScaleList(params);
	}
	
	/**
	 * 根据参数获取一条记录
	 */
	@Override
	public T get(Map<String, Object> params) {
		if(params == null)params = new HashMap<String, Object>();
		if(!params.containsKey("where")){
			//添加sql条件
			params.put("where", " where 1=1 " + SQLParamsUtil.tran2sql(params));
		}
		List<T> list = getMapper().selectByParams(params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

    /**
     * 逻辑删除
     */
    @Override 
    public void deleteForLogic(K id, long customerId) {
        getMapper().deleteForLogic(id, customerId);
    }

}
