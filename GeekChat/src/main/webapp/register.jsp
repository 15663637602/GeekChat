<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico" >
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<link href="css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.login.css" rel="stylesheet" type="text/css" />
<title>GeekChat</title>
<meta name="keywords" content="my12306">
<meta name="description" content="my12306">
<script type="text/javascript">
var req = false;
var name = false;
var password = false;
var mail = false;
function createRequest(){  		
	if (window.XMLHttpRequest) {
     req = new XMLHttpRequest();
 }else if (window.ActiveXObject) {
     req = new ActiveXObject("Microsoft.XMLHTTP");
 }
 if(!req){
 	alert("Error Initializing XMLHttpRequest!");
 }
}
function name_check(){
	var username = document.getElementById("username").value;
	createRequest();
	var t = new Date();
	req.open("POST","testname.do?time="+t.getTime(),true); 
	req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	req.onreadystatechange = updatePagedd;
	req.send("username=" + username );
}
function updatePagedd(){
	if(req.readyState==4){
		if(req.status==200){
			var res = req.responseText;
			 if(res=="You can use this name"){
				name = true;
			}else{
				name = false;
			} 
			document.getElementById("error").innerHTML = res;	
		}
	}
}
function check_email(){
	var email = document.getElementById("email").value;
	var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/; 
	if(reg.test(email)){
		createRequest();
		var t = new Date();
		req.open("POST","testemail.do?time="+t.getTime(),true); 
		req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		req.onreadystatechange = updatePage2;
		req.send("email=" + email); 
	}else{
	mail = false;
	document.getElementById("error").innerHTML ="email format error";
	}
	
	}	
function updatePage2(){
	if(req.readyState==4){
		if(req.status==200){
			var res = req.responseText;
			if(res=="Email already used"){
				mail = false;
			}else{
				mail = true;
			}
			document.getElementById("error").innerHTML = res;	
		}
	}
}
function check_pass(){
	var pass_1 = document.getElementById("password").value.length;
	if(pass_1<6){
		document.getElementById("error").innerHTML ="Password length should be 6-16";
		password = false;
	}else if(pass_1>15){
		document.getElementById("error").innerHTML ="Password length should be 6-16";
		password = false;
	}else{
		document.getElementById("error").innerHTML ="You can use this password";
		password = true;
	}
	}
	function cancel() {
		if (name && password && mail) {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form action="insertUser.do" method="post" onsubmit="return cancel()">
    
      <div class="formRow user">
        <input id="username" name="username" type="text" placeholder="username" class="input_text input-big" autocomplete="off" onkeyup="name_check()">
      </div>
      <div class="formRow password">
        <input id="password" name="password" type="password" placeholder="password" class="input_text input-big" onkeyup="check_pass()">
      </div>
      <div class="formRow input_text">
        <input id="email" name="email" placeholder="email" class="input_text input-big" onkeyup="check_email()">
      </div>
      <div class="formRow">
        <input  type="submit" class="btn radius btn-success btn-big" value="Submit">
        <span style="margin-left: 100px " id="error"></span>
      </div>
      
    </form>
  </div>
</div>
<div class="footer">Geek Chat&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yuqi&nbsp;Li&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yongjin&nbsp;Chen
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pengyu&nbsp;Chen</div>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/H-ui.js"></script>

</body>
</html>
