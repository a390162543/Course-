<%@ page import="org.springframework.web.context.annotation.RequestScope" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zhsite.model.Course" %>
<%@ page import="cn.zhsite.model.Attend" %>
<%@ page import="cn.zhsite.model.state.AttendState" %><%--
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
                <a href="/manage/home">经理主页</a>
            </li>
            <li class='launcher'>
                <i class='icon-file-text-alt'></i>
                <a href="/manage/course">课程审批</a>
            </li>
            <li class='launcher'>
                <i class='icon-table'></i>
                <a href="/manage/payment">财务结算</a>
            </li>
            <li class='launcher active'>
                <i class='icon-bookmark'></i>
                <a href='/manage/college'>机构信息</a>
            </li>
            <li class='launcher'>
                <i class='icon-cloud'></i>
                <a href='/manage/finance'>财务状况</a>
            </li>
        </ul>
        <div data-toggle='tooltip' id='beaker' title='Made by lab2023'></div>
    </section>
    <!-- Tools -->
    <section id='tools'>
        <ul class='breadcrumb' id='breadcrumb'>
            <li class='title'>Course+</li>
            <li class=''><a href="/manage/college">机构信息</a></li>
            <li class="active"><a href="/manage/college/${collegeId}">课程信息</a></li>
            <li class="active"><a href="#">学生信息</a></li>
        </ul>
        <div id='toolbar'>
        </div>
    </section>
    <!-- Content -->
    <div id='content'>
        <%List<Attend> attendList = (List<Attend>) request.getAttribute("attendList");%>
        <%if(attendList.isEmpty()){%>
        <div class="whole-page">
            课程暂无学生。
        </div>
        <%}else{%>
        <table class='table'>
            <thead>
            <tr>
                <th>学生编号</th>
                <th>身份证号</th>
                <th>学生状态</th>
                <th>学生成绩</th>
                <th class='actions'></th>
            </tr>
            </thead>
            <tbody>
            <%for(Attend attend:attendList) {
                request.setAttribute("attend",attend);
            %>
            <tr class=''>
                <td>${attend.studentId}</td>
                <td>${attend.student.idcard}</td>
                <td>${attend.state.chinese}</td>
                <td>${attend.score}</td>
                <td class='action'>
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
