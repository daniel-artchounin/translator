<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>Editeur de sous-titres</title>
		<script src="<c:url value="/js/jquery-2.2.2.min.js"/>"></script>
		<link href="<c:url value="/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
		<script src="<c:url value="/bootstrap/js/bootstrap.min.js"/>"></script>
		<link href="<c:url value="/css/styles.css"/>" rel="stylesheet">
	</head>
	<body>	
		<!-- Here, we include the header -->
		<%@ include file="/WEB-INF/header.jsp" %>   
		<jsp:include page="/WEB-INF/menu.jsp" >
			<jsp:param name="contentsManagement" value="contentsManagement" />
		</jsp:include>
		<div class="container-fluid">
			<c:if test="${ !empty content }">			
			<form method="post" >
				<div class="row">
					<div class="col-md-6 col-md-offset-3" >
						<div class="panel panel-default">
							<div class="panel-heading">
								Traduction de ${ content.name }
							</div>
							<div class="panel-body">
								<c:if test="${ !empty content.parts }">	
								<c:forEach var="contentPart" items="${ content.parts }" varStatus="status">
									<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
										<label class="control-label" for="${ contentPart.id }" >${status.count}<br/> ${ contentPart.beginning },000 --> ${ contentPart.end },000 </label>
										<textarea class="form-control" rows="5" id="${ contentPart.id }"  disabled >${ contentPart.partContent }</textarea>
									</div>
								</c:forEach>
								</c:if>
								<!-- Here, we display the error message -->
								<c:if test="${ !empty errorMessage }">
							  	    <div class="alert alert-danger" role="alert"> 
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
										<span class="sr-only">Error: </span><c:out value="${ errorMessage }" />
									</div>
								</c:if>
								
								<!-- Here, we display the success message -->
								<c:if test="${ !empty successMessage }">
							  	    <div class="alert alert-success" role="alert"> 
										<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
										<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
										<span class="sr-only">Success: </span><c:out value="${ successMessage }" />
									</div>
								</c:if>				
							</div>
						</div>
					</div>
				</div>
			</form>
			</c:if>
	    </div>
	    <!-- Here, we include the footer -->
	    <%@ include file="/WEB-INF/footer.jsp" %>
	</body>