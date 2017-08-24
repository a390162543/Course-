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
            <div class="module-header">机构信息</div>
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
    <div class="module-row">
      <div id="teacher-graph" class="module" style="width: 100%;min-height: 500px;">
      </div>
    </div>
</div>
<!-- Footer -->
<!-- Javascripts -->
<script src="/assets/js/plugin/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/plugin/echarts.js" type="text/javascript"></script>
<script src="/assets/js/main.js" type="text/javascript"></script>
<script src="/assets/js/college.js" type="text/javascript"></script>
<script type="text/javascript">

    let id = 2;
    id = <%=request.getAttribute("id")%>;
    id= checkInvalid(id)?1:id;

    let starGraph = echarts.init(document.getElementById('star-graph'));
    let linearGraph = echarts.init(document.getElementById('linear-graph'));
    let barGraph = echarts.init(document.getElementById('bar-graph'));
    let courseGraph = echarts.init(document.getElementById('course-graph'));
    let teacherGraph = echarts.init(document.getElementById('teacher-graph'));
    const infoSQL =
      `
select id,name,area,area2,createDate,ifnull(b.money,0) as money,ifnull(b.day,0) as day,ifnull(student,0) as student, ifnull(course,0) as course from
(select * from college where id=${id} ) as a
left join
(select collegeId,sum(income) as money,count(*) as day from daily where collegeId=${id} and income >0 and date>date_sub(curdate(),interval 30 day) and date <= curdate() group by collegeId) as b
on a.id = b.collegeId
left join
(select collegeId,count(distinct(studentId)) as student,count(distinct(courseId)) as course from daily where collegeId=${id} and income >0 and date>date_sub(curdate(),interval 30 day) and date <= curdate() group by collegeId) as d
on a.id = d.collegeId
`;
    const otherSQL =`
select a.date ,ifnull(course,0) as course ,ifnull(attend,0) as attend ,ifnull(ifnull(quitNum,0)*moneyPerDay,0) as quit,attendNum,quitNum from
(select * from date where date>date_sub(curdate(),interval 180 day) and date <= curdate() ) as a
left join
(select date,count(distinct(courseId)) as course,sum(income) as attend,count(distinct(studentId)) as attendNum
from daily
where collegeId = ${id} and income>0
group by date) as b
on a.date = b.date
left join
(select daily.date,count(distinct(studentId)) as quitNum,moneyPerDay
from daily,course
where daily.collegeId = ${id} and income=0 and course.id = daily.courseId
group by daily.date) as c
on a.date = c.date ;
`;
    const courseIncomeSQL = `
select c.name,a.*,ifnull(b.quit,0) as quit from
(
select course.id,course.money*count(distinct(attend.studentId)) as total,course.moneyPerDay*count(distinct(attend.studentId)) as average
from course,attend
where course.id = attend.courseId and course.collegeId = ${id}
and date_sub(curdate(),interval 90 day)< course.toDate and course.toDate<=curdate()
group by course.id) as a
left join
(select courseId,sum(money) as quit from quit group by courseId) as b
on a.id = b.courseId
left join
(select * from course) as c
on a.id = c.id
`;
    const teacherIncomeSQL = `
select c.name,a.*,ifnull(b.quit,0) as quit from
(
select course.id,course.teacherId,course.money*count(distinct(attend.studentId)) as total,course.moneyPerDay*count(distinct(attend.studentId)) as average
from course,attend
where course.id = attend.courseId and course.collegeId = ${id}
and date_sub(curdate(),interval 90 day)< course.toDate and course.toDate<=curdate()
group by course.teacherId) as a
left join
(select courseId,sum(money) as quit from quit group by courseId) as b
on a.id = b.courseId
left join
(select * from teacher) as c
on a.teacherId = c.id
`;
    console.log(teacherIncomeSQL)
    renderInfoCard = (obj) => {
        $("#info-card").append(`
                <div>机构名称：${obj.name}</div>
                <div>所在地区：${obj.area2}-${obj.area}</div>
                <div>月活跃度：${obj.day}</div>
                <div>月收入额：${obj.money}</div>
                <div>月课程数：${obj.course}</div>
                <div>月学生数：${obj.student}</div>
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
    renderTeacherIncomeChart = (data) => {
      teacherGraph.setOption(createTeacherIncomeOption(data));
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
      {sql:otherSQL},
      (data)=>{
        data = JSON.parse(data);
        let data1 = data.map((obj)=>([obj.date,obj.course]));
        renderStarChart(data1);
        renderLineChart(data);
        let data2 = data.map((obj)=>({date:obj.date,attend:obj.attendNum,quit:obj.quitNum}));
        renderBarChart(data2);
      }
    );
    $.post(
      "/API/POST",
      {sql:courseIncomeSQL},
      (data)=>{
        data = JSON.parse(data);
        renderCourseIncomeChart(data);
      }
    );
    $.post(
      "/API/POST",
      {sql:teacherIncomeSQL},
      (data)=>{
        data = JSON.parse(data);
        console.log(data);
        renderTeacherIncomeChart(data);
      }
    );
    navInit();
</script>
</body>
</html>
