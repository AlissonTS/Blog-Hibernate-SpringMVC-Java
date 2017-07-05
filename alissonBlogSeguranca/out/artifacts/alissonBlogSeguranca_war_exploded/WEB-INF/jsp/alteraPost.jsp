<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
	<title>Gerenciar Postagens</title>
	<%@ include file="../../import/head.jsp" %>
</head>
<body>
<div class="row">
	<div class="col-lg-12">
		<h1 class="text-center">Blog - Trabalho</h1><br>
	</div>
</div>
<div class="row">
	<div class="col-lg-offset-3 col-lg-6">
		<c:if test="${not empty usuario}">
			<c:if test="${usuario.tipoUsuario==1}">
				<a class="btn btn-default" href="paginaAdm.html" role="button">Voltar</a><br><br>
			</c:if>
			<c:if test="${usuario.tipoUsuario==2}">
				<a class="btn btn-default" href="paginaUser.html" role="button">Voltar</a><br><br>
			</c:if>
			<a class="btn btn-default" href="login.html" role="button">Página Inicial</a><br>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="alert alert-${tipo}" style="margin-top: 2%;">
				<strong>${msg}!</strong><br>
			</div>
		</c:if>
		<h3 class="text-center">Alterar Postagem</h3><br>
		<form:form action="cadastro-post.html" method="post" commandName="posts">
			<form:hidden path="idPost"/>
			<label for="tituloPost" class="col-md-3 col-xs-4 col-form-label">Título da Postagem: </label>
			<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
				<form:input class="form-control" id="tituloPost" name="tituloPost" placeholder="Digite o título da postagem" required="true" path="tituloPost" />
			</div>
			<label for="textoPost" class="col-md-3 col-xs-4 col-form-label">Texto: </label>
			<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
				<form:textarea class="form-control" id="textoPost" name="textoPost" placeholder="Digite o texto da postagem" required="true" path="textoPost"/>
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-default btn-lg" value="Enviar" name="form">Alterar Postagem</button>
			</div>
		</form:form>
	</div>
</div>
