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
            <form method="post" action="/human/addTeacher.do">
                <fieldset>
                    <legend>添加教师</legend>
                </fieldset>
                <div class='form-group row'>
                    <label class='control-label'>教师名称</label>
                    <input class='form-control pull-right' placeholder='请输入教师名称' type="text" name="name">
                </div>
                <div class='form-group row'>
                    <label class='control-label'>教师等级</label>
                    <input class='form-control pull-right' placeholder='请输入教师等级' type="text" name="level">
                </div>
                <div class='form-group row'>
                    <label class='control-label'>教师年龄</label>
                    <input class='form-control pull-right' placeholder='请输入教师年龄' type="text" name="age">
                </div>
                <div class="pull-right">
                    <a class="btn btn-danger" style="margin-right: 10px" href="/human/teacher">返回</a>
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