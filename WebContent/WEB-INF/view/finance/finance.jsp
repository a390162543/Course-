<%@ page import="org.springframework.web.context.annotation.RequestScope" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.zhsite.model.Course" %>
<%@ page import="cn.zhsite.model.CourseModify" %>
<%@ page import="cn.zhsite.model.Payment" %>
<%@ page import="cn.zhsite.model.Payback" %><%--
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
                <a href="/finance/home">财务主页</a>
            </li>
            <li class='launcher'>
                <i class='icon-table'></i>
                <a href="/finance/payment">财务结算</a>
            </li>
            <li class='launcher active'>
                <i class='icon-cloud'></i>
                <a href='/finance/finance'>财务状况</a>
            </li>
        </ul>
        <div data-toggle='tooltip' id='beaker' title='Made by lab2023'></div>
    </section>
    <!-- Tools -->
    <section id='tools'>
        <ul class='breadcrumb' id='breadcrumb'>
            <li class='title'>Course+</li>
            <li class="active"><a href="#">财务状况</a></li>
        </ul>
    </section>
    <!-- Content -->
    <div id='content'>
        <%List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
          List<Payback> paybackList = (List<Payback>) request.getAttribute("paybackList");
        %>
        <%if(paymentList.isEmpty() && paybackList.isEmpty()){%>
        <div class="whole-page">
            您没有账单。
        </div>
        <%}else{%>
        <table class='table'>
            <thead>
            <tr>
                <th>单据类型</th>
                <th>单据编号</th>
                <th>课程编号</th>
                <th>学生编号</th>
                <th>总计金额</th>
                <th>创建时间</th>
                <th>单据状态</th>
                <th class='actions'></th>
            </tr>
            </thead>
            <tbody>
        <%for(Payment payment:paymentList) {
            request.setAttribute("payment",payment);
        %>
            <tr class=''>
                <td>收款</td>
                <td>${payment.id}</td>
                <td>${payment.courseId}</td>
                <td>${payment.studentId}</td>
                <td>${payment.money}</td>
                <td>${payment.createTime}</td>
                <td>${payment.state.chinese}</td>
                <td class='action'>
                </td>
            </tr>


        <%}%>
        <%for(Payback payback:paybackList) {
            request.setAttribute("payback",payback);
        %>
            <tr class=''>
                <td>退款</td>
                <td>${payback.id}</td>
                <td>${payback.courseId}</td>
                <td>${payback.studentId}</td>
                <td>${payback.money}</td>
                <td>${payback.createTime}</td>
                <td>${payback.state.chinese}</td>
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
