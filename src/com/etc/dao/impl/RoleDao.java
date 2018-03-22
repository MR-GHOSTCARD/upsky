package com.etc.dao.impl;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.base.dao.impl.BaseDao;
import com.etc.dao.IRoleDao;
import com.etc.mapper.IRoleMapper;
import com.etc.module.Role;

@Repository
public class RoleDao extends BaseDao<Role, Integer> implements IRoleDao {

	@Autowired
	public RoleDao(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
		this.setMapperClass(IRoleMapper.class);
	}
}
