package cn.zhsite.service.impl;

import cn.zhsite.dao.*;
import cn.zhsite.model.*;
import cn.zhsite.model.Date;
import cn.zhsite.service.InitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class InitServiceImpl implements InitService{

    @Resource
    CollegeDAO collegeDAO;
    @Resource
    TeacherDAO teacherDAO;
    @Resource
    CourseDAO courseDAO;
    @Resource
    AttendDAO attendDAO;
    @Resource
    StudentDAO studentDAO;
    @Resource
    MemberDAO memberDAO;
    @Resource
    PaybackDAO  paybackDAO;
    @Resource
    PaymentDAO paymentDAO;
    @Resource
    QuitDAO quitDAO;
    @Resource
    DailyDAO dailyDAO;
    @Resource
    DateDAO dateDAO;

    @Override
    public void init() {
        //initCollege();
        //initStudent();
        //fakeAttend();
        //initIncome();
        initDate();
    }

    private void initDate(){
        LocalDate localDate = LocalDate.of(2013,1,1);

        LocalDate afterDate = LocalDate.of(2020,1,1);

        while(localDate.isBefore(afterDate)){
            Date date = new Date();
            date.setDate(localDate);
            date.setYear(localDate.getYear());
            date.setMonth(localDate.getMonthValue());
            TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
            date.setWeek(localDate.get(weekOfYear));
            date.setDay(localDate.getDayOfMonth());

            dateDAO.insert(date);
            localDate = localDate.plusDays(1);
        }

    }


    private void initIncome(){
        List<Attend> attendList = attendDAO.getAll();
        List<Daily> dailyList = new ArrayList<Daily>(100000);
        int count=0;
        for(Attend attend:attendList){
            Course course = attend.getCourse();
            Integer courseId = course.getId();
            Integer collegeId = course.getCollegeId();
            Integer teacherId = course.getTeacherId();
            Integer studentId = attend.getStudentId();
            String[] columns = {"studentId","courseId"};
            Integer[] values = {studentId,courseId};
            List<Quit> quitList = quitDAO.getAllBy(columns,values);
            boolean hasQuit = !quitList.isEmpty();


            LocalDate pointer = course.getFromDate();
            LocalDate endAttend = hasQuit?quitList.get(0).getCreateDate():course.getToDate();



            while (pointer.isBefore(endAttend) || pointer.isEqual(endAttend)){
                Daily daily = new Daily();
                daily.setCollegeId(collegeId);
                daily.setTeacherId(teacherId);
                daily.setCourseId(courseId);
                daily.setStudentId(studentId);
                daily.setDate(pointer);
                daily.setYear(pointer.getYear());
                daily.setMonth(pointer.getMonthValue());
                TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
                daily.setWeek(pointer.get(weekOfYear));

                daily.setIncome((int) course.getMoneyPerDay());

                dailyList.add(daily);
                pointer = pointer.plusDays(1);
            }

            while (pointer.isBefore((course.getToDate())) || pointer.isEqual(course.getToDate())){
                Daily daily = new Daily();
                daily.setCollegeId(collegeId);
                daily.setTeacherId(teacherId);
                daily.setCourseId(courseId);
                daily.setStudentId(studentId);
                daily.setDate(pointer);
                daily.setYear(pointer.getYear());
                daily.setMonth(pointer.getMonthValue());
                TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
                daily.setWeek(pointer.get(weekOfYear));

                daily.setIncome(0);

                dailyList.add(daily);
                pointer = pointer.plusDays(1);
            }
            if(dailyList.size()>90000){
                dailyDAO.insertAll(dailyList);
                count+=dailyList.size();
                dailyList.clear();
                System.out.println("已经输出"+count+"行");
            }
        }
        System.out.println("结束");
    }

    private void fakeAttend(){
        List<Course> courseList = courseDAO.getAll();
        List<Student> studentList = studentDAO.getAll();


        int studentSum = studentList.size();

        for (int j=0;j<courseList.size();j++){
            Course course = courseList.get(j);

            int attendNum = (int) (30+ gaussianRandom()*7);
            attendNum = (int) (attendNum*2.5);
            double baseQuitRate = (gaussianRandom()+2)/8;
            baseQuitRate = baseQuitRate<0?0:baseQuitRate;
            int quitNum = (int) (attendNum*baseQuitRate);

            double avgScore = Math.random()*20+60+gaussianRandom()*5;
            avgScore= avgScore>100?100:avgScore;
            double scoreSpan = avgScore>50?100-avgScore:avgScore;

            LocalDate start = course.getFromDate();
            LocalDate end = course.getToDate();

            String area = course.getCollege().getArea();
            String area2 = course.getCollege().getArea2();

            List<Integer> attendIds = randomInts(attendNum,studentSum-50,1);

            for(int i=0;i<attendNum;i++){
                Integer studentId = attendIds.get(i);
                Student student = studentList.get(studentId);

                if(!area2.equals(student.getArea2())){
                    if(Math.random()<0.7){
                        continue;
                    }

                }else if(!area.equals(student.getArea())){
                    if(Math.random()<0.5){
                        continue;
                    }
                }


                LocalDate attendDate = randomDate(start.minusDays(28),start.minusDays(1));
                Double score = 0.0;

                if(i>=quitNum){
                    score = avgScore +scoreSpan*gaussianRandom()/2;
                    score = score>100?100:score;
                    score = score<10?15+gaussianRandom()*3:score;

                }


                Attend attend = new Attend();
                attend.setCourseId(course.getId());
                attend.setStudentId(student.getId());
                attend.setScore(score);
                attend.setCreateDate(attendDate);
                attendDAO.insert(attend);

                if(i<quitNum) {
                    Quit quit = new Quit();
                    quit.setCourseId(course.getId());
                    quit.setStudentId(student.getId());
                    quit.setCreateDate(randomDate(start.plusDays(1),end.minusDays(1)));
                    quitDAO.insert(quit);

                }
            }
        }

    }

    private void initStudent(){

        for(int i=0;i<6000;i++){
            Student student = new Student();
            student.setSex(randomSex());
            student.setName(randomName(student.getSex()));
            student.setArea(randomSArea());
            student.setArea2(getProvince(student.getArea()));
            student.setAge(randomSAge());
            student.setCreateDate(randomDate(LocalDate.of(2014,6,1),LocalDate.of(2014,12,28)));
            studentDAO.insert(student);


        }
    }

    private void initCollege(){
        String[] collegesName = {"择善","启趣","草图","华夏","启智",
                "厚博","创想","斐奥","阳禾","新西方",
                "新南方","新北方","习根","图德","晨树",
                "以文","美习","点鸣","默森","地瓜"};
        for(int i=0;i<20;i++){
            initCollege(collegesName[i]);
        }
    }

    private void initCollege(String name){
        College college = new College();
        college.setName(name);
        college.setArea(randomCArea());
        college.setArea2(getProvince(college.getArea()));
        LocalDate d = randomDate(LocalDate.of(2014,6,1),LocalDate.of(2014,12,28));
        college.setCreateTime(LocalDateTime.of(d.getYear(),d.getMonth(),d.getDayOfMonth(),0,0));
        college.setCreateDate(d);
        collegeDAO.insert(college);
        int teacherNum = (int) (10+Math.random()*15);
        for(int i=0;i<teacherNum;i++){
            initTeacher(college);
        }
    }

    private void initTeacher(College college){
        Teacher teacher = new Teacher();
        String sex = randomSex();
        teacher.setSex(sex);
        teacher.setName(randomName(sex));
        teacher.setLevel(randomTeacherLevel());
        teacher.setAge(randomTAge());
        teacher.setCollegeId(college.getId());
        teacher.setArea(randomTArea());
        teacher.setArea2(getProvince(teacher.getArea()));
        teacher.setType(randomCourseType());
        teacher.setCreateDate(randomDate(college.getCreateDate().plusDays(1),LocalDate.of(2015,1,1)));

        teacherDAO.insert(teacher);
        int courseNum = (int) (3+Math.random()*7);

        for(int i=0;i<courseNum;i++){
            initCourse(college,teacher);
        }

    }

    private void initCourse(College college, Teacher teacher){
        Course course = new Course();
        LocalDate fromDate = randomDate(LocalDate.of(2015,1,2),LocalDate.of(2017,7,1));
        int days = (int) (25+Math.random()*100+gaussianRandom()*10);
        days = days<30?30:days;
        LocalDate toDate = fromDate.plusDays(days);
        String type = randomCourseType();
        if(!type.equals(teacher.getType())){
            if(gaussianRandom()<0){
                type = teacher.getType();
            }
        }
        String name = randomCourseName(type);
        int money = randomMoney((int) (toDate.toEpochDay()-fromDate.toEpochDay()));

        course.setCollegeId(college.getId());
        course.setTeacherId(teacher.getId());
        course.setTeacher(teacher);
        course.setMoney((double) money);
        course.setName(name);
        course.setType(type);
        course.setFromDate(fromDate);
        course.setToDate(toDate);
        course.setDays((int) (course.getToDate().toEpochDay()-course.getFromDate().toEpochDay()));
        course.setMoneyPerDay(course.getMoney()/course.getDays());

        courseDAO.insert(course);

    }

    private static String randomCArea(){
        return random(area1);
    }
    private static String randomTArea(){
        return random(area2);
    }
    private static String randomSArea(){
        return random(area3);
    }

    private static LocalDate randomDate(){
        long d = LocalDate.now().toEpochDay()-LocalDate.of(2016,1,1).toEpochDay();
        return LocalDate.of(2016,1,1).plusDays((long) (d*Math.random()));
    }

    private static LocalDate randomDate(LocalDate startDate){
        long d = LocalDate.now().toEpochDay()-startDate.toEpochDay();
        return startDate.plusDays((long) (d*Math.random()));
    }

    private static LocalDate randomDate(LocalDate startDate,LocalDate endDate){
        long d = endDate.toEpochDay()-startDate.toEpochDay();
        return startDate.plusDays((long) (d*Math.random()));
    }

    private static Integer randomTAge(){
        Random r = new Random();
        double d = r.nextGaussian();
        double age = 40+d*6;
        age = age<20?20:age;
        age = age>80?80:age;
        return (int)age;
    }

    private static Integer randomSAge(){
        Random r = new Random();
        double d = r.nextGaussian();
        double age = 25+d*5;
        age = age<8?8:age;
        age = age>50?50:age;
        return (int)age;
    }

    private static String randomSex(){
        Random r = new Random();
        double d = r.nextGaussian();
        if(d<0){
            return "男";
        }
        return "女";
    }

    private static String randomName(String sex){
        String name = firstName.charAt((int) (firstName.length()*Math.random()))+"";
        if(sex.equals("男")){
            name += boy.charAt((int) (boy.length()*Math.random()));
            if(Math.random()<0.5){
                name += boy.charAt((int) (boy.length()*Math.random()));
            }
        }else{
            name += girl.charAt((int) (girl.length()*Math.random()));
            if(Math.random()<0.46){
                name += girl.charAt((int) (girl.length()*Math.random()));
            }
        }
        return name;
    }

    private static String randomCourseType(){
        return random(courseTypes);
    }

    private static String randomCourseName(String type){
        char c = 'A';
        c = (char) (c+(int)(Math.random()*26));
        char d = 'A';
        d = (char) (d+(int)(Math.random()*26));
        return type+c+d;
    }

    private static Integer randomMoney(int days){
        Random r = new Random();
        double d = r.nextGaussian();
        d=d<-5?-5:d;
        d=d>5?5:d;
        return (23+(int)(d*3))*days;
    }

    private static String randomTeacherLevel(){
        double d = Math.random();
        if(d<0.5){
            return "讲师";
        }
        if(d<0.8){
            return "副教授";
        }
        return "教授";
    }

    private static String random(List<Node> list){
        double d = Math.random();
        for(int i=0;i<list.size();i++){
            if(d<list.get(i).rate){
                return list.get(i).value;
            }
        }
        return list.get(0).value;
    }


    private static double[] areaRs = {0.1,0.25,0.33,0.46,0.58,0.77,0.94,1};
    private static double[] areaRs2 = {0.12,0.21,0.32,0.43,0.53,0.70,0.92,1};
    private static double[] areaRs3 = {0.08,0.22,0.30,0.41,0.57,0.75,0.91,1};
    private static String[] areaVs = {"泰州","南京","宁波","苏州","温州","杭州","上海","宿迁"};

    private static List<Node> area1 = nodes(areaRs,areaVs);
    private static List<Node> area2 = nodes(areaRs2,areaVs);
    private static List<Node> area3 = nodes(areaRs3,areaVs);

    private static String getProvince(String area){
        if(area.equals("泰州")||area.equals("南京")||area.equals("苏州")||area.equals("宿迁")){
            return "江苏";
        }else if(area.equals("上海")){
            return "上海";
        }else{
            return "浙江";
        }
    }

    private static double[] courseTypeRs = {0.07,0.33,0.38,0.46,0.51,0.70,0.86,1};
    private static String[] courseTypeVs = {"文学","计算机","历史","管理","农业","艺术","其他","经济"};
    private static List<Node> courseTypes = nodes(courseTypeRs,courseTypeVs);

    private static String firstName = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华" +
            "金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳鲍史唐费廉岑薛雷贺倪汤" +
            "滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成" +
            "戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡凌" +
            "霍虞万支柯咎管卢莫经房裘缪干解应宗宣丁贲邓郁单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊" +
            "甄魏邴松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全班仰秋仲伊宫宁仇栾" +
            "甘钭厉戎祖武符刘姜詹束龙叶幸司韶郜黎薄印宿白怀蒲台鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍" +
            "闻莘党翟谭贡劳姬申冉宰雍桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕" +
            "习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东沃利蔚越隆师巩聂晁";

    private static String girl = "秀筠柔竹霭凝晓欢霄美娜静淑惠珠翠雅芝玉萍红娥春菊兰凤洁梅琳素云" +
            "莲真环雪荣爱妹霞香华慧巧佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛玲芬芳燕彩" +
            "青倩婷姣芸菲寒伊亚娟英蓉眉君琴蕊薇菁梦岚月莺媛艳瑞凡枫宁欣飘育滢馥琰韵融园艺咏卿聪澜" +
            "纯毓悦昭冰爽琬茗羽希婉娴瑾颖露瑶怡婵雁蓓纨仪宜可姬舒影荔枝思丽苑婕馨瑗荷丹";

    private static String boy = "伟刚斌梁栋维启克俊清飞彬全国胜健世广志义兴良海山仁" +
            "波宁贵福生龙元思群豪心邦利学庆磊民友裕河祥才发武勇毅天达安岩中茂进林楠榕风新泽晨" +
            "有坚和彪博诚先敬震振壮会富顺信子杰涛承乐绍功强军松善厚哲江超浩亮政谦亨奇固" +
            "之轮翰朗伯宏言若鸣朋平保东文辉力明永伦翔旭鹏辰士以建家致树炎德行时泰盛雄" +
            "琛钧冠策腾昌成康星光峰航弘";



    private static Node node(double rate,String value){
        return new Node(rate,value);
    }

    private static List<Node> nodes(double[] rates,String[] values){
        List<Node> list = new ArrayList<Node>();
        for(int i=0;i<rates.length;i++){
            list.add(node(rates[i],values[i]));
        }
        return list;
    }

    private static Random random = new Random();

    private static double gaussianRandom(){
        double d = random.nextGaussian();
        d=d<-3?-3:d;
        d=d>3?3:d;
        return d;
    }

    private static List<Integer> randomInts(int count,int size,int begin){
        ArrayList<Integer> list = new ArrayList<Integer>();

        for(int i=0;i<count;i++) {
            for(;;){
                int num = (int) (Math.random()*size)+begin;
                if (!list.contains(num)) {
                    list.add(num);
                    break;
                }
            }
        }
        return list;
    }

    private static class Node{
        public double rate;
        public String value;

        public Node(double rate,String value){
            this.rate = rate;
            this.value = value;
        }
    }

    public static void main(String[] args){
        LocalDate pointer = LocalDate.of(2017,1,31);
        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();

        System.out.println(pointer.getMonthValue());
        System.out.println(pointer.get(weekOfYear));
    }
}
