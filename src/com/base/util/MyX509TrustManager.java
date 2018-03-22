package com.base.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 版权所有：山东易运输信息科技有限公司
 * 文件名称: MyX509TrustManager.java
 * 修订记录：
 * 序号          日期				             作者(操作:具体内容)
 * 1          2016年3月11日			 林辉(创建:创建文件)
 *====================================================
 * 类描述：证书信任管理器(用于https请求)
 */
public class MyX509TrustManager implements X509TrustManager{

	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {	
	}

	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1)
			throws CertificateException {
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}
