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
    <div class="module-row" style="height: 300px">
        <div id="star-graph" class="module" style="width: 55%;min-width: 700px;padding: 0">
        </div>
        <div class="module" style="width: 45%;">
            <div class="module-header">课程信息</div>
            <div id="info-card" class="module-content">

            </div>
        </div>
    </div>
    <div class="module-row">
        <div id="linear-graph" class="module" style="width: 100%;min-height: 300px;">
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
<script src="/assets/js/course.js" type="text/javascript"></script>
<script type="text/javascript">

  let id = 2;
  id = <%=request.getAttribute("id")%>;
  id= checkInvalid(id)?332:id;

  let starGraph = echarts.init(document.getElementById('star-graph'));
  let linearGraph = echarts.init(document.getElementById('linear-graph'));
  let courseGraph = echarts.init(document.getElementById('course-graph'));

const infoSQL =
`
select id,name,type,fromDate,toDate,moneyPerDay,ifnull(b.money,0) as money,ifnull(day,0) as day, days,ifnull(student,0) as student,ifnull(quit,0) as quit from
(select * from course where id=${id}) as a
left join
(select courseId,sum(income) as money,count(*) as day,count(distinct(studentId)) as student from daily where courseId=${id} and income >0 group by courseId) as b
on a.id = b.courseId
left join
(select courseId,count(distinct(studentId)) as quit from quit where courseId=${id}) as c
on a.id = c.courseId;
`;
  const lineChartSQL =
    `
select a.date,ifnull(attend,0) as attend,ifnull(quit,0) as quit from
(select date,count(distinct(studentId)) as attend
from daily
where courseId = ${id} and income>0
group by date) as a
left join
(select date,count(distinct(studentId)) as quit
from daily
where courseId = ${id} and income=0
group by date) as b
on a.date = b.date
`;
  const starChartSQL =
    `
select date,count(distinct(studentId)) as student
from daily
where courseId = ${id} and income>0
group by date
`;

  const courseScoreSQL = `
select score from attend
where courseId = ${id}
`;

  renderInfoCard = (obj) => {
    $("#info-card").append(`
                <div>名称：${obj.name}</div>
                <div>类型：${obj.type}</div>
                <div>开课时间：${obj.fromDate}</div>
                <div>结课时间：${obj.toDate}</div>
                <div>课时价格：${obj.moneyPerDay}</div>
                <div>总活跃度：${obj.day}</div>
                <div>总消费额：${obj.money}</div>
                <div>结业学生：${obj.student}</div>
                <div>退课学生：${obj.quit}</div>
                <div>退课率：${((obj.quit*100)/(obj.student*1+obj.quit*1)).toFixed(2)}%</div>
`);};

  renderStarChart = (data) => {
    starGraph.setOption(createCalendarOption(data));
  };

  renderLineChart = (data) => {
    linearGraph.setOption(createLineChartOption(data));
  };

  renderCourseScoreChart = (data) => {
    courseGraph.setOption(createCourseScoreOption(data));
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
      data = JSON.parse(data).map((obj)=>([obj.date,obj.student]));
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
    {sql:courseScoreSQL},
    (data)=>{
      console.log(data);
      data = JSON.parse(data);
      console.log(data);
      renderCourseScoreChart(data);
    }
  );

  navInit();
</script>
</body>
</html>
