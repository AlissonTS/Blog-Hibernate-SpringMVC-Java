<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Página do Administrador</title>
		<%@ include file="../../import/head.jsp" %>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<h1 class="text-center">Blog - Trabalho</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-offset-2 col-lg-8">
				<h4 class="text-center">Seja bem-vindo <c:out value="${usuario.nomeUsuario}" escapeXml="true"/></h4>
			</div>
		</div>
		<div class="row" style="margin-top: 2%;">
			<div class="col-lg-offset-4 col-lg-4" style="text-align: center;">
				<h3>Gerenciamento:</h3>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="GerenciaUsuarios.html" role="button" value="Gerenciar Usuarios">Gerenciar Usuários do Sistema</a><br><br>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="GerenciaPosts.html" role="button" value="Gerenciar Postagens">Gerenciar Postagens</a><br><br>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="login.html" role="button">Voltar à Página Inicial</a><br><br>
				<a class="btn btn-default" href="logout.html" role="button" value="Logout"><span class="glyphicon glyphicon-log-out"></span> Sair do Sistema</a>
			</div>
		</div>
	</body>
</html>