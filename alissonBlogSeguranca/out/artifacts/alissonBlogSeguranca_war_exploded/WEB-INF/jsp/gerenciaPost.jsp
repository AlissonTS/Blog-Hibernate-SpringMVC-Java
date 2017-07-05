<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Gerenciar Postagens</title>
		<%@ include file="../../import/head.jsp" %>
	</head>
	<body>
	<div class="row">
		<div class="col-lg-12">
			<h1 class="text-center">Blog - Trabalho</h1>
		</div>
	</div>
	<div class="row" style="margin-top: 2%;">
		<div class="col-lg-offset-4 col-lg-4">
			<c:if test="${not empty usuario}">
				<c:if test="${usuario.tipoUsuario==1}">
					<a class="btn btn-default" href="paginaAdm.html" role="button">Voltar</a><br><br>
				</c:if>
				<c:if test="${usuario.tipoUsuario==2}">
					<a class="btn btn-default" href="paginaUser.html" role="button">Voltar</a><br><br>
				</c:if>
				<a class="btn btn-default" href="login.html" role="button">Página Inicial</a><br>
			</c:if>
			<div class="text-center">
				<h3>Gerenciar Postagens:</h3>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="meusPosts.html" role="button" value="Minhas Postagens">Minhas Postagens</a><br><br>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="todosPosts.html" role="button" value="Todas as Postagens do Blog">Todas as Postagens do Blog</a><br><br>
				<a class="btn btn-default" href="logout.html" role="button" value="Logout"><span class="glyphicon glyphicon-log-out"></span> Sair do Sistema</a>
			</div>
		</div>
	</div>
	</body>
</html>