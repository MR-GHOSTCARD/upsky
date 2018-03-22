package com.base.util;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: SystemConfig.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年5月26日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：读取配置文件
 */
public class SystemConfig {
	/**
	 * 获取微信appid
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午10:15:28
	 * @return
	 */
	public static String getWeixinAppId(){
		return SysProperty.getProperty("weixin_app_id");
	}
	
	/**
	 * 获取微信secret
	 * 作者：林辉
	 * 创建时间：2016年3月11日 上午10:15:28
	 * @return
	 */
	public static String getWeixinSecret(){
		return SysProperty.getProperty("weixin_secret");
	}
	
	/**
	 * 获取冻结原因
	 * 作者：郑建璋
	 * 创建时间：2016年5月4日 上午10:24:56
	 * @return
	 */ 
	public static String getFreezeReason(){
		return SysProperty.getProperty("freeze_reason");
	}
	
	/**
	 * 获取账户IO类型
	 * 作者：郑建璋
	 * 创建时间：2016年5月4日 上午10:26:35
	 * @return
	 */ 
	public static String getAccountIoType(){
		return SysProperty.getProperty("account_io_type");
	}
	
	/**
	 * 获取支付类型
	 * 作者：郑建璋
	 * 创建时间：2016年5月4日 上午10:27:21
	 * @return
	 */ 
	public static String getPayType(){
		return SysProperty.getProperty("pay_type");
	}
	
	/**
	 * 获取分配类型
	 * 作者：郑建璋
	 * 创建时间：2016年5月4日 上午10:50:52
	 * @return
	 */ 
	public static String getAccountRecharge(){
		return SysProperty.getProperty("account_recharge");
	}
	/**
	 * 获取区域供应商
	 * 作者：郑建璋
	 * 创建时间：2016年8月19日上午10:47:33
	 * @return
	 */
	public static String getSupplierName(String name){
		String namepinyin=PinyinUtil.hanziToPinyin(name).replace(" ", "");
		return SysProperty.getProperty("oil_supplier_area_"+namepinyin);
	}
}
