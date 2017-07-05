<%@ include file="../../import/contentType.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Página Inicial</title>
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
                <div class="row">
                    <div class="col-lg-offset-3 col-lg-6">
                        <h2 class="text-center">Acesso ao Sistema</h2><br>
                        <c:if test="${not empty msg}">
                            <div class="alert alert-${tipo}">
                                <strong><c:out value="${msg}" escapeXml="true"/>!</strong><br>
                            </div>
                        </c:if>
                        <c:if test="${not empty usuario}">
                            <c:if test="${usuario.tipoUsuario==1}">
                                <div class="text-center">
                                    <a class="btn btn-default" href="paginaAdm.html" role="button">Entrar no Sistema <span class="glyphicon glyphicon-log-in"></span></a><br><br><br>
                                </div>
                            </c:if>
                            <c:if test="${usuario.tipoUsuario==2}">
                                <div class="text-center">
                                    <a class="btn btn-default" href="paginaUser.html" role="button">Entrar no Sistema <span class="glyphicon glyphicon-log-in"></span></a><br><br>
                                </div>
                            </c:if>
                            <div class="text-center">
                                <a class="btn btn-default" href="logout.html" role="button" value="Logout"><span class="glyphicon glyphicon-log-out"></span> Sair do Sistema</a>
                            </div>
                        </c:if>
                        <c:if test="${empty usuario}">
                            <form action="login.html" method="post" style="font-size: 16px;">
                                <label for="login" class="col-md-3 col-xs-4 col-form-label">Login: </label>
                                <div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
                                    <input class="form-control" placeholder="Digite seu login" type="text" type="text" name="login" id="login" required>
                                </div>
                                <label for="senha" class="col-md-3 col-xs-4 col-form-label">Senha: </label>
                                <div class="col-md-8 col-xs-8" style="margin-bottom: 2%;">
                                    <input class="form-control" placeholder="Digite sua senha" id="senha" type="password" type="password" name="senha" required>
                                </div>
                                <br>
                                <div class="text-center">
                                    <button type="submit" class="btn btn-default btn-lg" value="Enviar" name="form">Entrar no Sistema <span class="glyphicon glyphicon-log-in"></span></button>
                                </div>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <hr align="center" width="90%" size="1" color=blue>
        <div class="row">
            <div class="col-lg-offset-1 col-lg-10">
                <h2 class="text-center">Postagens do Blog</h2>
                <h3 class="text-center" style="margin-bottom: 3%;">Últimas 10 postagens</h3>
                <a style="margin-bottom: 3%;" class="btn btn-default" href="todosPosts.html" role="button" value="Ver Todos">Ver todas as postagens</a>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Texto</th>
                            <th>Autor da Postagem</th>
                            <th>Data da Postagem</th>
                            <th>Ação</th>
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
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>