<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/base64.js"></script>
<script src="js/tripledes2.js"></script>  
<script src="js/mode-ecb.js"></script>  
</head>
<body>
	<input type="text" id="ans"/>
	<button id="btn">确定</button>
	<div class="imgg" style="width:100px;height:100px;"></div>
	<progress>
	<span id="objprogress">85</span>%
	</progress>
	<input type="text" id="path" value="http://files-cqc.oss-cn-zhangjiakou.aliyuncs.com/7fea0c0eb14d419aa9428944129090d7.pdf"/>
	<button id="download">下载</button>
</body>
<script>
$(function(){
	getCode();
	
	function getCode(){
		$.ajax({
			url:'ajaxGetPic.htm',
			type:'post',
			data:{
				a:'a'
			},
			dataType:'json',
			success:function(data){
				//alert(jsondata.code);
				$('.imgg').html('');
				var str='<image id="code" src="data:image/png;base64,'+data.code+'">';
				$('.imgg').html(str);
			}
		});
	};
	$('#download').click(function(){
	/*	$.ajax({
			url:'download.htm',
			type:'post',
			data:{path:$('#path').val()},
			dataType:'json',
			success:function(data){
				
			}
		});*/
		location.href='download.htm?path='+$('#path').val();
	});
	$('#btn').click(function(){
		$.ajax({
			url:'checkCode.htm',
			type:'post',
			data:{
				code:$('#ans').val()
			},
			dataType:'json',
			success:function(data){
				alert(data.msg)
			}
		});
	});
	
	$(document).on('click','#code',function(){
		getCode();
	});
	
	 function decryptByDES(ciphertext, key) {    
         var keyHex = CryptoJS.enc.Utf8.parse(key);    
          
         // direct decrypt ciphertext  
         var decrypted = CryptoJS.TripleDES.decrypt({    
             ciphertext: CryptoJS.enc.Base64.parse(ciphertext)    
         }, keyHex, {    
             mode: CryptoJS.mode.ECB,    
             padding: CryptoJS.pad.Pkcs7    
         });    
          
         return decrypted.toString(CryptoJS.enc.Utf8);    
     }  
	
	//ba('201801171617514','612701199104082014','LFP83ACC3D1G06899','2016-11-04');
	ba('','612701199104082014','LFP83ACC3D1G06899','2016-11-04');
	function ba(salesmanNo,idCard,carFrameNo,outMoneyDate){
		$.ajax({
			url:'http://10.59.2.118:8094/cqc/pc/walletGoodCar/outOrIn/getRepayJson.htm',
			type:'post',
			data:{
				salesmanNo:salesmanNo,
				idCard:idCard,
				carFrameNo:carFrameNo,
				outMoneyDate:outMoneyDate
			},
			dataType:'json',
			success:function(data){
				console.log(data)
				/*
				var map=eval(data);
				var key="123";
				if(salesmanNo!=null&&$.trim(salesmanNo)!=''){
					key=salesmanNo;
				}else if(idCard!=null&&idCard!=''){
					key=idCard;
				}
				var content=map['returnContent'];
				content=decryptByDES(content,key)
			//	console.log(data)
				var b=new Base64();
				var str=b.decode(content);
				console.log(str)*/
			}
		})
	}
});
</script>
</html>