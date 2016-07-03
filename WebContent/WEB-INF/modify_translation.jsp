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
			<c:if test="${ !empty deactivatedTranslation && !empty activatedTranslation }">			
			<form method="post" enctype="multipart/form-data" >
				<div class="row">
					<div class="col-md-6 col-md-offset-3" >
						<div class="panel panel-default">
							<div class="panel-heading">
								Traduction de ${ deactivatedTranslation.name }
							</div>
							<div class="panel-body">								
								<c:if test="${ !empty deactivatedTranslation.parts && !empty activatedTranslation.parts }">		
								<div class="container-fluid">
								<div class="row">
									<div class="col-lg-6">	
									<label class="control-label" for="languageId">Déjà traduit en :</label>		
									<select name="languageId" class="form-control" id ="languageId" required>										
										<c:if test="${ !empty activatedLanguages }">
											<c:forEach var="language" items="${ activatedLanguages }" varStatus="status">
												<option value="<c:out value="${ language.id }"/>" 
													<c:if test="${ chosenLanguage.id == language.id }">
														selected
													</c:if>
												> 
													${ language.language } 
												</option>
											</c:forEach>
										</c:if>
									</select>
									</div>
									<div class="col-lg-6">
											<label class="control-label" for="toTranslateIn">A traduire en :</label>
											<input type="text" id="toTranslateIn" class="form-control" placeholder="Nom de la langue" value ="${ deactivatedLanguage }" disabled />
									</div>
								</div>	
								<c:forEach var="deactivatedContentPart" items="${ deactivatedTranslation.parts }" varStatus="status">
									<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
										<div class="row">
										<label class="control-label" for="${ deactivatedContentPart.id }_${ deactivatedLanguage }" >${status.count}<br/> ${ deactivatedContentPart.beginning },000 --> ${ deactivatedContentPart.end },000 </label>
										</div>
										<div class="row">
										<div class="col-lg-6">
											<textarea class="form-control" rows="5" id="activatedlanguage_${ status.index }" disabled>${ activatedTranslation.parts[status.index].partContent }</textarea>
										</div>
										<div class="col-lg-6">
											<textarea class="form-control" rows="5" id="${ deactivatedContentPart.id }_${ deactivatedLanguage }" >${ deactivatedContentPart.partContent }</textarea>
										</div>	
										</div>									
									</div>
								</c:forEach>
								</div>
								</c:if>
								
								<!-- Here, we display the update button -->
								<input type="submit" class="btn btn-default" value="Update"/>
								<input type="hidden" id="contentId" value="${ contentId }" />
							
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
	    <script src="<c:url value="/js/script.js"/>"></script>
	</body>