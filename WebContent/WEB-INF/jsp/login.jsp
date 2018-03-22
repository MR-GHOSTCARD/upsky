<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/sockjs.min.js"></script>
</head>
<body>
  	Welcome<br/><input id="text" type="text"/>
	<button onclick="send()">发送消息</button>
	<hr/>
	<button onclick="closeWebSocket()">关闭WebSocket连接</button>
	<hr/>
	<div id="message"></div>
</body>
<script type="text/javascript">
	var websocket=null;
	if('WebSocket' in window){
		websocket=new WebSocket("ws://localhost/upsky/websocket");
	}else{
		alert('当前浏览器不支持');
	}
	
	websocket.onerror=function(){
		setMessagerInnerHTML("WebSocket连接发生错误");
	};
	
	websocket.onopen=function(){
		setMessagerInnerHTML("WebSocket连接成功");
	}
	
	websocket.onmessage=function(event){
		setMessagerInnerHTML(event.data);
	}
	
	websocket.onclose=function(){
		setMessagerInnerHTML("WebSocket连接关闭");
	}
	
	window.onbeforeunload=function(){
		closeWebSocket();
	}
	
	function setMessagerInnerHTML(innerHTML){
		document.getElementById('message').innerHTML+=innerHTML+'<br/>';
	}
	
	function closeWebSocket(){
		websocket.close();
	}
	
	function send(){
		var message=document.getElementById('text').value;
		websocket.send(message);
	}

</script>
</html>