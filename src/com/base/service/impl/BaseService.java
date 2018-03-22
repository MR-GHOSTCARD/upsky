package com.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.dao.impl.BaseDao;
import com.base.exception.SimpleException;
import com.base.service.IBaseService;
import com.base.util.PageData;


public class BaseService<T,K> implements IBaseService<T, K> {
	
	private BaseDao<T, K> dao;
	public BaseService(BaseDao<T, K>o){
		this.dao = o;
	}
	public T save(T entity) {
		return dao.save(entity);
	}

	public void delete(K id) {
		dao.delete(id);
	}
	public void deleteList(Map<String, Object> params){
		dao.deleteList(params);
	}

	public T update(T entity) {
		return dao.update(entity);
	}
	

	public T get(K id) {
		return dao.get(id);
	}

	@Override
	public List<T> list(Map<String, Object> params) {
		return dao.list(params);
	}
	@Override
	public List<T> listByPage(PageData pageData) {
		return dao.listByPage(pageData);
	}
	@Override
	public Integer count(Map<String, Object> params) {
		return dao.count(params);
	}
	@Override
	public List<T> listPageByParams(Map<String, Object> params,
			PageData pageData) {
		return dao.listPageByParams(params, pageData);
	}
	@Override
	public void updateField(Map<String, Object> params) {
		 dao.updateField(params);
	}
	@Override
	public Object getScale(Map<String, Object> params) {
		return dao.getScale(params);
	}
	@Override
	public T get(Map<String, Object> params) {
		return dao.get(params);
	}
	@Override
	public int saveList(List<T> list) {
		if(list != null && list.size() > 0)
			return dao.saveList(list);
		else
			return 0;
	}
	@Override
	public List<Map<String, Object>> getScaleList(Map<String, Object> params) {
		return dao.getScaleList(params);
	}
	@Override
	public List<Object> getScaleListOne(Map<String, Object> params) {
		List<Map<String, Object>> list = dao.getScaleList(params);
		List<Object> list_obj = new ArrayList<Object>();
		for (Map<String, Object> map : list) {
			if(map.containsKey("rs"))
				list_obj.add(map.get("rs"));
		}
		return list_obj;
	}
	
    /**
     * 逻辑删除
     * 作者：郑建璋
     * 创建时间：2016年6月16日下午2:25:45
     * @param id
     * @param custmerId
     */
    @Override 
    public void deleteForLogic(K id, long customerId) {
        if(id==null)throw new SimpleException("参数错误！");
        dao.deleteForLogic(id, customerId);
    }

}
