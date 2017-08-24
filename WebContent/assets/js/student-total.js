createOption1 = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.attendCourse*1-o1.quitCourse*1-o2.attendCourse*1+o2.quitCourse*1));
  let data2 = [{name:'上课',value:0},{name:'退课',value:0}];
  data.forEach((obj)=>{
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
      text: '学生修课TOP20',
      subtext: '看看机构有哪些学霸 [排序规则为总修课时数(大~小)-退选课时数(小~大)]',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '参与课程排行',
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
      name:"课程数量",
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
      name:"学生姓名",
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
      data: data.map((obj)=>(obj.attendCourse*1-obj.quitCourse*1)),

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
      text: '学生退课TOP20',
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
      name:"课程数量",
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
      name:"学生姓名",
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
      text: '学生付费TOP20',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '付费排行',
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
      name:"学生姓名",
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
      data: data.map((obj)=>(obj.attendCourse*1-obj.quitCourse*1)),

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
      text: '地区付费分析',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '付费排行',
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
      name:"人均付费金额",
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
      data: data.map((obj)=>(obj.attendCourse*1-obj.quitCourse*1)),

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

createOption5 = (data) =>{
  // data = [{age:'11',attendCourse:5,quitCourse:1},];
  data = data.filter((obj)=>(obj.attendCourse != 0));
  data = data.sort((o1,o2)=>(o1.age*1-o2.age*1));
  data.forEach((obj)=>{
    obj.attendCourse = (obj.attendCourse*1).toFixed();
    obj.quitCourse = (obj.quitCourse*1).toFixed();
  });
  let option = {
    backgroundColor: '#404a59',
    title: {
      text: '付费年龄分布',
      left: 'center',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    toolbox: {
      feature: {
        dataView: {
          show: true,
          readOnly: false
        },
        restore: {
          show: true
        },
        saveAsImage: {
          show: true
        }
      }
    },
    legend:{
      data:['付费','退课'],
      top:30,
      left:'20%',
      textStyle:{
        color:'#fff',
      },
    },
    xAxis: [{
      type: 'category',
      name:'年龄',
      data: data.map((obj)=>(obj.age)),
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
      boundaryGap:false,
    },],
    yAxis: [{
      type: 'value',
      name: '人均付费金额',
      axisLabel: {
        formatter: '{value} '
      },
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    }],
    series: [{
      name: '付费',
      type: 'line',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#8dc1a9"}
      },
      areaStyle:{
        normal:{
        }
      },
      data: data.map((obj)=>(obj.attendCourse)),
      markPoint:{
        data:[{
          name:'最大值',
          type:'max',
        },{
          name:'最小值',
          type:'min',
        }],
      },

    },{
      name: '退课',
      type: 'line',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#dd6b66"}
      },
      areaStyle:{
        normal:{
        }
      },
      markPoint:{
        data:[{
          name:'最大值',
          type:'max',
        },{
          name:'最小值',
          type:'min',
        }],
      },
      data: data.map((obj)=>(obj.quitCourse)),

    }]
  };
  return option;
};