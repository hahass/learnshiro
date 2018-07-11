<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/3/8
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>

</head>
<body>


Welcome <shiro:principal></shiro:principal>

<br><br>

<shiro:hasRole name="admin">
    <a href="admin.jsp">admin page</a>
</shiro:hasRole>

<shiro:hasRole name="user">
    <a href="user.jsp">user page</a>
</shiro:hasRole>

登录成功
<a href="shiro/logout">退出</a>

<br>
<br>

<br>
<br>


</body>
</html>
