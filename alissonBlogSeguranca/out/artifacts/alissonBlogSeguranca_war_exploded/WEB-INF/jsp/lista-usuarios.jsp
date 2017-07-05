<!---
<%@ include file="../../import/contentType.jsp" %>
<html>
<head>
    <title>Lista de Usuários</title>
    <%@ include file="../../import/head.jsp" %>
</head>
<body>
<c:forEach items="${usuarios}" var="u">
    ${u.nome} / ${u.login} / ${u.senha} <br/>
    <ul>
        <c:forEach items="${u.logs}" var="l">
            <li>alterou ${l.idObjeto}@${l.classe} em <fmt:formatDate value="${l.dataHora}"
                                                                     pattern="dd/MM/yyyy 'às' HH:mm"/></li>
        </c:forEach>
    </ul>
</c:forEach>
</body>
</html>

-->