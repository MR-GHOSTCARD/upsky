package com.base.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: SQLParamsUtil.java.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年2月20日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：sql数据库参数加工类
 */
public class SQLParamsUtil {
	
	/**
	 * 解析参数防止SQL注入
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:58:51
	 * @param param
	 * @return
	 */
	public static String paramsEscape(String param){
		if(StringUtils.isBlank(param)){return param;}
		return param.replace("'", "''");
	}
	
	/**
	 * 把Map形式的参数加工成sql条件参数
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:58:58
	 * @param params
	 * @return
	 */
	public static String tran2sql(Map<String, Object> params){
		//数据权限的条件约束
		String check_where = "";
		if(params != null && params.containsKey("check_where")){
			check_where = params.get("check_where").toString();
			params.remove("check_where");
		}
		
		//拼接sql
		String sql = "";
		if(params != null){
			for (Map.Entry<String,Object> entry: params.entrySet()) {
				String key = entry.getKey();
				if(entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString()) && !key.equals("order_by") && !key.equals("limit") && !key.equals("column") && !key.equals("group_by")){
					if(key.indexOf("or_") == 0){
						if(StringUtils.isBlank(sql)){
							sql = sql + " and ";
						}else{
							sql = sql + " or ";
						}
					}//以and_开头的
					else{
						sql = sql + " and ";
					}//end if
					//条件方式
					if(key.indexOf("_not_like") != -1 && key.indexOf("_not_like") == (key.length() - 9)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_not_like", "") + " not like '%" + entry.getValue().toString().trim() + "%'";
					}else if(key.indexOf("_like") != -1 && key.indexOf("_like") == (key.length() - 5)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_like", "") + " like '%" + entry.getValue().toString().trim() + "%'";
					}else if(key.indexOf("_not_in") != -1 && key.indexOf("_not_in") == (key.length() - 7)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_not_in", "") + " not in (" + entry.getValue().toString().trim() + ")";
					}else if(key.indexOf("_in") != -1 && key.indexOf("_in") == (key.length() - 3)){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_in", "") + " in (" + entry.getValue().toString().trim() + ")";
					}else if(key.indexOf("_sql") != -1){
						sql = sql + entry.getValue().toString().trim();
					}else if(key.contains("<>")){
						sql = sql + key.replace("and_", "").replace("or_", "").replace("_<>", "") + " <> '" + entry.getValue().toString().trim() + "'";
					}else if(key.contains("<=")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_<=", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') <= to_timestamp('" + entry.getValue().toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_<=", "") + " <= '" + entry.getValue().toString().trim() + "'";
					}else if(key.contains(">=")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_>=", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') >= to_timestamp('" + entry.getValue().toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_>=", "") + " >= '" + entry.getValue().toString().trim() + "'";
					}else if(key.contains("<")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_<", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') < to_timestamp('" + entry.getValue().toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_<", "") + " < '" + entry.getValue().toString().trim() + "'";
					}else if(key.contains(">")){
						if(key.contains("$time")){
							sql = sql +  "to_timestamp(" + key.replace("and_", "").replace("or_", "").replace("_>", "").replace("$time", "") + ",'YYYY-MM-DD HH24:MI:SS') > to_timestamp('" + entry.getValue().toString().trim() + "','YYYY-MM-DD HH24:MI:SS')";
						}else
							sql = sql + key.replace("and_", "").replace("or_", "").replace("_>", "") + " > '" + entry.getValue().toString().trim() + "'";
					}else if(entry.getValue().toString().contains("is_null")){
						sql = sql + key.replace("and_", "").replace("or_", "") + " is null ";
					}else if(entry.getValue().toString().contains("is_not_null")){
						sql = sql + key.replace("and_", "").replace("or_", "") + " is not null ";
					}else{
						sql = sql + key.replace("and_", "").replace("or_", "") + " = '" + entry.getValue().toString().trim() + "'";
					}
				}//end if
			}//end for
			//数据权限条件
			sql = sql + " " + check_where;
			
			//添加group by语句	youcb	201411121
			if(params.containsKey("group_by")){				
				sql = sql + "group by " + params.get("group_by").toString();
			}
			//排序条件
			if(params.containsKey("order_by")){
				sql = sql + " order by " + params.get("order_by").toString().replace("_desc", " desc").replace("_asc", " asc");
			}
			//分页条件
			if(params.containsKey("limit")){
				sql = sql + " limit " + params.get("limit");
			}
		}
		return sql;
	}
	
	/**
	 * 转换sql更新语句
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:59:06
	 * @param params
	 * @return
	 */
	public static String tran2updateSQL(Map<String, Object> params){
		// 初始化参数
		StringBuffer sql = new StringBuffer();
		sql.append("set ");
		Map<String, Object> whereparams = new HashMap<String, Object>();
		
		// 2014-10-22 chenfm 修改更新sql语句组合
		for (Map.Entry<String,Object> entry : params.entrySet()) {
			// 更新语句组合
			if(StringUtils.isNotBlank(entry.getKey()) && entry.getKey().contains("update_")){
				if(entry.getKey().replace("update_", "").equals("sql")){
					sql.append(" " + entry.getValue() + " ");
				}else{
					/***
					 * 2014-11-04 linjian 是否去掉单引号
					 */
					if(entry.getValue() != null){
					    if(entry.getValue().toString().indexOf("$") == 0){
					        sql.append(" " + entry.getKey().replace("update_", "") + " = " + entry.getValue().toString().substring(1) + ",");
					    }else{
		                    sql.append(" " + entry.getKey().replace("update_", "") + " = '" + entry.getValue() + "',");
					    }
					}else{
						sql.append(" " + entry.getKey().replace("update_", "") + " = NULL,");
					}
				}
			}
			// 条件语句
			else if(StringUtils.isNotBlank(entry.getKey()) && entry.getKey().contains("where_") 
					&& entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())){
				
				whereparams.put(entry.getKey().replace("where_", ""), entry.getValue());
			}
		}
		
		String sql_ = sql.toString();
		if(!sql_.equals("set ")){
			//不允许没有条件完全更新
			if(whereparams != null && whereparams.keySet().size() > 0){
				//去掉最后一个逗号
				sql_ = sql_.substring(0, sql.length() - 1) + " where 1=1 " + tran2sql(whereparams);
			}else{
				return "";
			}
		}else{
			return "";
		}
		return sql_;
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("order_by", "to_number(substring(replace(assert_formula, '分', '') from '\\d+$'),'999')_desc");
		System.out.println(tran2sql(params));
	}
	
	
	/**
	 * 将字符串转为in可查询的字符串
	 * 作者：林辉
	 * 创建时间：2016年2月20日 下午3:59:16
	 * @param val
	 * @return
	 */
	public static String sqlInState(String val){
		StringBuffer hqlparams = new StringBuffer();
		String[] vals = val.replaceAll(" ", "").split(",");
		for (int i = 0; i < vals.length; i++) {
			if(StringUtils.isNotBlank(vals[i])){
				hqlparams.append("'").append(vals[i]).append("'");
				if(i<vals.length-1){
					hqlparams.append(",");
				}
			}else {
				continue;
			}
		}
		return hqlparams.toString();
	}
}
