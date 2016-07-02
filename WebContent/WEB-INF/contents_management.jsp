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
		<c:set var="contentsManagementUrl" value="/contents_management" scope="request" />
		<c:set var="exportTranslationUrl" value="/export_translation" scope="request" />
		<c:set var="translationManagementUrl" value="/translation_management" scope="request" />
		<c:set var="importContentUrl" value="/import_content" scope="request" />
		
		<!-- Here, we include the header -->
		<%@ include file="/WEB-INF/header.jsp" %>   
		<jsp:include page="/WEB-INF/menu.jsp" >
			<jsp:param name="contentsManagement" value="contentsManagement" />
		</jsp:include>
		<div class="container-fluid">
	        <section>
	        	<h1>Contenus</h1>	        	
	        	<c:if test="${ !empty errorMessage || !empty successMessage }">	        	
					<div class="row">
					<div class="col-md-6 col-md-offset-3">				
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
				</c:if>				
				<c:forEach var="content" items="${ contents }" varStatus="status">
					<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<div class="panel-group">
							<div class="panel panel-default">
							
								<div class="panel-heading">
								<h4 class="panel-title">								
									<!-- Content's name  -->
									<a data-toggle="collapse" href="#<c:out value="${ status.index }" />"><c:out value="${ content.name }" /></a>									
									<!-- Possibility to delete a content -->
									<c:url value="${ contentsManagementUrl }" var="url1">
										<c:param name="action" value="delete_content" />
										<c:param name="content_id" value="${ content.id }" />
									</c:url>
									<a href="${ url1 }" class="btn btn-danger" role="button">Supprimer</a>
									
								</h4>
								</div>
								
								<div id="${status.index}" class="panel-collapse collapse">
									<ul class="list-group">										 
										<c:forEach var="language" items="${ content.languages }">
											<li class="list-group-item 
												<c:choose>
													<c:when test="${ language.exportable == true }">list-group-item-success</c:when>
													<c:otherwise>list-group-item-danger</c:otherwise>
												</c:choose>
											" >
												<!-- Possibility to consult or modify a translation -->
												<c:url value="${ translationManagementUrl }" var="url2">
													<c:param name="action" value="consult_modify_translation" />
													<c:param name="content_id" value="${ content.id }" />
													<c:param name="language_id" value="${ language.id }" />
												</c:url>												
												<a href="${ url2 }" 
												<c:choose>
													<c:when test="${ language.exportable == true }">title="Cliquez pour me consulter"</c:when>
													<c:otherwise>title="Cliquez pour me modifier"</c:otherwise>
												</c:choose>												
												><c:out value="${ language.language }" /></a>
												<!-- Possibility to export a translation -->
												<c:if test="${ language.exportable == true }">
													<c:url value="${ exportTranslationUrl }" var="url3">
														<c:param name="action" value="export_translation" />
														<c:param name="content_id" value="${ content.id }" />
														<c:param name="language_id" value="${ language.id }" />
													</c:url>
													<a href="${ url3 }" class="btn btn-info" role="button">Exporter</a>
													<!-- Possibility to desactivate (disable export) a translation -->
													<c:if test="${ content.hasAtLeastTwoExportableTranslations == true }">
														<c:url value="${ translationManagementUrl }" var="url4">
															<c:param name="action" value="desactivate_translation" />
															<c:param name="content_id" value="${ content.id }" />
															<c:param name="language_id" value="${ language.id }" />
														</c:url>
														<a href="${ url4 }" class="btn btn-danger" role="button">Désactiver</a>
													</c:if>
													
												</c:if>
												
												<c:if test="${ language.exportable == false }">
													<c:url value="${ translationManagementUrl }" var="url5">
														<c:param name="action" value="activate_translation" />
														<c:param name="content_id" value="${ content.id }" />
														<c:param name="language_id" value="${ language.id }" />
													</c:url>
													<a href="${ url5 }" class="btn btn-success" role="button">Activer</a>
												</c:if>
											</li>											
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					</div>
				</c:forEach>
				<c:url value="${ importContentUrl }" var="url6" />
				<h4>
					<a href="${ url6 }" title="Cliquez si vous souhaitez en ajouter un">
						Ajouter un contenu
					</a>
				</h4>
	    	</section>
	    </div>
	    <!-- Here, we include the footer -->
	    <%@ include file="/WEB-INF/footer.jsp" %>
	</body>
</html>