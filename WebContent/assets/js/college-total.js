createOption1 = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.attendCourse*1-o1.quitCourse*1-o2.attendCourse*1+o2.quitCourse*1));
  let data2 = [{name:'上课',value:0},{name:'退课',value:0}];
  data.forEach((obj)=>{
    obj.name = `ID${obj.id} ${obj.name}`;
    data2[0].value = data2[0].value + Math.abs(obj.attendCourse*1 -obj.quitCourse*1);
    data2[1].value = data2[1].value + obj.quitCourse*1;
  });
  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:['上课','退课'],
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '机构热度TOP20',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '机构课程排行',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: 'TOP20退课比率图',
      x: '80%',
      top:'20%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '50%',
      containLabel: true,
      left:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"学生数量",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: data.map((obj)=>(obj.name)),
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"课程名称",
    }],
    series: [{
      type: 'bar',
      name:'上课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#8dc1a9',
        },
      },
      data: data.map((obj)=>(Math.abs(obj.attendCourse*1-obj.quitCourse*1))),

    },{
      type: 'bar',
      name:'退课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#dd6b66',
        },
      },
      data: data.map((obj)=>(obj.quitCourse)),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['80%', '55%'],
      data: data2,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      },
      itemStyle:{
        color:['red','green'],
      },
    }],
    color:['#9bd4b9','#dd6b66'],
  };
  return option;
};

createOption2 = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.quitCourse*1-o2.quitCourse*1));
  let data2 = [{name:'上课',value:0},{name:'退课',value:0}];
  data.forEach((obj)=>{
    obj.name = `ID${obj.id} ${obj.name}`;
    data2[0].value = data2[0].value + Math.abs(obj.attendCourse*1-obj.quitCourse*1);
    data2[1].value = data2[1].value + obj.quitCourse*1;
  });
  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:['上课','退课'],
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '差评机构TOP20',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '退课/选课情况',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: 'TOP20退课比率图',
      x: '80%',
      top:'20%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '50%',
      containLabel: true,
      left:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"学生数量",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: data.map((obj)=>(obj.name)),
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"课程名称",
    }],
    series: [{
      type: 'bar',
      name:'退课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#dd6b66',
        },
      },
      data: data.map((obj)=>(obj.quitCourse)),

    },{
      type: 'bar',
      name:'上课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#8dc1a9',
        },
      },
      data: data.map((obj)=>(Math.abs(obj.attendCourse*1-obj.quitCourse*1))),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['80%', '55%'],
      data: data2,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      },
      itemStyle:{
        color:['red','green'],
      },
    }],
    color:['#9bd4b9','#dd6b66'],
  };
  return option;
};

createOption3 = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.attendCourse*1-o1.quitCourse*1-o2.attendCourse*1+o2.quitCourse*1));
  let data2 = [{name:'付费',value:0},{name:'退课',value:0}];
  data.forEach((obj)=>{
    obj.attendCourse = (obj.attendCourse*1).toFixed();
    obj.quitCourse = (obj.quitCourse*1).toFixed();
    obj.name = `ID${obj.id} ${obj.name}`;
    data2[0].value = data2[0].value + obj.attendCourse*1 -obj.quitCourse*1;
    data2[1].value = data2[1].value + obj.quitCourse*1;
  });
  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:['上课','退课'],
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '机构收入TOP20',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '绩效排行',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: 'TOP20付费比率图',
      x: '80%',
      top:'20%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '50%',
      containLabel: true,
      left:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"金额",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: data.map((obj)=>(obj.name)),
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"课程名称",
    }],
    series: [{
      type: 'bar',
      name:'上课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#8dc1a9',
        },
      },
      data: data.map((obj)=>(Math.abs(obj.attendCourse*1-obj.quitCourse*1))),

    },{
      type: 'bar',
      name:'退课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#dd6b66',
        },
      },
      data: data.map((obj)=>(obj.quitCourse)),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['80%', '55%'],
      data: data2,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      },
      itemStyle:{
        color:['red','green'],
      },
    }],
    color:['#9bd4b9','#dd6b66'],
  };
  return option;
};

createOption4 = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.attendCourse*1-o1.quitCourse*1-o2.attendCourse*1+o2.quitCourse*1));
  let data2 = [{name:'付费',value:0},{name:'退课',value:0}];
  data.forEach((obj)=>{
    obj.attendCourse = (obj.attendCourse*1).toFixed();
    obj.quitCourse = (obj.quitCourse*1).toFixed();
    data2[0].value = data2[0].value + obj.attendCourse*1 -obj.quitCourse*1;
    data2[1].value = data2[1].value + obj.quitCourse*1;
  });
  let option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:['上课','退课'],
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '机构地域分析',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '绩效排行',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '付费比率图',
      x: '80%',
      top:'20%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '50%',
      containLabel: true,
      left:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"均机构收入",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: data.map((obj)=>(obj.name)),
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"地区",
    }],
    series: [{
      type: 'bar',
      name:'上课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#8dc1a9',
        },
      },
      data: data.map((obj)=>(Math.abs(obj.attendCourse*1-obj.quitCourse*1))),

    },{
      type: 'bar',
      name:'退课',
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#dd6b66',
        },
      },
      data: data.map((obj)=>(obj.quitCourse)),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['80%', '55%'],
      data: data2,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      },
      itemStyle:{
        color:['red','green'],
      },
    }],
    color:['#9bd4b9','#dd6b66'],
  };
  return option;
};

createOption5 = (data) => {


  let cityNames = [];
  let typeNames = [];
  data = data.filter((obj)=>(!checkInvalid(obj.type)));
  data.forEach((data)=>{
    if(!cityNames.contains(data.area)){
      cityNames.push(data.area);
    }
    if(!typeNames.contains(data.type)){
      typeNames.push(data.type);
    }
  });
  let citys = {};
  let types = {};
  cityNames.forEach((city,index)=>{
    citys[city] = index;
  });
  typeNames.forEach((type,index)=>{
    types[type] = index;
  });

  let matrix = [];
  for(let i=0;i<typeNames.length;i++){
    matrix[i] = [];
    for(let j=0;j<cityNames.length;j++){
      matrix[i][j] = 0;
    }
  }

  data.forEach((obj)=>{
    matrix[types[obj.type]][citys[obj.area]] = obj.count;
  });

  let series = [];
  matrix.forEach((row,index)=>{
    series.push({
      type: 'bar',
      name:typeNames[index],
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: (obj)=>{if(obj.data==0)return '';return obj.data;},
        }
      },
      data: row,
    });
  });
  let option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:typeNames,
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '机构-教师类型分布',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '80%',
      containLabel: true,
      left:'10%',
      right:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"教师数量",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: cityNames,
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"机构",
    }],
    series: series,
    color:['#dd6b66','#759aa0','#e69d87','#8dc1a9'
      ,'#ea7e53','#eedd78','#73a373','#73b9bc']
  };
  return option;
};

createOption6 = (data) => {


  let cityNames = [];
  let typeNames = [];
  data = data.filter((obj)=>(!checkInvalid(obj.type)));
  data.forEach((data)=>{
    if(!cityNames.contains(data.area)){
      cityNames.push(data.area);
    }
    if(!typeNames.contains(data.type)){
      typeNames.push(data.type);
    }
  });
  let citys = {};
  let types = {};
  cityNames.forEach((city,index)=>{
    citys[city] = index;
  });
  typeNames.forEach((type,index)=>{
    types[type] = index;
  });

  let matrix = [];
  for(let i=0;i<typeNames.length;i++){
    matrix[i] = [];
    for(let j=0;j<cityNames.length;j++){
      matrix[i][j] = 0;
    }
  }

  data.forEach((obj)=>{
    matrix[types[obj.type]][citys[obj.area]] = obj.count;
  });

  let series = [];
  matrix.forEach((row,index)=>{
    series.push({
      type: 'bar',
      name:typeNames[index],
      stack:'1',
      z: 3,
      label: {
        normal: {
          position: '',
          show: true,
          formatter: (obj)=>{if(obj.data==0)return '';return obj.data;},
        }
      },
      data: row,
    });
  });
  let option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:typeNames,
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '机构-课程类型分布',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '80%',
      containLabel: true,
      left:'10%',
      right:'10%',
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"课程数量",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: cityNames,
      axisLabel: {
        interval: 0,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"机构",
    }],
    series: series,
    color:['#dd6b66','#759aa0','#e69d87','#8dc1a9'
      ,'#ea7e53','#eedd78','#73a373','#73b9bc']
  };
  return option;
};