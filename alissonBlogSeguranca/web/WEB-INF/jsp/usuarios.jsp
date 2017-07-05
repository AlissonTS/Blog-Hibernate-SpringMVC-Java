<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Gerenciar Usuários</title>
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
				<a class="btn btn-default" href="paginaAdm.html" role="button">Voltar</a><br><br>
				<a style="margin-bottom: 2%;" class="btn btn-default" href="login.html" role="button">Voltar à Página Inicial</a><br>
				<c:if test="${not empty msg}">
					<div class="alert alert-${tipo}">
						<strong><c:out value="${msg}" escapeXml="true"/>!</strong><br>
					</div>
				</c:if>
				<h3 class="text-center">Cadastrar Usuário</h3><br>
				<form:form action="cadastro-usuario.html" method="post" commandName="usuarios">
					<label for="nomeUsuario" class="col-md-3 col-xs-4 col-form-label">Nome: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:input class="form-control" id="nomeUsuario" name="nomeUsuario" placeholder="Digite o nome do usuário" required="true" path="nomeUsuario" />
					</div>
					<label for="senhaUsuario" class="col-md-3 col-xs-4 col-form-label">Senha: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:password class="form-control" id="senhaUsuario" name="senhaUsuario" placeholder="Digite a senha do usuário" required="true" path="senhaUsuario"/>
					</div>
					<label for="loginUsuario" class="col-md-3 col-xs-4 col-form-label">Login de Acesso: </label>
					<div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
						<form:input class="form-control" id="loginUsuario" name="loginUsuario" placeholder="Digite o login do usuário" required="true" path="loginUsuario"/>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-success btn-lg" value="Enviar" name="form">Cadastrar Usuário</button>
					</div>
				</form:form>
			</div>
		</div>
		<hr align="center" width="90%" size="1" color=blue>
		<div class="row">
			<div class="col-lg-offset-3 col-lg-6">
				<c:if test="${not empty msgUsuario}">
					<div class="alert alert-${tipo}">
						<strong><c:out value="${msgUsuario}" escapeXml="true"/>!</strong><br>
					</div>
				</c:if>
				<h4 class="text-center">Usuários Cadastrados:</h4>

				<table class="table table-bordered">
					<thead>
					<tr>
						<th>Nome do Usuário</th>
						<th>Login</th>
						<th>Ativado</th>
						<th>Ação</th>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${usuariosCadastrados}" var="u">
						<c:if test="${u.tipoUsuario!=1}">
							<tr>
								<td><c:out value=" ${u.nomeUsuario}" escapeXml="true"/></td>
								<td><c:out value=" ${u.loginUsuario}" escapeXml="true"/></td>
								<c:if test="${u.ativo==true}">
								<td>Ativado</td>
								<td class="text-center"><form action="ativaUsuario.html" method="POST"><button type="submit" value="${u.idUsuario}" name="id" class="btn btn-info">Desativar</button></form></td>
							</c:if>
								<c:if test="${u.ativo!=true}">
									<td>Desativado</td>
									<td class="text-center"><form action="ativaUsuario.html" method="POST"><button type="submit" value="${u.idUsuario}" name="id" class="btn btn-info">Ativar</button></form></td>
								</c:if>
							</tr>
						</c:if>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
