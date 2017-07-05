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
					<a style="margin-bottom: 2%;" class="btn btn-default" href="login.html" role="button">Voltar à Página Inicial</a>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="alert alert-${tipo}" style="margin-top: 2%;">
						<strong><c:out value="${msg}" escapeXml="true"/>!</strong><br>
					</div>
				</c:if>
				<h3 class="text-center">Cadastrar Postagem</h3><br>
				<form:form action="cadastro-post.html" method="post" commandName="posts">
					<label for="tituloPost" class="col-md-3 col-xs-4 col-form-label">Título da Postagem: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:input class="form-control" id="tituloPost" name="tituloPost" placeholder="Digite o título da postagem" required="true" path="tituloPost" />
					</div>
					<label for="textoPost" class="col-md-3 col-xs-4 col-form-label">Texto: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:textarea class="form-control" id="textoPost" name="textoPost" placeholder="Digite o texto da postagem" required="true" path="textoPost"/>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-default btn-lg" value="Enviar" name="form">Cadastrar Postagem</button>
					</div>
				</form:form>
			</div>
		</div>
		<hr align="center" width="90%" size="1" color=blue>
		<div class="row">
			<div class="col-lg-offset-2 col-lg-8">
				<c:if test="${not empty msgPost}">
					<div class="alert alert-${tipo}">
						<strong><c:out value="${msgPost}" escapeXml="true"/>!</strong><br>
					</div>
				</c:if>
				<h4 class="text-center">Postagens do Usuário:</h4>

				<table class="table table-bordered">
					<thead>
					<tr>
						<th>Título</th>
						<th>Texto</th>
						<th>Data da Postagem</th>
						<th>Ação1</th>
						<th>Ação2</th>
						<th>Ação3</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${postsUsuario}" var="p">
						<tr>
							<th><c:out value="${p.tituloPost}" escapeXml="true"/></th>
							<td><c:out value="${fn:substring(p.textoPost, 0,199)}" escapeXml="true"/>...</td>
							<td><c:out value="${p.dataPost}" escapeXml="true"/></td>
							<td class="text-center"><form action="verPost.html" method="POST"><button type="submit" value="${p.idPost}" name="id" class="btn btn-default">Postagem Completa</button></form></td>
							<td class="text-center"><form action="alterarPost.html" method="POST"><button type="submit" value="${p.idPost}" name="id" class="btn btn-primary">Alterar Postagem</button></form></td>
							<td class="text-center"><form action="deletaPost.html" method="POST"><button type="submit" value="${p.idPost}" name="id" class="btn btn-danger">Deletar Postagem</button></form></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>


