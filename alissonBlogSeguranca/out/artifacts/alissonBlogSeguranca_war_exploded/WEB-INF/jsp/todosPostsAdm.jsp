<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Todas as Postagens</title>
		<%@ include file="../../import/head.jsp" %>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<h1 class="text-center">Blog - Trabalho</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="row">
					<div class="col-lg-offset-1 col-lg-10">
						<c:if test="${usuario.tipoUsuario==1}">
							<a class="btn btn-default" href="paginaAdm.html" role="button">Página do Administrador</a><br><br>
						</c:if>
						<a style="margin-bottom: 2%;" class="btn btn-default" href="login.html" role="button">Voltar à Página Inicial</a>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-offset-1 col-lg-10">
						<c:if test="${not empty msgPost}">
							<div class="alert alert-${tipo}">
								<strong><c:out value="${msgPost}" escapeXml="true"/>!</strong><br>
							</div>
						</c:if>
						<h3 class="text-center">Todos as Postagens</h3>
						<table class="table table-bordered">
							<thead>
							<tr>
								<th>Título</th>
								<th>Texto</th>
								<th>Autor da Postagem</th>
								<th>Data da Postagem</th>
								<th>Ação1</th>
								<th>Ação2</th>
								<th>Ação3</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${posts}" var="p">
								<tr>
									<th><c:out value="${p.tituloPost}" escapeXml="true"/></th>
									<td><c:out value="${fn:substring(p.textoPost, 0,199)}" escapeXml="true"/>...</td>
									<td><c:out value="${p.usuario.nomeUsuario}" escapeXml="true"/></td>
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
			</div>
		</div>
	</body>
</html>
