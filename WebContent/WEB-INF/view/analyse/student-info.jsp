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
            <div class="module-header">学生信息</div>
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
<script src="/assets/js/student.js" type="text/javascript"></script>
<script type="text/javascript">

  let id = 2;
  id = <%=request.getAttribute("id")%>;
  id= checkInvalid(id)?332:id;

  let starGraph = echarts.init(document.getElementById('star-graph'));
  let linearGraph = echarts.init(document.getElementById('linear-graph'));
  let courseGraph = echarts.init(document.getElementById('course-graph'));

const infoSQL =
`
select id,name,ifnull(age,0) as age,area,area2,createDate,sex,
ifnull(money,0) as money,ifnull(day,0) as day,
ifnull(moneyMonth,0) as moneyMonth,ifnull(dayMonth,0) as dayMonth,
ifnull(moneyQuarter,0) as moneyQuarter,ifnull(dayQuarter,0) as dayQuarter,
ifnull(moneyYear,0) as moneyYear,ifnull(dayYear,0) as dayYear
from
(select * from student where id = ${id} ) as a
left join
(select studentId,sum(income) as moneyYear,count(*) as dayYear from daily where studentId = ${id} and income >0 and date>date_sub(curdate(),interval 365 day) and date <= curdate() group by studentId) as b
on a.id = b.studentId
left join
(select studentId,sum(income) as moneyQuarter,count(*) as dayQuarter from daily where studentId = ${id} and income >0 and date>date_sub(curdate(),interval 90 day) and date <= curdate() group by studentId) as c
on a.id = c.studentId
left join
(select studentId,sum(income) as moneyMonth,count(*) as dayMonth from daily where studentId = ${id} and income >0 and date>date_sub(curdate(),interval 30 day) and date <= curdate() group by studentId) as d
on a.id = d.studentId
left join
(select studentId,sum(income) as money,count(*) as day from daily where studentId = ${id} and income >0 and date = curdate() group by studentId) as e
on a.id = e.studentId
`;
  const moneyLineChartSQL =
    `
select a.date,ifnull(b.money,0) as money,ifnull(b.day,0) as day from
(select date from date where ${sqlSubDate(180)} and date <= curdate()) as a
left join
(select studentId,date,sum(income) as money, count(*) as day
from daily
where studentId=${id} and ${sqlSubDate(180)} and income>0
group by studentId,date ) as b
on a.date = b.date
`;
  const starChartSQL =
    `
select a.date,ifnull(b.day,0) as day from
(select date from date where ${sqlSubDate(180)} and date <= curdate()) as a
left join
(select studentId,date,count(*) as day
from daily
where studentId=${id} and date>date_sub(curdate(),interval 180 day) and date <= curdate() and income>0
group by studentId,date) as b
on a.date = b.date
`;

  const courseTypeSQL = `
select coursetype.type as name,ifnull(a.num,0) as value
from
coursetype
left join
(select course.type,count(*) as num
from course,daily
where course.Id = daily.courseId and daily.studentId = ${id}
group by course.type) as a
on coursetype.type = a.type
`;

  renderInfoCard = (obj) => {
    $("#info-card").append(`
                <div>姓名：${obj.name}</div>
                <div>性别：${obj.sex}</div>
                <div>年龄：${obj.age}</div>
                <div>所在地区：${obj.area2}-${obj.area}</div>
                <div>日活跃度：${obj.day}</div>
                <div>日消费额：${obj.money}</div>
                <div>月活跃度：${obj.dayMonth}</div>
                <div>月消费额：${obj.moneyMonth}</div>
                <div>季活跃度：${obj.dayQuarter}</div>
                <div>季消费额：${obj.moneyQuarter}</div>
                <div>年活跃度：${obj.dayYear}</div>
                <div>年消费额：${obj.moneyYear}</div>
`);};

  renderStarChart = (data) => {
    starGraph.setOption(createCalendarOption(data));
  };

  renderMoneyLineChart = (data) => {
    linearGraph.setOption(createMoneyLineChartOption(data));
  };

  renderCourseChart = (data) => {
    courseGraph.setOption(createCoursePreferOption(data));
  };

  //handle info card
  $.post(
    "/API/POST",
    {sql:infoSQL},
    (data)=>{
      //console.log("sql:",infoSQL);
      data = JSON.parse(data);
      //console.log("data:",data);
      renderInfoCard(data[0]);
    }
  );
  $.post(
    "/API/POST",
    {sql:starChartSQL},
    (data)=>{
      //console.log("sql:",starChartSQL);
      data = JSON.parse(data).map((obj)=>([obj.date,obj.day]));
      //console.log("data:",data);
      renderStarChart(data)
    }
  );
  $.post(
    "/API/POST",
    {sql:moneyLineChartSQL},
    (data)=>{
      data = JSON.parse(data);
      //console.log(data);
      renderMoneyLineChart(data);
    }
  );
  $.post(
    "/API/POST",
    {sql:courseTypeSQL},
    (data)=>{
      data = JSON.parse(data);
      //console.log(data);
      renderCourseChart(data);
    }
  );

  navInit();
</script>
</body>
</html>
