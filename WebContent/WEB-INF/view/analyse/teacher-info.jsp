<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
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
    <div class="module-row" style="height: 330px">
        <div id="star-graph" class="module" style="width: 55%;min-width: 700px;padding: 0">
        </div>
        <div class="module" style="width: 45%;">
            <div class="module-header">教师信息</div>
            <div id="info-card" class="module-content">

            </div>
        </div>
    </div>
    <div class="module-row">
        <div id="linear-graph" class="module" style="width: 100%;min-height: 400px;">
        </div>
    </div>
    <div class="module-row">
      <div id="bar-graph" class="module" style="width: 100%;min-height: 300px;">
      </div>
    </div>
    <div class="module-row">
      <div id="course-graph" class="module" style="width: 100%;min-height: 500px;">
      </div>
    </div>
</div>
<!-- Footer -->
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/main.js" type="text/javascript"></script>
<script src="/assets/js/teacher.js" type="text/javascript"></script>
<script type="text/javascript">

  let id = 2;
  id = <%=request.getAttribute("id")%>;
  id= checkInvalid(id)?332:id;

  let starGraph = echarts.init(document.getElementById('star-graph'));
  let linearGraph = echarts.init(document.getElementById('linear-graph'));
  let barGraph = echarts.init(document.getElementById('bar-graph'));
  let courseGraph = echarts.init(document.getElementById('course-graph'));

const infoSQL =
`
select id,name,ifnull(age,0) as age,area,area2,createDate,sex,level,type,ifnull(b.money,0) as money,ifnull(b.day,0) as day,ifnull(c.money,0) as moneyYear,ifnull(c.day,0) as dayYear,ifnull(student,0) as student, ifnull(course,0) as course from
(select * from teacher where id=${id} ) as a
left join
(select teacherId,sum(income) as money,count(*) as day from daily where teacherId=${id} and income >0 and date>date_sub(curdate(),interval 30 day) and date <= curdate() group by teacherId) as b
on a.id = b.teacherId
left join
(select teacherId,sum(income) as money,count(*) as day from daily where teacherId=${id} and income >0 and date>date_sub(curdate(),interval 365 day) and date <= curdate() group by teacherId) as c
on a.id = c.teacherId
left join
(select teacherId,count(distinct(studentId)) as student,count(distinct(courseId)) as course from daily where teacherId=${id} and income >0 and date>date_sub(curdate(),interval 365 day) and date <= curdate() group by teacherId) as d
on a.id = d.teacherId
`;
  const lineChartSQL =
    `
select a.date,ifnull(attend,0) as attend,ifnull(ifnull(quit,0)*moneyPerDay,0) as quit from
(select date,sum(income) as attend
from daily
where teacherId = ${id} and income>0
group by date) as a
left join
(select daily.date,count(distinct(studentId)) as quit,moneyPerDay
from daily,course
where daily.teacherId = ${id} and income=0 and course.id = daily.courseId
group by date) as b
on a.date = b.date
`;
  const barChartSQL = `
select a.date,ifnull(attend,0) as attend,ifnull(quit,0) as quit from
(select date,count(distinct(studentId)) as attend
from daily
where teacherId = ${id} and income>0
group by date) as a
left join
(select date,count(distinct(studentId)) as quit
from daily
where teacherId = ${id} and income=0
group by date) as b
on a.date = b.date
`;
  const starChartSQL =
    `
select a.date ,ifnull(course,0) as course from
(select * from date where date>date_sub(curdate(),interval 180 day) and date <= curdate() ) as a
left join
(select date,count(distinct(courseId)) as course
from daily
where teacherId = ${id} and income>0
group by date) as b
on a.date = b.date;

`;
  const courseIncomeSQL = `
select name,total,average from
(select id,name from course where teacherId = ${id}) as a
left join
(select courseId,sum(income) as total,sum(income)/count(distinct(date)) as average
from daily
where teacherId = ${id} and income>0
group by courseId) as b
on a.id = b.courseId
`;

  renderInfoCard = (obj) => {
    $("#info-card").append(`
                <div>姓名：${obj.name}</div>
                <div>性别：${obj.sex}</div>
                <div>年龄：${obj.age}</div>
                <div>所在地区：${obj.area2}-${obj.area}</div>
                <div>教师类型：${obj.level}(${obj.type})</div>
                <div>月活跃度：${obj.day}</div>
                <div>月收入额：${obj.money}</div>
                <div>年活跃度：${obj.dayYear}</div>
                <div>年收入额：${obj.moneyYear}</div>
                <div>年课程数：${obj.course}</div>
                <div>年学生数：${obj.student}</div>
`);};

  renderStarChart = (data) => {
    starGraph.setOption(createCalendarOption(data));
  };

  renderLineChart = (data) => {
    linearGraph.setOption(createLineChartOption(data));
  };

  renderBarChart = (data) => {
    barGraph.setOption(createBarChartOption(data));
  };

  renderCourseIncomeChart = (data) => {
    courseGraph.setOption(createCourseIncomeOption(data));
  };

  //handle info card
  $.post(
    "/API/POST",
    {sql:infoSQL},
    (data)=>{
      data = JSON.parse(data);
      renderInfoCard(data[0]);
    }
  );
  $.post(
    "/API/POST",
    {sql:starChartSQL},
    (data)=>{
      data = JSON.parse(data).map((obj)=>([obj.date,obj.course]));
      renderStarChart(data);
    }
  );
  $.post(
    "/API/POST",
    {sql:lineChartSQL},
    (data)=>{
      data = JSON.parse(data);
      renderLineChart(data);
    }
  );
  $.post(
    "/API/POST",
    {sql:barChartSQL},
    (data)=>{
      data = JSON.parse(data);
      renderBarChart(data);
    }
  );
  $.post(
    "/API/POST",
    {sql:courseIncomeSQL},
    (data)=>{
      console.log(data);
      data = JSON.parse(data);
      console.log(data);
      renderCourseIncomeChart(data);
    }
  );

  navInit();
</script>
</body>
</html>
