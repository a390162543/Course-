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
    <div id="graph7" class="module" style="width: 100%;min-height: 500px;">
    </div>
  </div>
  <div class="module-row">
    <div id="graph8" class="module" style="width: 100%;min-height: 500px;">
    </div>
  </div>
  <div class="module-row">
    <div id="graph9" class="module" style="width: 100%;min-height: 500px;">
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
  <div class="row">
    年龄维度
    <div class="pane">
      <div class="row" id="a00">所有</div>
      <div class="row" id="a30">≤30</div>
      <div class="row" id="a45">30-45</div>
      <div class="row" id="a60">45-60</div>
    </div>
  </div>
</div>
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/main.js" type="text/javascript"></script>
<script src="/assets/js/teacher-total.js" type="text/javascript"></script>
<script type="text/javascript">
  let baseURL = '/analyse/total/teacher';
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
    let graph7 = echarts.init(document.getElementById('graph7'));
    let graph8 = echarts.init(document.getElementById('graph8'));
    let graph9 = echarts.init(document.getElementById('graph9'));
    const SQL1 =
      `
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from teacher where ${queryAge()}) as a
left join
(select teacherId,count(*) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
left join
(select teacherId,count(*) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
group by a.id
order by attendCourse desc,quitCourse asc limit 0,20
`;
    const SQL2 =`
select id,name,ifnull(attendCourse,0) as attendCourse,ifnull(quitCourse,0) as quitCourse from
(select id,name from teacher where ${queryAge()}) as a
left join
(select teacherId,count(*) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
left join
(select teacherId,count(*) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
group by a.id
order by quitCourse desc, attendCourse asc limit 0,20
`;
    const SQL3 = `
select id,name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,name from teacher where ${queryAge()}) as a
left join
(select teacherId,sum(money) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
left join
(select teacherId,sum(money) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
group by a.id
order by attendCourse desc,quitCourse asc limit 0,20
`;
    const SQL4 = `
select type as name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,type from teacher where ${queryAge()}) as a
left join
(select teacherId,sum(money) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
left join
(select teacherId,sum(money) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
group by type
order by attendCourse desc,quitCourse asc
`;
    const SQL7 = `
select level as name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,level from teacher where ${queryAge()}) as a
left join
(select teacherId,sum(money) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
left join
(select teacherId,sum(money) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
group by level
order by attendCourse desc,quitCourse asc
`;
    const SQL8 = `
select ${queryArea()} as name,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,${queryArea()} from teacher where ${queryAge()}) as a
left join
(select teacherId,sum(money) as quitCourse
from quit where ${queryDay()}
group by teacherId ) as c
on a.id = c.teacherId
left join
(select teacherId,sum(money) as attendCourse
from attend where ${queryDay()}
group by teacherId) as b
on a.id = b.teacherId
group by ${queryArea()}
order by attendCourse desc,quitCourse asc
`;
    const SQL9 = `
select age,ifnull(avg(attendCourse),0) as attendCourse,ifnull(avg(quitCourse),0) as quitCourse from
(select id,age from teacher where ${queryAge()}) as a
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
    const SQL5 = `
select ${queryArea()} as area,type,count(*) as count from teacher where ${queryAge()}
group by ${queryArea()},type
`;
    const SQL6 = `
select type,level,count(*) as count from
(select teacherId,type from course where ${queryDay()}) as a
right join
(select id,level from teacher where ${queryAge()}) as b
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
    renderChart7 = (data) => {
      graph7.setOption(createOption7(data));
    };
    renderChart8 = (data) => {
      graph8.setOption(createOption8(data));
    };
    renderChart9 = (data) => {
      graph9.setOption(createOption9(data));
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
    $.post(
      "/API/POST",
      {sql:SQL6},
      (data)=>{
        data = JSON.parse(data);
        renderChart6(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL7},
      (data)=>{
        data = JSON.parse(data);
        renderChart7(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL8},
      (data)=>{
        data = JSON.parse(data);
        renderChart8(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:SQL9},
      (data)=>{
        data = JSON.parse(data);
        renderChart9(data);
      }
    );
    navInit();
  choosePaneInit();
</script>
</body>
</html>
