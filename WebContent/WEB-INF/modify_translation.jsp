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
			<form method="post" action="<c:url value="/translation_management"/>">
				<div class="row">
					<div class="col-md-6 col-md-offset-3" >
						<div class="panel panel-default">
							<div class="panel-heading">
								Traduction de ${ deactivatedTranslation.name }
							</div>
							<div class="panel-body">
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
								<c:if test="${ !empty deactivatedTranslation.parts && !empty activatedTranslation.parts }">		
								<div class="container-fluid">
								<div class="row">
									<!-- Possibility to choose another activated translation -->
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
									<!-- The language the content should be translated in -->
									<div class="col-lg-6">
										<label class="control-label" for="toTranslateIn">A traduire en :</label>
										<input type="text" id="toTranslateIn" name="toTranslateIn" class="form-control" placeholder="Nom de la langue" value ="${ languageName }" disabled />
									</div>
								</div>	
								<!-- Here, we display the two translations (the activated one and the not activated one) -->
								<c:forEach var="deactivatedContentPart" items="${ deactivatedTranslation.parts }" varStatus="status">
									<div class="form-group <c:if test="${ !empty errorMessage }"><c:out value="has-error" /></c:if>">
										<div class="row">
										<label class="control-label" for="deactivated_language_part_content_${ status.count }" >${status.count}<br/> ${ deactivatedContentPart.beginning } --> ${ deactivatedContentPart.end } </label>
										</div>
										<div class="row">
										<div class="col-lg-6">
											<textarea class="form-control" rows="5" id="activated_language_${ status.index }" disabled>${ activatedTranslation.parts[status.index].partContent }</textarea>
										</div>
										<div class="col-lg-6">
											<textarea class="form-control" rows="5" id="deactivated_language_part_content_${ status.count }" name="deactivated_language_part_content_${ status.count }">${ deactivatedContentPart.partContent }</textarea>
											<input type="hidden" name="deactivated_language_part_id_${ status.count }" value="${ deactivatedContentPart.id }" />
										</div>	
										</div>									
									</div>
								</c:forEach>
								</div>
								</c:if>
								
								<!-- Here, we display the update button -->
								<input type="submit" class="btn btn-default" value="Sauver"/>
								
								<input type="hidden" name="number_of_deactivated_language_parts" value="${ fn:length(deactivatedTranslation.parts ) }" />
								<input type="hidden" name="language_id" value ="${ deactivatedLanguage }" />
								<input type="hidden" id="content_id" name="content_id" value="${ contentId }" />
								<input type="hidden" name="language_name" value ="${ languageName }" />
								<input type="hidden" name="action" value="update_translation" />		
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