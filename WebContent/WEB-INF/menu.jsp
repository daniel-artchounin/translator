<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>                        
			</button>
			<a class="navbar-brand" href="<c:url value="/import_content"/>"><span class="glyphicon glyphicon-home"></span> Editeur de sous-titres</a>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">			
			<ul class="nav navbar-nav">
				<li <c:if test="${ !empty param.importContent }">class="active"</c:if> ><a href="<c:url value="/import_content"/>" title="Vers l'import d'un contenu">Import d'un contenu</a></li>
				<li <c:if test="${ !empty param.contentsManagement }">class="active"</c:if> ><a href="<c:url value="/contents_management"/>" title="Vers la gestion des contenus">Gestion des contenus</a></li>
			</ul>
		</div>
	</div>
</nav>