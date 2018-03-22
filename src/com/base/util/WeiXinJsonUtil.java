package com.base.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;


/**
 * Json辅助类
 * @author wucc
 * @version 1.0
 *
 */
public class WeiXinJsonUtil {
	
	/**
	 * Map 转化为对象类型
	 * @param maps
	 * @param cls
	 * @return 对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object map2bean(Map maps, Class<?> cls){
		Object obj = null;
		try {
			obj = cls.newInstance();
			for (Method mth : cls.getDeclaredMethods()) {
				if(mth.getName().contains("set")){
					String fieldName = ((mth.getName().charAt(3) + "") + mth.getName().substring(4)).toLowerCase();
					//如果拥有该属性
					Set<Entry> entries = maps.entrySet() ;
					for (Entry entry : entries) {
						if(entry.getKey().toString().toLowerCase().equals(fieldName)){
							//属性为String类型转换
							if(mth.getParameterTypes()[0].getSimpleName().equals("String")){
								mth.invoke(obj, entry.getValue());
							//Integer类型
							}else if(mth.getParameterTypes()[0].getSimpleName().equals("Integer")){
								mth.invoke(obj, entry.getValue() != null ?Integer.parseInt(entry.getValue().toString()):null);
							}
							else if(mth.getParameterTypes()[0].getSimpleName().equals("Long")){
								mth.invoke(obj, entry.getValue() != null ?Long.valueOf(entry.getValue().toString()):null);
							}
							else if(mth.getParameterTypes()[0].getSimpleName().equals("Double")){
								mth.invoke(obj, entry.getValue() != null ?Double.valueOf(entry.getValue().toString()):null);
							}
							//Date类型
							else if(mth.getParameterTypes()[0].getSimpleName().equals("Date")){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								mth.invoke(obj, sdf.parse(entry.getValue().toString()));
							}
						}
					}
				}// end if
			}//end for
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return obj;
	} 
	/**
	 * 将对象转换成Json,默认追踪属性第三层
	 * @param obj 要转换的对象
	 * @return String Json 
	 */
	public static String object2json(Object obj) {
		return object2json(obj,1,12);
	}
	/**
	 * 将对象转换成Json
	 * @param obj 要转换的对象
	 * @param layer 需求的对象属性的层次
	 * @return String Json 
	 */
	public static String object2json(Object obj,int layer){
		return object2json(obj,1,layer);
	}
	/**
	 * 将对象转换成Json
	 * @param obj 要转换的对象
	 * @param current 当前递归层次
	 * @param layer 需求的对象属性的层次
	 * @return String Json 
	 */
	public static String object2json(Object obj,int current,int layer) {
		//如果达到该层次就跳出
		if(current == layer)return "\"\"";
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		}
		// 对象类型是简单类型(string ,int float,boolean , short, double...)
		else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		}
		// 对象类型是List集合
		else if (obj instanceof List) {
			json.append(list2json((List<?>) obj, current + 1,layer));
		}
		// 对象类型是Map
		else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj, current + 1,layer));
		}
		// 对象类型是set
		else if (obj instanceof Set) {
			json.append(set2json((Set<?>) obj, current + 1,layer));
		} 
		// 自定义类型
		else {
			json.append(bean2json(obj,current + 1,layer));
		}
		return json.toString();
	}

	/**
	 * 自定义对象转换成Json对象
	 * @param bean 自定义对象
	 * @return 
	 */
	private static String bean2json(Object bean,int current,int layer) {
		//如果达到该层次就跳出
		if(current == layer)return "\"\"";
		StringBuilder json = new StringBuilder();
		try {
			json.append("{");
			PropertyDescriptor[] props = null;
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					// 获取名称
					String name = object2json(props[i].getName(),current + 1,layer);
					// 用于读取属性值
					String value = object2json(props[i].getReadMethod().invoke(bean), current + 1,layer);
					json.append(name);
					json.append(":");
					json.append(value);
					json.append(",");
				}
				json.setCharAt(json.length() - 1, '}');
			} else {
				json.append("}");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 将list转换成json
	 * @param list list集合
	 * @return
	 */
	private static String list2json(List<?> list,int current, int layer) {
		//如果达到该层次就跳出
		if(current == layer)return "\"\"";
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(object2json(obj, current + 1,layer));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * Map转换成json
	 * @param map转换的Map
	 * @return
	 */
	private static String map2json(Map<?, ?> map,int current, int layer) {
		//如果达到该层次就跳出
		if(current == layer)return "\"\"";
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Entry<?, ?> entry : map.entrySet()) {
				json.append(object2json(entry.getKey(), current + 1,layer));
				json.append(":");
				json.append(object2json(entry.getValue(), current + 1,layer));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	private static String set2json(Set<?> set,int current, int layer) {
		//如果达到该层次就跳出
		if(current == layer)return "\"\"";
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (set != null && set.size() > 0) {
			for (Object obj : set) {
				json.append(object2json(obj, current + 1, layer));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	private static String string2json(String s) {
		return s;
//		if (s == null)
//			return "";
//		StringBuilder sb = new StringBuilder();
////		for (int i = 0; i < s.length(); i++) {
////			char ch = s.charAt(i);
////			switch (ch) {
//////			case '"':
////				sb.append("\"");
////				break;
////			case '\\':
////				sb.append("\\");
////				break;
////			case '\b':
////				sb.append("\\b");
////				break;
////			case '\f':
////				sb.append("\\f");
////				break;
////			case '\n':
////				sb.append("\\n");
////				break;
////			case '\r':
////				sb.append("\\r");
////				break;
////			case '\t':
////				sb.append("\\t");
////				break;
////			case '/':
////				sb.append("\\/");
////				break;
////			default:
////				if (ch >= '\u0000' && ch <= '\u001F') {
////					String ss = Integer.toHexString(ch);//以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式
////					sb.append("\\u");
////					for (int k = 0; k < 4 - ss.length(); k++) {
////						sb.append('0');
////					}
////					sb.append(ss.toUpperCase());
////				} else {
////					sb.append(ch);
////				}
//			}
//		}
//		return sb.toString();
	}
	/**
	 * 把字符串的json对象反转为Map<String,Object>对象
	 * @param json
	 * @return 配置需要的Map对象
	 */
	public static Map<String,Object> Json2Object(String json){
		Map<String,Object> obj = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(json)){
			//从第一个双引号开始就是key
			int flagstart = json.indexOf("\"");
			int flagend = json.indexOf("\"",flagstart + 1);
			while(flagstart > 0 && flagend > 0){
			//第一个双引号到接下来的一个就是key的内容
				String key = json.substring(flagstart + 1, flagend);
				//value
				Object val = null;
				flagstart = json.indexOf("\"",flagend + 1);
				flagend = json.indexOf("\"",flagstart + 1);
				//有嵌套json对象
				if(json.indexOf("{",flagstart) > 0){
					//另行操作
					int end = 0;
					int count = 1;
					int flag = json.indexOf("{",flagstart);
					int startout = flag;
					while(flag > 0){
						flag = json.indexOf("{",flag + 1);
						if(flag > 0 && json.substring(startout,flag).indexOf("}") > 0){
							break;
						}
						count ++;
					}
					//记录多少个“{”号
					flag = flagstart;
					while(count > 0){
						flag = json.indexOf("}",flag + 1);
						if(count == 1){
							end = flag;
						}
						count --;
					}
					String tempstr = json.substring(json.indexOf("{",flagstart),end + 1);
					val = Json2Object(tempstr);
					flagend = end + 1;
				
				}else{
					val = json.substring(flagstart + 1, flagend);
				}
				flagstart = json.indexOf("\"",flagend + 1);
				flagend = json.indexOf("\"",flagstart + 1);
				obj.put(key, val);
			}
		}
		return obj;
	}
	
	/**
	 * 把字符串的json对象反转为Map<String,String>对象
	 * @param json
	 * @return 配置需要的Map对象
	 */
	public static Map<String,String> Json2String(String json){
		Map<String,String> obj = new HashMap<String,String>();
		if(StringUtils.isNotBlank(json)){
			
			//从第一个双引号开始就是key
			int flagstart = json.indexOf("\"");
			int flagend = json.indexOf("\"",flagstart + 1);
			while(flagstart > 0 && flagend > 0){
			//第一个双引号到接下来的一个就是key的内容
				String key = json.substring(flagstart + 1, flagend);
				//value
				Object val = null;
				flagstart = json.indexOf("\"",flagend + 1);
				flagend = json.indexOf("\"",flagstart + 1);
				//有嵌套json对象
				if(json.indexOf("{",flagstart) > 0){
					//另行操作
					int end = 0;
					int count = 1;
					int flag = json.indexOf("{",flagstart);
					int startout = flag;
					while(flag > 0){
						flag = json.indexOf("{",flag + 1);
						if(flag > 0 && json.substring(startout,flag).indexOf("}") > 0){
							break;
						}
						count ++;
					}
					//记录多少个“{”号
					flag = flagstart;
					while(count > 0){
						flag = json.indexOf("}",flag + 1);
						if(count == 1){
							end = flag;
						}
						count --;
					}
					String tempstr = json.substring(json.indexOf("{",flagstart),end + 1);
					val = Json2Object(tempstr);
					flagend = end + 1;
				
				}else{
					val = json.substring(flagstart + 1, flagend);
				}
				flagstart = json.indexOf("\"",flagend + 1);
				flagend = json.indexOf("\"",flagstart + 1);
				obj.put(key, val.toString());
			}	
		}
		return obj;
	}
	
	/**
	 * 与全局变量合并获取全局变量
	 * @param params 当前参数
	 * @param globalparams 全局参数
	 * @return 合并后的参数
	 */
	public static Map<String, Object> mergeParams(Map<String, Object> params,Map<String, String> globalparams){
		for (Entry<String, Object> e : params.entrySet()) {
			if(e.getValue() == null || e.getValue().toString().equals("") || e.getValue().toString().equals("default") || e.getValue().toString().equals("global")){
				if(globalparams.containsKey(e.getKey())){
					params.put(e.getKey(), globalparams.get(e.getKey()));
				}
			}
		}
		return params;
	}
	
	/**
	 * 把字符串分割成数组
	 * @param content 要分割的字符串
	 * @param spstr 分割字符
	 * @return 分割后的数组
	 */
	public static String[] convertStr2List(String content,String spstr){
		if(content != null && !content.equals("")){
			return content.split(spstr);
		}
		return null;
	}
	/**
	 * 判断Map中是否包含指定的key,如果包含返回value,否则返回null
	 * @param key
	 * @param map
	 * @return value || null
	 */
	@SuppressWarnings("rawtypes")
	public static Object getValue(String key, Map map){
		if(map.containsKey(key)){
			return map.get(key);
		}else{
			return null;
		}
	}
	
	/**
	 * 把对象转换成map对象
	 * @param obj
	 * @return
	 */
	public static Map<String,Object> bean2map(Object obj){
		
		Map<String, Object> maps = new HashMap<String, Object>();
		
		for (Method mth : obj.getClass().getMethods()) {
			
			if(mth.getName().contains("get")){
				
				String field = (mth.getName().replace("get", "").charAt(0) + "").toLowerCase() + mth.getName().replace("get", "").substring(1);
				try {
					
					//排除getclass方法
					if(!field.equals("class"))
					maps.put(field, mth.invoke(obj));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return maps;
	}
}
