<%@ page import="org.springframework.web.context.annotation.RequestScope" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zhsite.model.Course" %>
<%@ page import="cn.zhsite.model.state.CourseState" %>
<%@ page import="cn.zhsite.model.College" %>
<%@ page import="cn.zhsite.model.state.CollegeState" %><%--
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
            <li class="active"><a href="#">机构信息</a></li>
        </ul>
        <div id='toolbar'>
        </div>
    </section>
    <!-- Content -->
    <div id='content'>
        <%List<College> collegeList = (List<College>) request.getAttribute("collegeList");%>
        <%if(collegeList.isEmpty()){%>
        <div class="whole-page">
            目前无机构。
        </div>
        <%}else{%>
        <table class='table'>
            <thead>
            <tr>
                <th>机构编号</th>
                <th>机构名称</th>
                <th>注册时间</th>
                <th>机构状态</th>
                <th class='actions'></th>
            </tr>
            </thead>
            <tbody>
            <%for(College college:collegeList) {
                request.setAttribute("college",college);
                if(college.getState() == CollegeState.APPLY){
                    request.setAttribute("op1","disabled");
                }else{
                    request.setAttribute("op1","");
                }
            %>
            <tr class=''>
                <td>${college.id}</td>
                <td>${college.name}</td>
                <td>${college.createTime}</td>
                <td>${college.state.chinese}</td>
                <td class='action'>
                    <a class='btn btn-primary' data-toggle='tooltip' href='/manage/college/${college.id}' title='查看课程'>
                        <i class='icon-search'></i>
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
