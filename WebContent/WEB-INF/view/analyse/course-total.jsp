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
    <div class="module-row">
        <div id="graph1" class="module" style="width: 100%;min-height: 500px;">
        </div>
    </div>
    <div class="module-row">
        <div id="graph2" class="module" style="width: 100%;min-height: 500px;">
        </div>
    </div>
    <div class="module-row">
      <div id="graph3" class="module" style="width: 100%;min-height: 500px;">
      </div>
    </div>
  <div class="module-row">
    <div id="graph4" class="module" style="width: 100%;min-height: 500px;">
    </div>
  </div>
  <div class="module-row">
    <div id="graph5" class="module" style="width: 100%;min-height: 500px;">
    </div>
  </div>
  <div class="module-row">
    <div id="graph6" class="module" style="width: 100%;min-height: 500px;">
    </div>
  </div>
</div>
<!-- Footer -->
<div class="choose-pane">
  <div class="row">
    时间维度
    <div class="pane">
      <div class="row" id="d0">所有</div>
      <div class="row" id="d30">30日</div>
      <div class="row" id="d90">90日</div>
      <div class="row" id="d360">360日</div>
      <div class="row" id="d720">720日</div>
    </div>
  </div>
  <div class="row">
    地域维度
    <div class="pane">
      <div class="row" id="a1">市级</div>
      <div class="row" id="a2">省级</div>
    </div>
  </div>
</div>
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/main.js" type="text/javascript"></script>
<script src="/assets/js/course-total.js" type="text/javascript"></script>
<script type="text/javascript">
  let baseURL = '/analyse/total/course';
    let id = 2;
    id = <%=request.getAttribute("id")%>;
    id= checkInvalid(id)?1:id;

  paramDay = <%=request.getParameter("day")%>;
  paramLimit = <%=request.getParameter("limit")%>;
  paramArea = <%=request.getParameter("area")%>;
  paramAgeStart = <%=request.getParameter("ageStart")%>;
  paramAgeEnd = <%=request.getParameter("ageEnd")%>;

    let graph1 = echarts.init(document.getElementById('graph1'));
    let graph2 = echarts.init(document.getElementById('graph2'));
    let graph3 = echarts.init(document.getElementById('graph3'));
    let graph4 = echarts.init(document.getElementById('graph4'));
    let graph5 = echarts.init(document.getElementById('graph5'));
    let graph6 = echarts.init(document.getElementById('graph6'));
    const SQL1 =
      `
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from course where ${queryDay()}) as a
left join
(select courseId,count(*) as attendCourse
from attend
group by courseId) as b
on a.id = b.courseId
left join
(select courseId,count(*) as quitCourse
from quit
group by courseId ) as c
on a.id = c.courseId
group by a.id
order by attendCourse desc,quitCourse asc limit 0,20
`;
    const SQL2 =`
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from course where ${queryDay()}) as a
left join
(select courseId,count(*) as attendCourse
from attend
group by courseId) as b
on a.id = b.courseId
left join
(select courseId,count(*) as quitCourse
from quit
group by courseId ) as c
on a.id = c.courseId
group by a.id
order by quitCourse desc, attendCourse asc limit 0,20
`;
    const SQL3 = `
select id,name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,name from course where ${queryDay()}) as a
left join
(select courseId,sum(money) as attendCourse
from attend
group by courseId) as b
on a.id = b.courseId
left join
(select courseId,sum(money) as quitCourse
from quit
group by courseId ) as c
on a.id = c.courseId
group by a.id
order by attendCourse desc,quitCourse asc limit 0,20
`;
    const SQL4 = `
select type as name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,type from course where ${queryDay()}) as a
left join
(select courseId,sum(money) as quitCourse
from quit
group by courseId ) as c
on a.id = c.courseId
left join
(select courseId,sum(money) as attendCourse
from attend
group by courseId) as b
on a.id = b.courseId
group by type
order by attendCourse desc,quitCourse asc
`;
    const SQL5 = `
select ${queryArea()} as area,type,count(*) as count from
(select collegeId,type from course where ${queryDay()}) as a
left join
(select id,${queryArea()} from college) as b
on a.collegeId = b.id
group by ${queryArea()},type
`;
    const SQL6 = `
select type,level,count(*) as count from
(select teacherId,type from course where ${queryDay()}) as a
left join
(select id,level from teacher) as b
on a.teacherId = b.id
group by type,level
`;
    renderChart1 = (data) => {
        graph1.setOption(createOption1(data));
    };
    renderChart2 = (data) => {
      graph2.setOption(createOption2(data));
    };
    renderChart3 = (data) => {
      graph3.setOption(createOption3(data));
    };
    renderChart4 = (data) => {
      graph4.setOption(createOption4(data));
    };
    renderChart5 = (data) => {
      graph5.setOption(createOption5(data));
    };
    renderChart6 = (data) => {
      graph6.setOption(createOption6(data));
    };
    $.post(
      "/API/POST",
      {sql:SQL1},
      (data)=>{
          data = JSON.parse(data);
          renderChart1(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL2},
      (data)=>{
        data = JSON.parse(data);
        renderChart2(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL3},
      (data)=>{
        data = JSON.parse(data);
        renderChart3(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL4},
      (data)=>{
        data = JSON.parse(data);
        renderChart4(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL5},
      (data)=>{
        console.log(SQL5)
        data = JSON.parse(data);
        renderChart5(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL6},
      (data)=>{
        data = JSON.parse(data);
        renderChart6(data);
      }
    );
    navInit();
  choosePaneInit();
</script>
</body>
</html>
