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
  <div class="row">
    年龄维度
    <div class="pane">
      <div class="row" id="a00">所有</div>
      <div class="row" id="a12">≤12</div>
      <div class="row" id="a24">12-24</div>
      <div class="row" id="a36">24-36</div>
      <div class="row" id="a48">＞36</div>
    </div>
  </div>
</div>
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/main.js" type="text/javascript"></script>
<script src="/assets/js/student-total.js" type="text/javascript"></script>
<script type="text/javascript">
  let baseURL = '/analyse/total/student';
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
    const SQL1 =
      `
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from student where ${queryAge()}) as a
left join
(select studentId,sum(days) as quitCourse
from quit where ${queryDay()}
group by studentId ) as c
on a.id = c.studentId
left join
(select studentId,sum(days) as attendCourse
from attend where ${queryDay()}
group by studentId) as b
on b.studentId = c.studentId
order by attendCourse desc,quitCourse asc limit 0,20
`;

    const SQL2 =`
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from student where ${queryAge()}) as a
left join
(select studentId,sum(days) as quitCourse
from quit where ${queryDay()}
group by studentId ) as c
on a.id = c.studentId
left join
(select studentId,sum(days) as attendCourse
from attend where ${queryDay()}
group by studentId) as b
on b.studentId = c.studentId
order by quitCourse desc,attendCourse asc limit 0,20
`;
    const SQL3 = `
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from student where ${queryAge()}) as a
left join
(select studentId,sum(money) as quitCourse
from quit where ${queryDay()}
group by studentId ) as c
on a.id = c.studentId
left join
(select studentId,sum(money) as attendCourse
from attend where ${queryDay()}
group by studentId) as b
on b.studentId = c.studentId
order by attendCourse desc,quitCourse asc limit 0,20
`;
    const SQL4 = `
select ${queryArea()} as name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,${queryArea()} from student) as a
left join
(select studentId,sum(money) as quitCourse
from quit where ${queryDay()}
group by studentId ) as c
on a.id = c.studentId
left join
(select studentId,sum(money) as attendCourse
from attend where ${queryDay()}
group by studentId) as b
on b.studentId = c.studentId
group by ${queryArea()}
order by attendCourse desc,quitCourse asc
`;
    const SQL5 = `
select age,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,age from student where ${queryAge()}) as a
left join
(select studentId,sum(money) as quitCourse
from quit where ${queryDay()}
group by studentId ) as c
on a.id = c.studentId
left join
(select studentId,sum(money) as attendCourse
from attend where ${queryDay()}
group by studentId) as b
on b.studentId = c.studentId
group by age
order by age asc,attendCourse desc,quitCourse asc
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
        data = JSON.parse(data);
        renderChart5(data);
      }
    );
    navInit();
  choosePaneInit();
</script>
</body>
</html>
