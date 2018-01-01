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
 
				
    		</div>
  			<div class="col-md-9">
  			
  				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title">Public Chatroom</h3>
				  </div>
				  <div class="panel-body">
				  	<div class="well" id="log-container-public" style="height:300px;overflow-y:scroll">
				  	</div>
				  	
				    <input type="text" class="form-control col-md-12"  id="msg" /><br>
				    <button id="broadcast" type="button" class="btn btn-primary">Send</button>
				  </div>
				</div>
			</div>
			
			<div class="col-md-3">
    			<div class="panel panel-primary" id="online">
				  <div class="panel-heading">
				    <h3 class="panel-title">Other Online Users</h3>
				  </div>
				  <div class="panel-body">
				    <div class="list-group" id="users" style="height:300px;overflow-y:scroll">
					</div>
				  </div>
				</div>
    		</div>
			
			<div class="col-md-9">
  				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" id="talktitle"></h3>
				  </div>
				  <div class="panel-body">
				    <div class="well" id="log-container-private" style="height:300px;overflow-y:scroll">
				    
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
       	 
       	 	if(data.from==-1){//public message
       	 		if(data.fromName!="${sessionScope.username}"){
       	 			// Receive real-time messages from other users
            		$("#log-container-public").append("<div class='bg-info'><label class='text-danger'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='text-success'>"+data.text+"</div></div><br>");
            	} else{
            		// Receive real-time messages from itself
            		$("#log-container-public").append("<div class='bg-success'><label class='text-info'>Me&nbsp;"+new Date().toString()+"</label><div class='text-info'>"+data.text+"</div></div><br>");
            	}
            // Scrollbar rolling to the lowest part
            publicscrollToBottom();
            }else if(data.from>0){//private message
            //receive real-time messages from user
            $("#log-container-private").append("<div class='bg-info'><label class='text-danger'>"+data.fromName+"&nbsp;->&nbsp;Me&nbsp;"+data.date+"</label><div class='text-success'>"+data.text+"</div></div><br>");
            // Scrollbar rolling to the lowest part
            privatescrollToBottom();
            }else if(data.from==0){//Online message
            	if(data.text!="${sessionScope.username}")
            	{	
            		$("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data.text+'</a>');
            		//alert(data.text+" is online");
            		//receive online messages from user
            		$("#log-container-public").append("<div class='bg-info'><label class='text-danger'>"+data.text+" is online" + "</label></div><br>");
           			// Scrollbar rolling to the lowest part
            		publicscrollToBottom();
            	}
            }else if(data.from==-2){//Offline message
            	if(data.text!="${sessionScope.username}")
            	{	
            		$("#users > a").remove(":contains('"+data.text+"')");
            		//alert(data.text+" is offline");
            		//receive offline messages from user
            		$("#log-container-public").append("<div class='bg-info'><label class='text-danger'>"+data.text+" is offline" + "</label></div><br>");
           			// Scrollbar rolling to the lowest part
            		publicscrollToBottom();
            	}
            }
        };
        $.get("onlineusers.do",function(data){
    		for(var i=0;i<data.length;i++)
    			$("#users").append('<a href="#" onclick="talk(this)" class="list-group-item">'+data[i]+'</a>');
    	});
        
        $("#broadcast").click(function(){
        	$.post("broadcast.do",{"text":$("#msg").val(),"username":"${sessionScope.username}"});
        	$("#msg").val("");
        });
        
        $("#send").click(function(){
        	if($("#talktitle").text()==""){
        		$("#log-container-private").append("<div class='bg-success'><label class='text-info'>Please choose a user.</label></div><br>");
        	}
        	else{
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
					$("#log-container-private").append("<div class='bg-success'><label class='text-info'>Me&nbsp;->&nbsp;"+$("body").data("to")+new Date().toString()+"</label><div class='text-info'>"+v+"</div></div><br>");
					privatescrollToBottom();
					$("#myinfo").val("");
					}
        		});
        	}
        	
        });
        
    });
   
   function talk(a){
   	$("#talktitle").text("Chat with "+a.innerHTML);
   	$("body").data("to",a.innerHTML);
   }
   function publicscrollToBottom(){
		var div = document.getElementById('log-container-public');
		div.scrollTop = div.scrollHeight;
	}
	function privatescrollToBottom(){
		var div = document.getElementById('log-container-private');
		div.scrollTop = div.scrollHeight;
	}
</script>    
    
  </body>
</html>
