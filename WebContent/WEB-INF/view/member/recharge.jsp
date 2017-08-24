<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017/3/15
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class='no-js' lang='en'>
<head>
    <meta charset='utf-8'>
    <meta content='IE=edge,chrome=1' http-equiv='X-UA-Compatible'>
    <title>Course+</title>
    <meta content='lab2023' name='author'>
    <meta content='' name='description'>
    <meta content='' name='keywords'>
    <link href="/assets/css/application-default.css" rel="stylesheet" type="text/css" />
    <link href="/assets/css/plugin/font-awesome.css" rel="stylesheet" type="text/css" />
    <link href="/assets/image/favicon.ico" rel="icon" type="image/ico" />
    <style type="text/css">
        .control-label{
            width: 20%;
            text-align: right;
            line-height: 34px;
        }
        .form-control{
            width: 75%;
            margin-right: 10px;
        }
    </style>
</head>
<body class='login'>
<div class='wrapper' style="width: 500px">
    <div class='row'>
        <div class='col-lg-12'>
            <form method="post" action="/member/recharge.do">
                <fieldset>
                    <legend>账户余额充值</legend>
                </fieldset>
                <div class='form-group row hidden'>
                    <label class='control-label'>用户id</label>
                    <input class='form-control pull-right' type='text' name="memberId" value="${member.id}">
                </div>
                <div class='form-group row'>
                    <label class='control-label'>充值金额</label>
                    <input class='form-control pull-right' placeholder='请输入充值金额' type="number" name="money">
                </div>
                <div class="pull-right">
                    <a class="btn btn-danger" style="margin-right: 10px" href="/member/home">返回</a>
                    <button class="btn btn-primary" type="submit">充值</button>
                </div>

            </form>
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

