<!DOCTYPE html>

<html>
	<head>
		<title>Login</title>
		<style type="text/css">
			.label {text-align: right}
			.error {color: red}
		</style>

		<script src="../bootstrap/3.1.1/js/jquery-2.1.1.min.js"></script>

		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="../bootstrap/3.1.1/css/bootstrap.min.css">
		<!-- Optional theme -->
		<link rel="stylesheet" href="../bootstrap/3.1.1/css/bootstrap-theme.min.css">

		<!-- Latest compiled and minified JavaScript -->
		<script src="../bootstrap/3.1.1/js/bootstrap.min.js"></script>
	</head>


	<body>
		<div class="panel panel-primary" >
			
			<div class="panel-heading">
				<h3 class="panel-title">Web DsCall</h3>
			</div>
			
			<div class="panel panel-default">
			
				<div class="panel-heading">
					<h3 class="panel-title">Request params</h3>
				</div>

				<div class="panel-body">
				<form class="form-horizontal" role="form" method="post">
				  <div class="form-group">
				    <label class="col-sm-2 control-label">Server</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" placeholder="http://localhost:7001" name="serverName" value="${serverName!""}">
				    </div>
				  </div>
				  <div class="form-group">
				    <label class="col-sm-2 control-label">Context</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" placeholder="/cbs/cbsws" name="serverContext" value="${serverContext!""}">
				    </div>
				  </div>
				  <div class="form-group">
				    <label class="col-sm-2 control-label">Service</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" placeholder="dsRefreshLogConfiguration" name="serviceName" value="${serviceName!""}">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label class="col-sm-2 control-label">Username</label>
				    <div class="col-sm-10">
				      <input type="text" class="form-control" placeholder="username" name="userName" value="${userName!""}">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label for="inputPassword" class="col-sm-2 control-label">Password</label>
				    <div class="col-sm-10">
				      <input type="password" class="form-control" id="inputPassword" name="userPassword" value="${userPassword!""}" placeholder="Password">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    
				    <div class="col-sm-offset-2 col-sm-10">
				      <div class="checkbox">
							<label>
								<input type="checkbox"/>Hash password
							</label>
						</div>
				    </div>
				  </div>

				  <div class="form-group">
				    <label for="inputPassword" class="col-sm-2 control-label">Request</label>
				    <div class="col-sm-10">
						<textarea class="form-control" rows="7" style="font-family:monospace;" name="requestText">${requestText!""}</textarea>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <label class="col-sm-2 control-label"></label>
				    <div class="col-sm-10">
					  <div class="btn-group">
							<button class="btn btn-default" disabled="true">Format</button>
							<button type="reset" class="btn btn-default">Clear</button>
							<button type="submit" class="btn btn-default">Submit</button>
					  </div>
				    </div>
				  </div>				  
				</form>				
				</div>
				
				<!-- RESPONSE -->
				
				<div class="panel-heading">
					<h3 class="panel-title">Response params</h3>
				</div>
				
				<div class="panel-body alert alert-${callStatus!""}">
				  <!--div class="form-group">
  					<div class="alert alert-${callStatus!""}">Status</div>
				  </div-->
				  
				  <div class="form-group">
				    <label for="inputPassword" class="col-sm-2 control-label">Call time (ms)</label>
				    <div class="col-sm-10">
						<label class="col-sm-2 control-label" name="callTimeMs">${callTimeMs!""}</label>
				    </div>
				  </div>
				  
				  <div class="form-group">
						<textarea class="form-control" rows="7" style="font-family:monospace;" readonly="true" name="resultText">${resultText!""}</textarea>
				  </div>
				</div>
				
				<!-- VERSION -->
				
				<div class="panel-heading">
					<h3 class="panel-title">Version info</h3>
				</div>
				
				<div class="panel-body">
				  <div class="form-group">
				    <label class="col-sm-2 control-label">ru.diasoft.fa.platform.lib:util</label>
				    <div class="col-sm-10">
						<label class="col-sm-2 control-label" name="platformUtilsVersion">${platformUtilsVersion!""}</label>
				    </div>
				  </div>
				</div>
			</div>

	</body>

</html>
