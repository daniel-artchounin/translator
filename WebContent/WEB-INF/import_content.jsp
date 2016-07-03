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
			<jsp:param name="importContent" value="importContent" />
		</jsp:include>
		<div class="container-fluid">
			<form method="post" enctype="multipart/form-data" action="<c:url value="/import_content"/>">
				<div class="row">
					<div class="col-md-6 col-md-offset-3" >
						<div class="panel panel-default">
							<div class="panel-heading">
								Import de contenu
							</div>
							<div class="panel-body">
								<!-- Here, we display the form's elements (input and select) -->
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="contentName">Nom du contenu :</label>
									<input type="text" id="contentName" class="form-control" placeholder="Nom du contenu" name="contentName" autofocus required/>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="languageId">Langue :</label>
									<select name="languageId" class="form-control" id ="languageId" required>
										<c:if test="${ !empty languages }">
											<c:forEach var="language" items="${ languages }" varStatus="status">
												<option value="<c:out value="${ language.id }"/>"> ${ language.language } </option>
											</c:forEach>
										</c:if>
									</select>
								</div>
								<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
									<label class="control-label" for="file">Contenu à importer :</label>
									<input type="file" id="file" class="form-control" placeholder="Contenu à importer" name="file" required/>
								</div>
								
								<!-- Here, we display the submit button -->
								<input type="submit" class="btn btn-default" value="Importer"/>

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
	    </div>
	    <!-- Here, we include the footer -->
	    <%@ include file="/WEB-INF/footer.jsp" %>	    
	</body>
</html>