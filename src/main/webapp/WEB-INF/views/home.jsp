<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mourad
  Date: 7/28/2016
  Time: 12:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>MM-PRESTA | Login Page</title>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://opensource.keycdn.com/fontawesome/4.6.3/font-awesome.min.css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body style="padding-top:50px;">
    <div class="container">
        <jsp:include page="/admin/nav.do"></jsp:include>
        <div class="row" style="text-align:center;">
            <h2>List des utilisateurs</h2>
        </div>

        <div class="row">
            <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1">
                <div class="panel panel-primary">
                    <div class="panel-heading" style="text-align:center;">
                        <h6>Nouveau utilisateur</h6>
                    </div>
                    <div class="panel-body" >
                        <form action="/admin/user/create.do" method="POST">
                            <c:if test="${sessionScope.error!=null}">
                                <div class="alert alert-danger" role="alert">${sessionScope.error}</div>
                                <c:remove var="error" scope="session"/>
                            </c:if>
                            <c:if test="${sessionScope.success!=null}">
                                <div class="alert alert-success" role="alert">${sessionScope.success}</div>
                                <c:remove var="success" scope="session"/>
                            </c:if>

                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon1"><i class="fa fa-user"></i></span>
                                <input type="text" required="1" name="username" class="form-control" placeholder="Nom d'utilisateur" aria-describedby="basic-addon1">
                            </div><br>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon2"><i class="fa fa-lock"></i></span>
                                <input type="password" required="1" name="password" class="form-control" placeholder="Mot de passe" aria-describedby="basic-addon2">
                            </div>
                            <br>
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon3"><i class="fa fa-group"></i></span>
                                <select required="1" name="role" class="form-control" aria-describedby="basic-addon3">
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.key}">${role.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br>
                            <div style="text-align:center;">
                                <input type="submit" value="Créer" class="btn btn-success">
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer" style="text-align:center;"></div>

                    <table class="table table-hover table-bordered table-condensed">
                        <thead>
                        <tr class="info bl-text-center">
                            <td>Nom d'utilisateur</td>
                            <td>Role</td>
                            <td>Action</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.roleName}</td>
                                <td><a href="/admin/user/${user.username}/edit.do" class="btn btn-primary btn-xs">Edit</a>
                                    <a href="/admin/user/${user.username}/delete.do" class="btn btn-danger btn-xs">Delete</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>


    </div>

</body>

<script   src="https://code.jquery.com/jquery-3.1.0.min.js"   integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s="   crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
