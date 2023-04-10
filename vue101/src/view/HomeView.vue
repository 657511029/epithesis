<template>
<div class="home-wrap">
<!-- <el-button @click="toLogin">登录</el-button>-->
<!--  <el-button @click="toRegister">注册</el-button>-->
  <div style="background-color: white" class="home-container">
 <div style="height: 1200px; border: 1px solid #eee;">
   <div class="home-recommend">
     <div class="home-recommend-title">
       <span>课程推荐</span>
     </div>
     <div class="home-recommend-content">
       <div class="home-recommend-list">
         <div class="home-recommend-item" v-for="(item) in courseList.slice(0,5)" :key="item.courseId">
           <router-link :to="{path:'/CourseView',query:{courseId:item.courseId}}">
             <div class="course-img">
               <img :src="item.url" alt="">
             </div>
             <div class="course-title">
               <span>{{item.courseName}}</span>
             </div>
             <div class="course-teacher">
               <span class="prev el-icon-traffic-school"></span>
               <span>{{item. courseInstitution}}</span>
             </div>
             <div class="course-info">
          <span class="experimentNumber">
            章节数:{{item.experimentNumber}}</span>
               <span class="studentNumber">选课人数:{{item.studentNumber}}</span>
             </div>
           </router-link>
         </div>
       </div>
     </div>
   </div>
   <div style="background-color: white" class="choose-wrap">
     <div style="background-color: #f6f6f6" class="choose-container">
       <div style="height: 750px; border: 1px solid #eee;padding: 20px">
         <div class="choose-direction">
           <div class="choose-direction-title" style="width: 41px; margin-top: 4px;">方向</div>
           <div class="choose-direction-container">
             <div class="choose-direction-content">
               <div class="choose-direction-list">
                 <span  title="全部" :class="generateClassName(1)" style="color: rgb(0, 0, 0);" @click="changeAllList">全部</span>
                 <span  v-for="(item,index) in chooseList" :class="generateClassName(index + 2)" :key="index" style="color: rgb(0, 0, 0);" @click="changeList(index)" >
                      {{item}}
                 </span>
               </div>
             </div>
           </div>
         </div>
         <div class="choose-direction-course">
           <div class="choose-course-list">
             <div class="choose-course-item" v-for="(item) in directionList.slice(start,end)" :key="item.courseId">
               <router-link :to="{path:'/CourseView',query:{courseId:item.courseId}}">
                 <div class="course-img">
                   <img :src="item.url" alt="">
                 </div>
                 <div class="course-title">
                   <span>{{item.courseName}}</span>
                 </div>
                 <div class="course-teacher">
                   <span class="prev el-icon-traffic-school"></span>
                   <span>{{item. courseInstitution}}</span>
                 </div>
                 <div class="course-info">
          <span class="experimentNumber">
            章节数:{{item.experimentNumber}}</span>
                   <span class="studentNumber">选课人数:{{item.studentNumber}}</span>
                 </div>
               </router-link>
             </div>
           </div>
         </div>
         <div class="pageBox">
           <div class="page">

             <div  v-bind:class="{left: this.currentPage != 1,noLeft: this.currentPage === 1}"  @click="prevPage()">
          <span class="prev" >
            上一页
        </span>
             </div>

             <span v-html="'\u00a0\u00a0\u00a0'"></span>
             <div  v-bind:class="{right: this.currentPage != this.totalPage,noRight: this.currentPage === this.totalPage}" @click="nextPage()">
            <span class="next" >
              下一页
        </span>
             </div>

           </div>
         </div>
       </div>
     </div>
   </div>
 </div>
 </div>
</div>
</template>

<script>
export default {
  name: 'HomeView',
  data: function () {
    return {
      totalPage: 1,
      currentPage: 1, // 当前页数 ，默认为1
      pageSize: 8, // 每页显示数量
      courseNumber: 0,
      courseList: [],
      chooseList: [],
      chooseNumber: 2,
      directionList: [],
      directionList1: []
    }
  },
  mounted () {
    this.courseNumber = 10
    this.totalPage = Math.ceil(this.courseNumber / this.pageSize)
    this.courseList = [
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 0
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 1
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 2
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 3
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 4
      }
    ]
    this.chooseList = [
      '计算机1',
      '计算机2',
      '计算机3',
      '计算机4',
      '计算机5',
      '计算机6',
      '计算机7',
      '计算机8',
      '计算机9',
      '计算机10',
      '计算机11',
      '计算机12',
      '计算机13',
      '计算机14'
    ]
    this.directionList = [
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 0
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 1
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 2
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 3
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 4
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 5
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 6
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 7
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 8
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 9
      }
    ]
    this.directionList1 = [
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 0
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 1
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 2
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 3
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 4
      }
    ]
    this.directionList2 = [
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 0
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 1
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 2
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 3
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 4
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 5
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 6
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 7
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 8
      },
      {
        url: 'static/userPic/userPic.jpg',
        courseName: '计算机基础',
        courseInstitution: '山水城市',
        experimentNumber: 7,
        studentNumber: 110,
        courseId: 9
      }
    ]
  },
  computed: {
    start: function () {
      return this.currentPage * this.pageSize - this.pageSize
    },
    end: function () {
      return this.currentPage * this.pageSize
    }
  },
  methods: {
    toLogin: function () {
      this.$router.push({name: 'LoginView'})
    },
    toRegister: function () {
      this.$router.push({name: 'RegisterView'})
    },
    changeList (index) {
      this.chooseNumber = index + 2
      this.directionList = this.directionList2
      this.courseNumber = 10
      this.currentPage = 1
      this.totalPage = Math.ceil(this.courseNumber / this.pageSize)
    },
    changeAllList () {
      this.chooseNumber = 1
      this.directionList = this.directionList1
      this.courseNumber = 5
      this.currentPage = 1
      this.totalPage = Math.ceil(this.courseNumber / this.pageSize)
    },
    generateClassName (index) {
      if (index === this.chooseNumber) {
        return 'chooseActive'
      } else {
        return 'noChooseActive'
      }
    },
    prevPage () {
      console.log(this.currentPage)
      if (this.currentPage === 1) {
        return false
      } else {
        this.currentPage = this.currentPage - 1
      }
    },
    // 下一页
    nextPage () {
      if (this.currentPage === this.totalPage) {
        return false
      } else {
        this.currentPage = this.currentPage + 1
      }
    }
  }
}
</script>

<style>
.home-wrap{
  background-color: #f6f6f6;
  height: 1300px;
  padding: 30px 100px;
  width: 1158px;
}
.home-recommend{
  height: 380px;
  border-bottom: 1px solid #eaeaea;
  width: 100%;
}
.home-recommend-title{
  text-align: center;
  font-size: 20px;
  padding-top: 30px;
  font-weight: 600;
}
.home-recommend-content{
  display: flex;
  justify-content:center;
  align-items:center;
}
.home-recommend-list{
  height: 250px;
  padding: 50px 50px 20px 50px;
  display: flex;
  flex-wrap: wrap;
}
.home-recommend-item{
  width: 180px;
  margin-right: 18px;
  margin-bottom: 18px;
  height: 210px;
  background: #ffffff;
  box-shadow: 0px 2px 4px 0px #d6dae1;
  border-radius: 7px;
  position: relative;
}

a{
  color: #1890ff;
  background-color: transparent;
  touch-action: manipulation;
  text-decoration: none;
  outline: none;
  cursor: pointer;
  transition: color 0.3s;
}
.course-img{
  overflow: hidden;
  height: 117px;
  border-radius: 7px 7px 0 0;
  position: relative;
}
.course-img img{
  width: 100%;
  transition: all 0.5s;
  border-style: none;
  vertical-align: middle;
  overflow-clip-margin: content-box;
  overflow: clip;
}
.course-img img:hover{
  transform: scale(1.1);
}
.course-title{
  height: 35px;
  font-size: 15px;
  font-weight: 600;
  color: #000000;
  padding: 10px 8px 0px 8px;
  width: 100%;
  display: flex;
  align-items: center;
}
.course-title span{
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 100%;
}
.course-teacher{
  font-size: 12px;
  color: #000f37;
  padding: 0px 8px;
  margin-top: 5px;
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 100%;
}
.course-info{
  height: 24px;
  display: flex;
  align-items: center;
  color: #000f37;
  padding: 0px 8px;
}
.course-info span{
  margin-right: 10px;
  font-size: 12px;
}
.choose-wrap{
  padding: 30px 10px;
  height: 800px;
}
.choose-direction{
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}
.choose-direction-title{
  font-size: 14px;
  color: #9096a3;
  flex-shrink: 0;
  text-align-last: justify;
  margin-right: 12px;
}
.choose-direction-container{
  flex: 1 1;
  position: relative;
}
.choose-direction-content{
  padding-right: 0px;
  max-height: 240px;
  overflow: hidden;
}
.choose-direction{
  margin-top: 50px;
}
.choose-direction-list{
  box-sizing: border-box;
  word-break: break-word;
}
.choose-direction-list span{
  display: inline-block;
  margin-left: 8px;
  margin-bottom: 17px;
  font-size: 14px;
  cursor: pointer;
  padding: 4px 12px;
}
.chooseActive{
  color: #165dff !important;
  border-radius: 2px;
  background: white;
}
.choose-course-list{
  height: 500px;
  padding: 50px 40px 20px 160px;
  display: flex;
  flex-wrap: wrap;
}
.choose-course-item{
  width: 180px;
  margin-right: 18px;
  margin-bottom: 18px;
  height: 210px;
  background: #ffffff;
  box-shadow: 0px 2px 4px 0px #d6dae1;
  border-radius: 7px;
  position: relative;
}
.pageBox{
  text-align: center;
  font-size: 20px;
}
.left{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.right{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.left:hover{
  color: #40a9ff;
  cursor: pointer;
}
.right:hover{
  color: #40a9ff;
  cursor: pointer;
}
.noLeft{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
.noRight{
  font-size: 15px;
  padding: 5px 15px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
</style>
