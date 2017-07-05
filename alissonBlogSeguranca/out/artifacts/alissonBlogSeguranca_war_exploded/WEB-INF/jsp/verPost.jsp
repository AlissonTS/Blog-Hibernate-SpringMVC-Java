<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Postagem</title>
		<%@ include file="../../import/head.jsp" %>
	</head>
	<body>
		<div class="row">
			<div class="col-lg-12">
				<h1 class="text-center">Blog - Trabalho</h1>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<c:if test="${usuario.tipoUsuario==1}">
					<a class="btn btn-default" href="paginaAdm.html" role="button">Página do Administrador</a><br><br>
				</c:if>
				<c:if test="${usuario.tipoUsuario==2}">
					<a class="btn btn-default" href="paginaUser.html" role="button">Página do Usuário</a><br><br>
				</c:if>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="login.html" role="button">Voltar à Página Inicial</a>
				<h3 class="text-center"><c:out value="${post.tituloPost}" escapeXml="true"/></h3>
				<p class="text-center"><c:out value="${post.textoPost}" escapeXml="true"/></p><br>
				<h4 class="text-center">Comentários da Postagem:</h4>

				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Título do Comentário</th>
							<th>Comentário</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${comentariosPost}" var="cp">
							<tr>
								<td><c:out value="${cp.tituloComentario}" escapeXml="true"/></td>
								<td><c:out value="${cp.textoComentario}" escapeXml="true"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<hr align="center" width="90%" size="1" color=blue>
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<c:if test="${not empty msg}">
					<div class="alert alert-success">
						<strong><c:out value="${msg}" escapeXml="true"/>!</strong><br>
					</div>
				</c:if>
				<h4 class="text-center">Insira um Comentário: </h4>
				<br>
				<form:form action="cadastro-comentario.html?idPost=${post.idPost}" method="post" commandName="comentarios">
					<label for="tituloComentario" class="col-md-3 col-xs-4 col-form-label">Título: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:input class="form-control" id="tituloComentario" name="tituloComentario" placeholder="Digite o título de seu comentário" required="true" path="tituloComentario" />
					</div>
					<label for="textoComentario" class="col-md-3 col-xs-4 col-form-label">Comentário: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:textarea class="form-control" id="textoComentario" name="textoComentario" placeholder="Digite seu comentário" required="true" path="textoComentario"/>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-default btn-lg" value="Enviar" name="form">Enviar Comentário</button>
					</div>
				</form:form>
			</div>
		</div>
	</body>
</html>
