<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="true" %>
<html class='no-js' lang='en'>
<head>
    <meta charset='utf-8'>
    <meta content='IE=edge,chrome=1' http-equiv='X-UA-Compatible'>
    <title>分析站</title>
    <meta content='lab2023' name='author'>
    <meta content='' name='description'>
    <meta content='' name='keywords'>
    <link href="/assets/css/site.css" rel="stylesheet" type="text/css" />
    <link href="/assets/css/plugin/font-awesome.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="header">
    <div class="header-left">
        <div class="sign">
            Course+分析站
        </div>
        <div id="nav-bar" class="nav-bar">

        </div>
    </div>
    <div class="header-right">
        <div class="button">
            返回
        </div>
    </div>
</div>
<div class="content">
    <div class="module-row">
        <div class="module">
            <div class="table">

            </div>
            <div class="module-footer" style="text-align: center;">
                <ul class="pagination">
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- Footer -->
<div class="spinner">
    <div class="bounce1"></div>
    <div class="bounce2"></div>
    <div class="bounce3"></div>
</div>
<div class="finder">
  <input id="finder-input" placeholder="请输入查询ID" type="text"/>
  <div id="finder-button">查询</div>
</div>
<!-- Javascripts -->
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>

<script src="/assets/js/main.js" type="text/javascript"></script>
<script type="text/javascript">
  let base = '/analyse/course';
  let page = 0;
  page = <%=request.getParameter("page")%>;
  if(checkInvalid(page)){ page = 1;}
  let day = 0;
const lowerBound = page*30-30;
const upperBound = page*30;

  $(".table").append(`
    <div class="row">
        <div class="cell">课程编号</div>
        <div class="cell">课程名称</div>
        <div class="cell">课程类型</div>
        <div class="cell">开课时间</div>
        <div class="cell">结课时间</div>
        <div class="cell">课时价格</div>
        <div class="cell">总活跃度</div>
        <div class="cell">总收入额</div>
        <div class="cell">结业学生</div>
        <div class="cell">操作</div>
    </div>
`);
renderRow = (obj) => {
    $(".table").append(
      `<div class="row">
<div class="cell">${obj.id}</div>
<div class="cell">${obj.name}</div>
<div class="cell">${obj.type}</div>
<div class="cell">${obj.fromDate}</div>
<div class="cell">${obj.toDate}</div>
<div class="cell">${obj.moneyPerDay}</div>
<div class="cell">${obj.day}</div>
<div class="cell">${obj.money}</div>
<div class="cell">${obj.student}</div>
<div class="cell">
<i class='icon-edit'></i><a href="${base}/${obj.id}">查看</a>
</div>
</div>`
);};
const sql = `
select id,name,type,fromDate,toDate,moneyPerDay,ifnull(b.money,0) as money,ifnull(day,0) as day, days,ifnull(student,0) as student from
(select * from course where id>${lowerBound} and id<=${upperBound}) as a
left join
(select courseId,sum(income) as money,count(*) as day,count(distinct(studentId)) as student from daily where courseId>${lowerBound} and courseId<=${upperBound} and income >0 group by courseId) as b
on a.id = b.courseId;
`;
  mainPageInit();
  navInit();
</script>
</body>
</html>
