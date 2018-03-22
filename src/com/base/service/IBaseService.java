package com.base.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.util.PageData;


public interface IBaseService<T,K> {
	public T save(T entity);
	public int saveList(List<T> list);
	public void delete(K id);
	public void deleteList(Map<String, Object> params);
	public T update(T entity);
	public T get(K id);
	public T get(Map<String, Object> params);
	public List<T> list(Map<String, Object> params);
	public List<T> listByPage(PageData pageData);
	public List<T> listPageByParams(Map<String, Object> params,PageData pageData);
	public void updateField(Map<String, Object> params);
	public Integer count(Map<String, Object> params);
	public Object getScale(Map<String, Object> params);
	public List<Map<String, Object>> getScaleList(Map<String, Object> params);
	public List<Object> getScaleListOne(Map<String, Object> params);
	
	public void deleteForLogic(@Param("id")K id,@Param("custmerId")long customerId);
}
