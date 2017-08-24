createCalendarOption = (data) => {
  //data = [['2017-06-01','5'],['2017-06-02','1'],['2017-06-03','10']];
  let nowDate = new Date();
  let beforeDate = new Date(new Date().getTime()-1000*60*60*24*179);
  let now = parseDate(nowDate);
  let before = parseDate(beforeDate);
  let option = {
    backgroundColor: '#404a59',
    title: {
      top: 30,
      text: '180日教师活跃表',
      subtext: '教师单日活跃等于单日上课课时数',
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
      range: [before, now],
      splitLine: {
        show: true,
        lineStyle: {
          color: '#000',
          width: 4,
          type: 'solid',
        },
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
              if(val == 0){return '#e4e5be';}
              if(val == 1){return '#a7b6aa';}
              if(val == 2){return '#698896';}
              if(val == 3){return '#305d82';}
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
      text: '教师绩效分析',
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
      data:['上课收入','退课金额','总金额'],
      top:30,
      left:'20%',
      textStyle:{
        color:'#fff',
      },
      selected:{
        '总金额':false,
      }
    },
    dataZoom: [{ // 这个dataZoom组件，默认控制x轴。
      type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
      start: 50, // 左边在 10% 的位置。
      end: 100, // 右边在 60% 的位置。
    }],
    xAxis: [{
      type: 'category',
      name: '时间',
      data: data.map((obj)=>(obj.date)),
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    },],
    yAxis: [{
      type: 'value',
      name: '收入',
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
      name: '上课收入',
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
      zlevel:3,

    },{
      name: '退课金额',
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

    },{
      name: '总金额',
      type: 'line',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#d67853"}
      },
      areaStyle:{
        normal:{
        }
      },
      data: data.map((obj)=>(parseInt(obj.quit)+parseInt(obj.attend))),
      zlevel:0,

    }]
  };
  return option;
};

createBarChartOption = (data) =>{
  // data = [{date:'2017-06-01',attend:5,quit:1},
  //   {date:'2017-06-02',attend:1,quit:5},
  //   {date:'2017-06-03',attend:3,quit:1}];
  let option = {
    backgroundColor: '#404a59',
    title: {
      text: '教学满意度分析',
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
      data:['上课率','缺课率'],
      top:30,
      left:'20%',
      textStyle:{
        color:'#fff',
      },
    },
    dataZoom: [{ // 这个dataZoom组件，默认控制x轴。
      type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
      start: 50, // 左边在 10% 的位置。
      end: 100, // 右边在 60% 的位置。
    }],
    xAxis: [{
      type: 'category',
      name: '时间',
      data: data.map((obj)=>(obj.date)),
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    },],
    yAxis: [{
      type: 'value',
      min:0,
      max:1,
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
      name: '上课率',
      type: 'bar',
      stack:'1',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#8dc1a9"}
      },
      areaStyle:{
        normal:{
        }
      },
      data: data.map((obj)=>(parseFloat(obj.attend)/(parseFloat(obj.attend)+parseFloat(obj.quit))).toFixed(2)),
      zlevel:3,
    },{
      name: '缺课率',
      type: 'bar',
      stack:'1',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#dd6b66"}
      },
      areaStyle:{
        normal:{
        }
      },
      data: data.map((obj)=>(parseFloat(obj.quit)/(parseFloat(obj.attend)+parseFloat(obj.quit))).toFixed(2)),
      zlevel:5,

    }]
  };
  return option;
};

createCourseIncomeOption = (data) => {
  // data = [{name:'t1',total:'1100',average:'500'},];
  data = data.sort((o1,o2)=>(o1.total-o2.total));
  names = [];
  data.forEach((obj)=>{
    names.push(obj.name);
    obj.value = obj.total;
  });
  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:[{
      data:['日均收入','总收入'],
      x:'40%',
      bottom:20,
      textStyle:{
        color:"#fff",
      },
    },{
      data:names,
      x:'85%',
      top:'10%',
      textStyle:{
        color:"#fff",
      },
    }],
    title: [{
      text: '课程收入分析',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '课程收入排行',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '课程收入比例图',
      x: '70%',
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
      name:"课程收入",
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
      name:"课程名称",
    }],
    series: [{
      type: 'bar',
      name:'总收入',
      z: 3,
      label: {
        normal: {
          position: 'right',
          show: true,
          formatter: '{c}元',
        }
      },
      itemStyle: {
        normal:{
          color:'#dd6b66',
        },
      },
      data: data.map((obj)=>(obj.total)),

    },{
      type: 'bar',
      name:'日均收入',
      z: 3,
      label: {
        normal: {
          position: 'right',
          show: true,
          formatter: '{c}元',
        }
      },
      itemStyle: {
        normal:{
          color:'#9fdabf',
        },
      },
      data: data.map((obj)=>((obj.average*1).toFixed(2))),
    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['70%', '55%'],
      data: data,
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      }
    }]
  };
  return option;
};