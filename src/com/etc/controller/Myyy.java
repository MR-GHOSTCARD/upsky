package com.etc.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.util.PageData;
import com.etc.controller.base.BaseController;
import com.etc.module.Role;
import com.etc.service.IRoleService;
import com.etc.utils.PdfEdit;
import com.etc.utils.VerifyCodeUtils;
import com.etc.utils.ZipEdit;
import com.etc.utils.modules.VerifyCode;

@Controller
public class Myyy {
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value="hello.htm")
	public String hello(HttpServletRequest request,PageData pageData){
	//	System.out.println(request.getSession().getServletContext().getRealPath("/"));
		Map<String, Object> params=new HashMap<String,Object>();
		List<Role> list=roleService.listPageByParams(params,pageData);
		return "index";
	}
	
	@RequestMapping(value="ajaxGetPic.htm",produces="application/json")
	public @ResponseBody Map<String, Object> ajaxGetPic(String a,String in,String file,HttpServletRequest request,ModelMap modelMap){
		Map<String, Object> result=new HashMap<String,Object>();
		VerifyCodeUtils codeUtils=new VerifyCodeUtils();
		VerifyCode verifyCode=codeUtils.getVerifyCodePic();
		request.getSession().setAttribute("code", verifyCode.getCode());
		result.put("code",verifyCode.getUrl());
		return result;
	}
	
	@RequestMapping(value="checkCode.htm",produces="application/json")
	public @ResponseBody Map<String, Object> checkCode(String code,HttpServletRequest request){
		Map<String, Object> result=new HashMap<String,Object>();
		String sessionCode=(String)request.getSession().getAttribute("code");
		if(StringUtils.isNotBlank(code)){
			if(code.equals(sessionCode)){
				result.put("status", "success");
				result.put("msg","验证成功");
				request.getSession().removeAttribute("code");
			}else{
				result.put("status", "error");
				result.put("msg","验证失败");
			}
		}else{
			result.put("status", "error");
			result.put("msg","验证失败");
		}
		
		return result;
	}
	
	@RequestMapping(value="chat.htm")
	public String chat(){
		return "login";
	}
	
	@RequestMapping(value="map.htm")
	public String map(){
		return "map";
	}
	
	@RequestMapping(value="download.htm")
	public void download(String path,HttpServletResponse response){
		PdfEdit pdfEdit=new PdfEdit(path);
		pdfEdit.setField("fill_1", "车前程111");
		pdfEdit.setField("fill_2", "sdasd");
		response.reset();
		response.setContentType("application/x-download");
		response.addHeader("Content-Disposition", "attachment;filename=hehe.pdf");
		byte[] bytes=pdfEdit.outFileToDownload();
		InputStream inputStream=new ByteArrayInputStream(bytes);
		int len=0;
		byte[] buffer=new byte[bytes.length];
		try {
			OutputStream out=response.getOutputStream();
			while((len=inputStream.read(buffer))>0){
				out.write(buffer,0,len);
			}
			out.flush();
			out.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="getZip.htm")
	public void getZip(String path,HttpServletResponse response){
		response.reset();
		response.setContentType("application/x-download");
		response.setCharacterEncoding("utf-8");
		String suffixName="attachment;filename=压缩.zip"; 
		response.addHeader("Content-Disposition", BaseController.toUtf8String(suffixName));
		try {
			ZipEdit.fileToZip("http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/72a9eb6fb8724ac0a97217240461984c,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/d9c12136b7af48acb3d6d4ad11569eb1,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/711bc2f1ce944df2bf0de4c18c6713dd,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/beec79a5ae5646dfa91a214d1c804653,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/cfe5ada7d8384a09a930b3e1d6fddde1,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/943053a5f1d24604a6caf72e1fd33900,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/884447ab293e4df0920956ba486df915,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/23482d30c3f84988a4abb755669638b5,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/cf08d39f5918450f89f9a87eaa06c2ff,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/691529ca9a5b4076aff06988a0c9877f,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/28352856c5024d5bb467440aef8d73fd,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/29f4ae9a8bbd453fa65264a91c540d26,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/60ce81aff9e24cadb93e9e20dcad8af8,http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/0ffab83b57704dbc91f0156b057805a2", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
