package com.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.util.PageData;


public interface IBaseMapper<T,K> {
	public void insert(T entity);
	public int insertList(List<T> list);
	public int delete(K id);
	public void deleteList(Map<String, Object> params);
	public void update(T entity);
	public List<T> selectByParams(Map<String, Object> parmas);
	public List<T> selectByPage(PageData pageData);
	public List<T> selectPageByParams(Map<String, Object> parmas);
	public T get(K id);
	public void updateField(Map<String, Object> params);
	public Integer count(Map<String, Object> params);
	public Double sum(Map<String, Object> params);
	public Object getScale(Map<String, Object> params);
	public List<Map<String, Object>> getScaleList(Map<String, Object> params);
	
	
	public void deleteForLogic(@Param("id")K id,@Param("customerId")long customerId);
}
