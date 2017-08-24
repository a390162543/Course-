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
    <style type="text/css">
      .table>.row>.cell{
        padding-left: 15px;
      }
      .table>.row>.cell:nth-child(1),
      .table>.row>.cell:nth-child(2),
      .table>.row>.cell:nth-child(5){
        width: 80px;
        min-width: 80px;
      }
      .table>.row>.cell:nth-child(3),
      .table>.row>.cell:nth-child(4){
        flex-shrink: 3;
        width: 70px;
        max-width: 80px;
        min-width: 50px;
      }
      .table>.row>.cell:nth-child(6),
      .table>.row>.cell:nth-child(7),
      .table>.row>.cell:nth-child(8){
        min-width: 110px;
      }
      .table>.row>.cell:nth-child(9),
      .table>.row>.cell:nth-child(10){
        min-width: 120px;
      }
      .table>.row>.cell:nth-child(11){
        min-width: 80px;
      }
    </style>
</head>
<body>

<div class="header">
    <div class="header-left">
        <div class="sign">
            Course+分析站
        </div>
        <div id='nav-bar' class="nav-bar">
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
<div class="chooser">
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
let base = '/analyse/teacher';
let page = 0;
page = <%=request.getParameter("page")%>;
if(checkInvalid(page)){ page = 1;}
let day = 0;
day = <%=request.getParameter("day")%>;
if(checkInvalid(day)){ day = 15;}
let period = parseDayForTableHeader(day);
const lowerBound = page*20-20;
const upperBound = page*20;

$(".table").append(
  `<div class="row">
    <div class="cell">教师编号</div>
    <div class="cell">姓名</div>
    <div class="cell">性别</div>
    <div class="cell">年龄</div>
    <div class="cell">所在地区</div>
    <div class="cell">教师类型</div>
    <div class="cell">${period}学生数</div>
    <div class="cell">${period}课程数</div>
    <div class="cell">${period}学生活跃</div>
    <div class="cell">${period}课程收入</div>
    <div class="cell">操作</div>
</div>`);

renderRow = (obj) => {
    $(".table").append(
      `<div class="row">
    <div class="cell">${obj.id}</div>
    <div class="cell">${obj.name}</div>
    <div class="cell">${obj.sex}</div>
    <div class="cell">${obj.age}</div>
    <div class="cell">${obj.area2}-${obj.area}</div>
    <div class="cell">${obj.level}(${obj.type})</div>
    <div class="cell">${obj.student}</div>
    <div class="cell">${obj.course}</div>
    <div class="cell">${obj.day}</div>
    <div class="cell">${obj.money}</div>
<div class="cell">
<i class='icon-edit'></i><a href="${base}/${obj.id}">查看</a>
</div>
</div>`
);};
const sql = `
select id,name,ifnull(age,0) as age,area,area2,createDate,sex,level,type,ifnull(money,0) as money,ifnull(day,0) as day,ifnull(student,0) as student, ifnull(course,0) as course from
(select * from teacher where id>${lowerBound} and id<=${upperBound}) as a
left join
(select teacherId,sum(income) as money,count(*) as day from daily where teacherId>${lowerBound} and teacherId<=${upperBound} and income >0 and date>date_sub(curdate(),interval ${day} day) and date <= curdate() group by teacherId) as b
on a.id = b.teacherId
left join
(select teacherId,count(distinct(studentId)) as student,count(distinct(courseId)) as course from daily where teacherId>${lowerBound} and teacherId<=${upperBound} and income >0 and date>date_sub(curdate(),interval ${day} day) and date <= curdate() group by teacherId) as c
on a.id = c.teacherId
`;
mainPageInit();
  navInit();
</script>
</body>
</html>
