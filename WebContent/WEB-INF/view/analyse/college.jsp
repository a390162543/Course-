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
  let base = '/analyse/college';
  let page = 0;
  page = <%=request.getParameter("page")%>;
  if(checkInvalid(page)){ page = 1;}
  let day = 0;
  day = <%=request.getParameter("day")%>;
  if(checkInvalid(day)){ day = 7;}
  let period = parseDayForTableHeader(day);
  const lowerBound = page*2-2;
  const upperBound = page*2;

  $(".table").append(
    `<div class="row">
      <div class="cell">机构编号</div>
      <div class="cell">机构名称</div>
      <div class="cell">所在地区</div>
      <div class="cell">注册时间</div>
      <div class="cell">${period}收入额</div>
      <div class="cell">${period}活跃课程</div>
      <div class="cell">${period}活跃教师</div>
      <div class="cell">${period}活跃学生</div>
    <div class="cell">操作</div>
</div>`);
renderRow = (obj) => {
    $(".table").append(
      `<div class="row">
<div class="cell">${obj.id}</div>
<div class="cell">${obj.name}</div>
<div class="cell">${obj.area2}-${obj.area}</div>
<div class="cell">${obj.createDate}</div>
<div class="cell">${obj.money}</div>
<div class="cell">${obj.course}</div>
<div class="cell">${obj.teacher}</div>
<div class="cell">${obj.student}</div>
<div class="cell">
    <i class='icon-edit'></i><a href="${base}/${obj.id}">查看</a>
</div>
</div>`
);};
const sql = `
select id,name,area,area2,createDate,ifnull(b.money,0) as money,ifnull(teacher,0) as teacher,ifnull(course,0) as course,ifnull(student,0) as student from
(select * from college where id>${lowerBound} and id<=${upperBound}) as a
left join
(select collegeId,sum(income) as money,count(distinct(studentId)) as student, count(distinct(courseId)) as course,count(distinct(teacherId)) as teacher  from daily where collegeId>${lowerBound} and collegeId<=${upperBound} and income >0 and ${sqlSubDate(day)} group by collegeId) as b
on a.id = b.collegeId;
`;
mainPageInit();
    navInit();
</script>
</body>
</html>
