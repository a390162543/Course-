createCalendarOption = (data) => {
  //data = [['2017-06-01','5'],['2017-06-02','1'],['2017-06-03','10']];

  let dates = data.map((obj)=>(obj[0])).sort((d1,d2)=>(new Date(d1).getTime()-new Date(d2).getTime()));
  let min = dates.shift();
  let max = dates.pop();

  let option = {
    backgroundColor: '#404a59',
    title: {
      top: 30,
      text: '课程活跃表',
      left: 'center',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip : {
      trigger: 'item'
    },
    legend: {
      top: '30',
      left: '100',
      data:['平日'],
      textStyle: {
        color: '#fff'
      },
      show:false,
    },
    calendar: [{
      backgroundColor:"#000",
      top: 100,
      left: 'center',
      range: [min,max],
      splitLine: {
        show: true,
        lineStyle: {
          color: '#000',
          width: 4,
          type: 'solid',
        },
      },
      yearLabel: {
        textStyle: {
          color: '#fff'
        }
      },
      monthLabel: {
        nameMap: 'cn',
      },
      dayLabel: {
        nameMap: 'cn',
      },
      itemStyle: {
        normal: {
          color: '#000',
          borderWidth: 1,
          borderColor: '#000',
        }
      }
    }],
    series : [
      {
        name: '平日',
        type: 'scatter',
        coordinateSystem: 'calendar',
        data: data,
        symbol:'rect',
        symbolSize: 18,
        hoverAnimation:false,
        itemStyle: {
          normal: {
            color: (obj)=> {
              let val = obj.data[1];
              if(val < 5){return '#e4e5be';}
              if(val < 13){return '#a7b6aa';}
              if(val < 25){return '#698896';}
              if(val < 40){return '#305d82';}
              return '#023a73';
            }
          }
        }
      },
    ]
  };
  return option;
};

createLineChartOption = (data) =>{
  // data = [{date:'2017-06-01',attend:5,quit:1},
  //   {date:'2017-06-02',attend:1,quit:5},
  //   {date:'2017-06-03',attend:3,quit:1}];
  let option = {
    backgroundColor: '#404a59',
    title: {
      text: '课程走势图',
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
      data:['上课人数','退课人数'],
      top:30,
      left:'20%',
      textStyle:{
        color:'#fff',
      },
    },
    xAxis: [{
      type: 'category',
      data: data.map((obj)=>(obj.date)),
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    },],
    yAxis: [{
      type: 'value',
      name: '人数',
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
      name: '上课人数',
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
      data: data.map((obj)=>(obj.attend)),
      zlevel:5,

    },{
      name: '退课人数',
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
      data: data.map((obj)=>(obj.quit)),
      zlevel:5,

    }]
  };
  return option;
};

createCourseScoreOption = (scores) => {
  // data = [{name:'t1',value:'500'},
  //   {name:'t2',value:'600'},
  //   {name:'t3',value:'333'},
  //   {name:'t4',value:'332'},
  //   {name:'t5',value:'612'}];


  let data=[{name:'0-40',value:0},
    {name:'40-50',value:0},
    {name:'50-60',value:0},
    {name:'60-70',value:0},
    {name:'70-80',value:0},
    {name:'80-90',value:0},
    {name:'90-100',value:0}];
  let data2=[{name:'退课/旷考',value:0},{name:'不合格',value:0},{name:'合格',value:0}];
  scores.forEach((obj)=>{
    let score = parseFloat(obj.score).toFixed(2);
    if(score==0){data2[0].value++;return;}
    if(score<40){console.log(score);data[0].value++;data2[1].value++;return;}
    if(score<50){data[1].value++;data2[1].value++;return;}
    if(score<60){data[2].value++;data2[1].value++;return;}
    if(score<70){data[3].value++;data2[2].value++;return;}
    if(score<80){data[4].value++;data2[2].value++;return;}
    if(score<90){data[5].value++;data2[2].value++;return;}
    data[6].value++;data2[2].value++;
  });

  data = data.reverse();

  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    title: [{
      text: '课程质量分析',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '课程成绩分布图',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '学生分布比例图',
      x: '80%',
      top:'10%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    }],
    grid: [{
      top: 60,
      width: '50%',
      containLabel: true,
      left:50,
    }],
    xAxis: [{
      type: 'value',
      splitLine: {
        show: false

      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      name:"人数",
    }],
    yAxis: [{
      type: 'category',
      show:true,
      data: data.map((obj)=>(obj.name)),
      axisLabel: {
        interval: 0,
        rotate: 30,
      },
      splitLine: {
        show: false
      },
      axisLine:{
        lineStyle:{color:'#fff'},
      },
      zlevel:10,
      name:"成绩",
    }],
    series: [{
      type: 'bar',
      stack: 'chart',
      z: 3,
      label: {
        normal: {
          position: 'right',
          show: true,
          formatter: '{c}人',
        }
      },
      itemStyle: {
        normal:{
          color:'#9fdabf',
        },
      },
      data: data.map((obj)=>(obj.value)),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['80%', '45%'],
      data: data2,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      }
    }]
  };
  return option;
};