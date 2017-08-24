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
                <a href="/member/home">学员主页</a>
            </li>
            <li class='launcher'>
                <i class='icon-file-text-alt'></i>
                <a href="/member/course">所有课程</a>
            </li>
            <li class='launcher'>
                <i class='icon-table'></i>
                <a href="/member/course/attend">我的课程</a>
            </li>
            <li class='launcher'>
                <i class='icon-cloud'></i>
                <a href='/member/payment'>财务记录</a>
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
                <label class="label">用户标识</label>
                <span class="text">
                ${member.idString}
                    <a href="/member/invalidate.do" class="pull-right" style="padding-right: 80px">停用</a>
                </span>
            </div>
            <div class="row label-text">
                <label class="label">用户名称</label>
                <span class="text">${member.username}</span>
            </div>
            <div class="row label-text">
                <label class="label">身份证号</label>
                <span class="text">${member.idcard}</span>
            </div>
            <div class="row label-text">
                <label class="label">用户状态</label>
                <span class="text">${member.state.chinese}</span>
            </div>

            <div class="row">
                <span class="title">账户信息</span>
            </div>
            <div class="row label-text">
                <label class="label">账户余额</label>
                <span class="text">
                    ${member.money}
                    <a href="/member/recharge" class="pull-right" style="padding-right: 80px">充值</a>
                </span>
            </div>
            <div class="row label-text">
                <label class="label">会员等级</label>
                <span class="text">${member.level}</span>
            </div>
            <div class="row label-text">
                <label class="label">会员折扣</label>
                <span class="text">${member.rateInfo}</span>
            </div>
            <div class="row label-text">
                <label class="label">会员积分</label>
                <span class="text">${member.points-member.usedPoints}
                    <a href="/member/points.do" class="pull-right" style="padding-right: 80px">兑换</a>
                </span>
            </div>

            <div class="row label-text">
                <label class="label">银行卡号</label>
                <span class="text">${member.accountId}</span>
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
