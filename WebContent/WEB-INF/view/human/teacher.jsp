<%@ page import="org.springframework.web.context.annotation.RequestScope" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zhsite.model.Course" %>
<%@ page import="cn.zhsite.model.state.CourseState" %>
<%@ page import="cn.zhsite.model.College" %>
<%@ page import="cn.zhsite.model.state.CollegeState" %>
<%@ page import="cn.zhsite.model.Teacher" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017/3/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class='no-js' lang='en'>
<head>
    <meta charset='utf-8'>
    <meta content='IE=edge,chrome=1' http-equiv='X-UA-Compatible'>
    <title>Dashboard</title>
    <meta content='lab2023' name='author'>
    <meta content='' name='description'>
    <meta content='' name='keywords'>
    <link href="/assets/css/application-default.css" rel="stylesheet" type="text/css" />
    <link href="/assets/css/plugin/font-awesome.css" rel="stylesheet" type="text/css" />
    <link href="/assets/css/theme.css" rel="stylesheet" type="text/css" />
    <link href="/assets/image/favicon.ico" rel="icon" type="image/ico" />
    <style type="text/css">
    </style>
</head>
<body class='main page'>
<!-- Navbar -->
<div class='navbar navbar-default' id='navbar' >
    <a class='navbar-brand' href='#' style="padding: 5px 10px">
        <i class='icon-beer'></i>
        Course+
    </a>
    <ul class='nav navbar-nav pull-right'>
        <li class='dropdown user'>
            <a class='dropdown-toggle' data-toggle='dropdown' href='#'  style="padding: 5px 10px">
                <i class='icon-user'></i>
                <strong>John DOE</strong>
                <b class='caret'></b>
            </a>
            <ul class='dropdown-menu'>
                <li>
                    <a href="/home/logout.do">注销登录</a>
                </li>
            </ul>
        </li>
    </ul>
</div>
<div id='wrapper' style="top: 50px">
    <!-- Sidebar -->
    <section id='sidebar'>
        <i class='icon-align-justify icon-large' id='toggle'></i>
        <ul id='dock'>
            <li class='launcher'>
                <i class='icon-dashboard'></i>
                <a href="/human/home">人事主页</a>
            </li>
            <li class='launcher active'>
                <i class='icon-bookmark'></i>
                <a href='/human/teacher'>教师管理</a>
            </li>
        </ul>
        <div data-toggle='tooltip' id='beaker' title='Made by lab2023'></div>
    </section>
    <!-- Tools -->
    <section id='tools'>
        <ul class='breadcrumb' id='breadcrumb'>
            <li class='title'>Course+</li>
            <li class="active"><a href="#">教师管理</a></li>
        </ul>
        <div id='toolbar'>
            <a class="btn bg-primary" href="/human/addTeacher">添加教师</a>
        </div>
    </section>
    <!-- Content -->
    <div id='content'>
        <%List<Teacher> teacherList = (List<Teacher>) request.getAttribute("teacherList");%>
        <%if(teacherList.isEmpty()){%>
        <div class="whole-page">
            目前无教师。
        </div>
        <%}else{%>
        <table class='table'>
            <thead>
            <tr>
                <th>教师编号</th>
                <th>教师名称</th>
                <th>教师等级</th>
                <th>教师年龄</th>
                <th class='actions'></th>
            </tr>
            </thead>
            <tbody>
            <%for(Teacher teacher:teacherList) {
                request.setAttribute("teacher",teacher);
            %>
            <tr class=''>
                <td>${teacher.id}</td>
                <td>${teacher.name}</td>
                <td>${teacher.level}</td>
                <td>${teacher.age}</td>
                <td class='action'>
                    <a class='btn btn-info' data-toggle='tooltip' href='/human/modifyTeacher/${teacher.id}' title='修改信息'>
                        <i class='icon-edit'></i>
                    </a>
                </td>
            </tr>

            <%}%>
            </tbody>
        </table>
        <%}%>
    </div>
</div>
<!-- Footer -->
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/jquery-ui.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/modernizr.js" type="text/javascript"></script>
<script src="/assets/js/application-default.js" type="text/javascript"></script>

</body>
</html>
