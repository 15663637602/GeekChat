<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Chat room</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style>
body{
	margin-top:5px;
}
</style>
</head>
  <body>
    <div class="container">
    	<div class="row">
    		<div class="col-md-3">
    		<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">Current User</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group">
					 <a href="#" class="list-group-item">Hello，${sessionScope.username}</a>
					 <a href="logout.do" class="list-group-item">Logout</a>
					</div>
				  </div>
				</div>
    			<div class="panel panel-primary" id="online">
				  <div class="panel-heading">
				    <h3 class="panel-title">Other Online Users</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group" id="users">
					</div>
				  </div>
				</div>
				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">Broadcast</h3>
				  </div>
				  <div class="panel-body">
				    <input type="text" class="form-control"  id="msg" /><br>
				    <button id="broadcast" type="button" class="btn btn-primary">Send</button>
				  </div>
				</div>
    		</div>
  			<div class="col-md-9">
  				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" id="talktitle"></h3>
				  </div>
				  <div class="panel-body">
				    <div class="well" id="log-container" style="height:400px;overflow-y:scroll">
				    
				    </div>
				    	<input type="text" id="myinfo" class="form-control col-md-12" /> <br>
				    	<button id="send" type="button" class="btn btn-primary">Send</button>
				    </div>
				</div>
  			</div>
    	</div>
    </div> 
<script>
    $(document).ready(function() {
        // websocket url
        var websocket;
        if ('WebSocket' in window) {
			websocket = new WebSocket("ws://localhost:8080/GeekChat/ws.do?uid="+${sessionScope.uid});
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://localhost:8080/GeekChat/ws.do"+${sessionScope.uid});
		} else {
			websocket = new SockJS("http://localhost:8080/GeekChat/ws/sockjs.do"+${sessionScope.uid});
		}
		
        websocket.onmessage = function(event) {
       	 var data=JSON.parse(event.data);
       	 
       	 	if(data.from>0||data.from==-1){//message
            // Receive real-time messages from the server side and add to the HTML page
            $("#log-container").append("<div class='bg-info'><label class='text-danger'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='text-success'>"+data.text+"</div></div><br>");
            // Scrollbar rolling to the lowest part
            scrollToBottom();
            }else if(data.from==0){//Online message
            	if(data.text!="${sessionScope.username}")
            	{	
            		$("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data.text+'</a>');
            		alert(data.text+" is online");
            	}
            }else if(data.from==-2){//Offline message
            	if(data.text!="${sessionScope.username}")
            	{	
            		$("#users > a").remove(":contains('"+data.text+"')");
            		alert(data.text+" is offline");
            	}
            }
        };
        $.get("onlineusers.do",function(data){
    		for(var i=0;i<data.length;i++)
    			$("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data[i]+'</a>');
    	});
        
        $("#broadcast").click(function(){
        	$.post("broadcast.do",{"text":$("#msg").val()});
        });
        
        $("#send").click(function(){
        	$.post("getuid.do",{"username":$("body").data("to")},function(d){
        		var v=$("#myinfo").val();
        		
				if(v==""){
					return;
				}else{
					var data={};
					data["from"]="${sessionScope.uid}";
					data["fromName"]="${sessionScope.username}";
					data["to"]=d.u_id;
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#log-container").append("<div class='bg-success'><label class='text-info'>Me&nbsp;"+new Date()+"</label><div class='text-info'>"+v+"</div></div><br>");
					scrollToBottom();
					$("#myinfo").val("");
				}
        	});
        	
        });
        
    });
   
   function talk(a){
   	$("#talktitle").text("Chat with "+a.innerHTML);
   	$("body").data("to",a.innerHTML);
   }
   function scrollToBottom(){
		var div = document.getElementById('log-container');
		div.scrollTop = div.scrollHeight;
	}
</script>    
    
  </body>
</html>
