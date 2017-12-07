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
</head>
<body>
<input type="hidden" id="TenantId" name="TenantId" value="" />
<div class="loginWraper">
  <div id="loginform" class="loginBox">
    <form action="mongovalidate.do" method="post">
      <div class="formRow user">
      <div>${hint}</div>
        <input id="username" name="username" type="text" placeholder="username" class="input_text input-big" autocomplete="off">
      </div>
      <div class="formRow password">
        <input id="password" name="password" type="password" placeholder="password" class="input_text input-big">
      </div>
      <div class="formRow">
        <input  type="submit" class="btn radius btn-success btn-big" value="Login">
        <input  type="button" class="btn radius btn-success btn-big" value="Register" style="margin-left: 200px " onclick="javascript:window.location.href='register.jsp' ">
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
