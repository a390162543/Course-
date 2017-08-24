<%--
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
        #content .col-lg-6{
            margin: auto;
            padding-right: 100px;
        }
        #content .col-lg-6>.row:first-child>.title{
            margin-top: 0px;
        }
        #content .title{
            display: inline-block;
            color: black;
            line-height: 48px;
            font-size: 24px;
            border-bottom: solid 1px #c7c7c7;
            margin: 30px 0 20px 60px;
            width: 400px;

        }

        #content .label-text>.label{
            display: inline-block;
            color: black;
            text-align: right;
            width: 30%;
            line-height: 24px;
        }
        #content .label-text>.text{
            float: right;
            text-align: left;
            width: 60%;
            line-height: 24px;

        }
    </style>
</head>
<body class='main page'>
<!-- Navbar -->
<div class='navbar navbar-default' id='navbar' >
    <a class='navbar-brand' href='#' style="padding: 5px 10px">
        <i class='icon-beer'></i>
        Hierapolis
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
            <li class='active launcher'>
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
            <li class='launcher'>
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
            <li class="active"><a href="#">主页</a></li>
        </ul>
        <div id='toolbar'>

        </div>
    </section>
    <!-- Content -->
    <div id='content'>
        <div class="col-lg-6">
            <div class="row">
                <span class="title">用户信息</span>
            </div>
            <div class="row label-text">
                <label class="label">用户名称</label>
                <span class="text">${manager.username}</span>
            </div>
        </div>
        <div class="col-lg-6">

        </div>

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
