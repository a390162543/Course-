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
            <form method="post" action="/college/course/member.do">
                <fieldset>
                    <legend>学员课程登记</legend>
                </fieldset>
                <div class='form-group row hidden'>
                    <label class='control-label'>id</label>
                    <input class='form-control pull-right' type='text' name="courseId" value="${course.id}">
                </div>
                <div class='form-group row'>
                    <label class='control-label'>会员卡号</label>
                    <input class='form-control pull-right' placeholder='请输入会员卡号' type="text" name="memberId">
                </div>
                <div class='form-group row' style="padding-left: 40px">
                    使用会员卡余额支付。
                </div>
                <div class="pull-right">
                    <a class="btn btn-danger" style="margin-right: 10px" href="/college/course">返回</a>
                    <button class="btn btn-primary" type="submit">确定</button>
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