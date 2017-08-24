


//http://gallery.echartsjs.com/preview.html?c=calendar-effectScatter&v=1
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
      text: '180日学生活跃表',
      subtext: '学生单日活跃等于单日上课课时数',
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
      yearLabel: {
        formatter: '{start} - {end}',
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

//http://gallery.echartsjs.com/editor.html?c=xB15ybNwlb
createMoneyLineChartOption = (data) =>{
  // data = [{date:'2017-06-01',day:5,money:133},
  //   {date:'2017-06-02',day:1,money:24},
  //   {date:'2017-06-03',day:3,money:82}];
  let option = {
    backgroundColor: '#404a59',
    title: {
      text: '学生日付费图',
      subtext: '每天学生上的课时记录下来',
      left: 'center',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    dataZoom: [{ // 这个dataZoom组件，默认控制x轴。
      type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
      start: 10, // 左边在 10% 的位置。
      end: 60 // 右边在 60% 的位置。
    }, { // 这个dataZoom组件，也控制x轴。
      type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
      start: 10, // 左边在 10% 的位置。
      end: 60 // 右边在 60% 的位置。
    }],
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
    legend: {
      data: ['课时数', '消费额'],
      textStyle:{color:'#ffffff'},
      top:30,
      left:200,
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
      name: '课时数',
      min: 0,
      max: 10,
      axisLabel: {
        formatter: '{value} '
      },
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    }, {
      type: 'value',
      name: '消费额',
      min: 0,
      max: 250,
      axisLabel: {
        formatter: '{value}元'
      },
      axisLine: {
        lineStyle: {
          color: '#ffffff'
        }
      },
    }],
    series: [{
      name: '课时数',
      type: 'line',
      smooth:true,
      showSymbol:false,
      itemStyle:{
        normal:{color:"#fce630"}
      },
      data: data.map((obj)=>(obj.day)),
      zlevel:5,

    },
      {
        name: '消费额',
        type: 'line',
        yAxisIndex:1,
        smooth: true,
        showSymbol:false,
        symbol: 'emptyCircle',
        itemStyle:{
          normal:{color:"#91c7ae"}
        },
        lineStyle: {
          normal:{color:"#91c7ae"}
        },
        areaStyle:{
          normal:{
          }
        },
        data: data.map((obj)=>(obj.money)),
      }]
  };
  return option;
};

//http://gallery.echartsjs.com/editor.html?c=xSJ6sf-n-W
createCoursePreferOption = (data) => {
  // data = [{name:'t1',value:'500'},
  //   {name:'t2',value:'600'},
  //   {name:'t3',value:'333'},
  //   {name:'t4',value:'332'},
  //   {name:'t5',value:'612'}];

  data = data.sort((o2,o1)=>(o1.value-o2.value));

  option = {
    backgroundColor: '#404a59',
    tooltip: {},
    textStyle:{
      color:'#fff',
    },
    legend:{
      data:['计算机','艺术','农业','历史','经济','其他','文学','管理'],
      x:'85%',
      top:'10%',
      textStyle:{
        color:"#fff",
      },
    },
    title: [{
      text: '课程偏好分析',
      x: '50%',
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
      subtext: '分析学生的选课偏好',
    },{
      text: '课程活跃度统计图',
      x: '30%',
      bottom:20,
      textAlign: 'center',
      textStyle:{
        color:'#fff',
      },
    },{
      text: '课程活跃度比例图',
      x: '70%',
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
      name:"活跃课时数",
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
      name:"课程类别",
    }],
    series: [{
      type: 'bar',
      stack: 'chart',
      z: 3,
      label: {
        normal: {
          position: 'right',
          show: true,
          formatter: '{c}',
        }
      },
      itemStyle: {
        normal:{
          color:'#f37570',
        },
      },
      data: data.map((obj)=>(obj.value)),

    }, {
      type: 'pie',
      radius: [0, '30%'],
      center: ['70%', '45%'],
      data: data.filter((obj)=>(obj.value != 0)),
      label:{
        normal:{
          formatter:'{b}:{d}%',
        }
      }
    }]
  };
  return option;
};


