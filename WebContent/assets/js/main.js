checkInvalid = (value) =>{
  if(value == null || value == undefined || value == 0){
    return true;
  }
  return false;
};

parseDate = (date) => {
  return `${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()}`;
};

parseDayForTableHeader = (day) => {
  if(day==7){return '周';}
  if(day==15){return '半月';}
  if(day==30){return '月';}
  if(day==90){return '季';}
  if(day==365){return '年';}
  return '周期';
};

sqlSubDate = (day=30) => {
  return ` date>date_sub(curdate(),interval ${day} day) and date <= curdate() `;
};

renderFooter = (page) => {
  if(checkInvalid(page)){
    return;
  }
  if(page>10){
    $(".pagination").append(`<li><a href="${base}?page=${page-10}">«</a></li>`);
  }
  for(let i=page-4<1?1:page-4;i<=page+4;i++){
    if(i==page){
      $(".pagination").append(`<li><a class="active" href="#">${i}</a></li>`);
    }else{
      $(".pagination").append(`<li><a href="${base}?page=${i}">${i}</a></li>`);
    }
  }
  $(".pagination").append(`<li><a href="${base}?page=${page+10}">»</a></li>`);
};

renderChooser = (base,page,day) =>{
  if(checkInvalid(page)){
    return;
  }
  if(checkInvalid(day)){
    day = 30;
  }
  let b1 = day==7?' active ':'';
  let b2 = day==15?' active ':'';
  let b3 = day==30?' active ':'';
  let b4 = day==90?' active ':'';
  let b5 = day==365?' active ':'';
  $(".chooser").append(`<a class="option ${b1} " href="${base}?page=${page}&day=7">七日</a>`);
  $(".chooser").append(`<a class="option ${b2} " href="${base}?page=${page}&day=15">十五日</a>`);
  $(".chooser").append(`<a class="option ${b3} " href="${base}?page=${page}&day=30">月度</a>`);
  $(".chooser").append(`<a class="option ${b4} " href="${base}?page=${page}&day=90">季度</a>`);
  $(".chooser").append(`<a class="option ${b5} " href="${base}?page=${page}&day=365">年度</a>`);
};

renderFinder = (base) => {
  $("#finder-button").on("click",() => {
    let id = $("#finder-input").val();
    $("#finder-input").val("");
    window.location.href = `${base}/${id}`;
  });
};


mainPageInit = () => {
  renderChooser(base,page,day);
  renderFinder(base);
  $.post(
    "/API/POST",
    {sql: sql},
    (data) => {
      console.log("sql:",sql);
      data = JSON.parse(data);
      console.log("data:",data);
      data.forEach(renderRow);
      $(".spinner").hide();
      renderFooter(page);

    });
};

navInit = () => {
  $("#nav-bar").append(`
    <div class="group">
      <div class="button"><a href="/analyse/college">机构</a></div>
      <div class="sub-buttons">
        <div class="button"><a href="/analyse/college">个体</a></div>
        <div class="button"><a href="/analyse/total/college">总体</a></div>
      </div>
    </div>
    <div class="group">
      <div class="button"><a href="/analyse/teacher">教师</a></div>
      <div class="sub-buttons">
        <div class="button"><a href="/analyse/teacher">个体</a></div>
        <div class="button"><a href="/analyse/total/teacher">总体</a></div>
      </div>
    </div>
    <div class="group">
      <div class="button"><a href="/analyse/course">课程</a></div>
      <div class="sub-buttons">
        <div class="button"><a href="/analyse/course">个体</a></div>
        <div class="button"><a href="/analyse/total/course">总体</a></div>
      </div>
    </div>
    <div class="group">
      <div class="button"><a href="/analyse/student">学生</a></div>
      <div class="sub-buttons">
        <div class="button"><a href="/analyse/student">个体</a></div>
        <div class="button"><a href="/analyse/total/student">总体</a></div>
      </div>
    </div>
`);};

Array.prototype.contains = function (obj) {
  var i = this.length;
  while (i--) {
    if (this[i] === obj) {
      return true;
    }
  }
  return false;
};

let paramDay = 10950;
queryDay = () => {
  paramDay = checkInvalid(paramDay)?10950:paramDay;
  return ` date>date_sub(curdate(),interval ${paramDay} day) and date <= curdate() `;
};
getParamDay = () => {
  paramDay = checkInvalid(paramDay)?10950:paramDay;
  return `${paramDay}`;
};
let paramLimit = 20;
queryLimit = () => {
  paramLimit = checkInvalid(paramLimit)?20:paramLimit;
  return ` limit 0,${paramLimit} `;
};
getParamLimit = () => {
  paramLimit = checkInvalid(paramLimit)?20:paramLimit;
  return `${paramLimit}`;
};
let paramArea = 'area';
queryArea = () => {
  paramArea = checkInvalid(paramArea)?'area':paramArea;
  return ` ${paramArea} `;
};
getParamArea = () => {
  paramArea = checkInvalid(paramArea)?'area':paramArea;
  return `${paramArea}`;
};
let paramAgeStart = 0;
let paramAgeEnd = 100;
queryAge = () => {
  paramAgeStart = checkInvalid(paramAgeStart)?0:paramAgeStart;
  paramAgeEnd = checkInvalid(paramAgeEnd)?100:paramAgeEnd;
  return ` age>${paramAgeStart} and age <= ${paramAgeEnd} `;
};
getParamAgeStart= () => {
  paramAgeStart = checkInvalid(paramAgeStart)?0:paramAgeStart;
  return `${paramAgeStart}`;
};
getParamAgeEnd= () => {
  paramAgeEnd = checkInvalid(paramAgeEnd)?100:paramAgeEnd;
  return `${paramAgeEnd}`;
};
GET = () =>{
  let url = `${baseURL}?day=${getParamDay()}&limit=${getParamLimit()}&area='${getParamArea()}'&ageStart=${getParamAgeStart()}&ageEnd=${getParamAgeEnd()}`
  window.location.href = url;
};
choosePaneInit = ()=>{
  $('#d0').on('click',()=>{
    paramDay = 0;
    GET();
  });
  $('#d30').on('click',()=>{
    paramDay = 30;
    GET();
  });
  $('#d90').on('click',()=>{
    paramDay = 90;
    GET();
  });
  $('#d360').on('click',()=>{
    paramDay = 360;
    GET();
  });
  $('#d720').on('click',()=>{
    paramDay = 720;
    GET();
  });
  $('#a1').on('click',()=>{
    paramArea = 'area';
    GET();
  });
  $('#a2').on('click',()=>{
    paramArea = 'area2';
    GET();
  });
  $('#a00').on('click',()=>{
    paramAgeStart = 0;
    paramAgeEnd = 100;
    GET();
  });
  $('#a12').on('click',()=>{
    paramAgeStart = 0;
    paramAgeEnd = 12;
    GET();
  });
  $('#a24').on('click',()=>{
    paramAgeStart = 12;
    paramAgeEnd = 24;
    GET();
  });
  $('#a36').on('click',()=>{
    paramAgeStart = 24;
    paramAgeEnd = 36;
    GET();
  });
  $('#a48').on('click',()=>{
    paramAgeStart = 36;
    paramAgeEnd = 100;
    GET();
  });
  $('#a30').on('click',()=>{
    paramAgeStart = 0;
    paramAgeEnd = 30;
    GET();
  });
  $('#a45').on('click',()=>{
    paramAgeStart = 30;
    paramAgeEnd = 45;
    GET();
  });
  $('#a60').on('click',()=>{
    paramAgeStart = 45;
    paramAgeEnd = 100;
    GET();
  });
};