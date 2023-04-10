<template>
  <div class="search-wrap">
    <div class="search-title">
      <ul class="search-title-list">
        <li>
          <a class="search-tile-list-first">
            实践课程
          </a>
        </li>
      </ul>
    </div>
    <div class="search-content">
      <div class="search-content-number">
        <div class="search-content-number-message">
          <p>
            搜索内容:{{content}}
            <span v-html="'\u00a0\u00a0'"></span>
            共找到结果{{courseNumber}}个
          </p>
        </div>
      </div>
     <ul class="search-content-list">
       <li v-for="(item) in items.slice(start,end)" :key="item.courseId" class="search-content-list-number">
         <router-link  :to="{path:'/CourseView',query:{courseId:item.courseId}}">
           <div class="search-content-list-number-container">
             <div class="search-content-name">
               {{item.courseName}}
             </div>
             <div class="search-content-message">
               {{item. courseInstitution}}
             <span v-html="'\u00a0\u00a0\u00a0'"></span>
               实验数：{{item.experimentNumber}}
               <span v-html="'\u00a0\u00a0\u00a0'"></span>
               学习人数：{{item.studentNumber}}
             </div>
           </div>
         </router-link>
       </li>
     </ul>
      <div class="pageBox">
        <div class="page">

            <div  v-bind:class="{left: this.currentPage != 1,noLeft: this.currentPage === 1}" @click="prevPage()">
          <span class="prev el-icon-arrow-left" >
        </span>
            </div>

          <span v-html="'\u00a0\u00a0\u00a0'"></span>
            <div  v-bind:class="{right: this.currentPage != this.totalPage,noRight: this.currentPage === this.totalPage}" @click="nextPage()">
            <span class="next el-icon-arrow-right" >
        </span>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchView',
  data () {
    return {
      content: 'a',
      totalPage: 1,
      currentPage: 1, // 当前页数 ，默认为1
      pageSize: 1, // 每页显示数量
      courseNumber: 0,
      items: []
    }
  },
  mounted () {
    this.content = this.$route.query.content
    this.courseNumber = 3
    this.totalPage = (this.courseNumber + this.pageSize - 1) / this.pageSize
    this.items = [
      {
        courseName: 'a',
        courseInstitution: 'a',
        experimentNumber: 0,
        studentNumber: 0,
        courseId: 0
      },
      {
        courseName: 'b',
        courseInstitution: 'b',
        experimentNumber: 1,
        studentNumber: 1,
        courseId: 1
      },
      {
        courseName: 'c',
        courseInstitution: 'c',
        experimentNumber: 2,
        studentNumber: 2,
        courseId: 2
      }
    ]
  },
  computed: {
    // 监听当前页curPage的变化，求得当前页的起始行start和末行end，自定更新至v-for
    start: function () {
      return this.currentPage * this.pageSize - this.pageSize
    },
    end: function () {
      return this.currentPage * this.pageSize
    }
  },
  methods: {
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

<style scoped>
.search-wrap{
  height: 100vh;
  width: 1350px;
}
.search-title{
  background-color: #f2f1f1;;
  height: 60px;
}
.search-title-list{
  height: 60px;
}
.search-title-list a{
  height: 60px;
  border-bottom: 2px solid #0152d9;
  color: #0152d9;
  display: block;
  width: 94px;
  text-align: center;
  font-size: 14px;
  line-height: 60px;
}
a:hover{
  color: #40a9ff;
}
li{
  list-style: none;
}
.search-content{
  background-color: #f6f6f6;
  padding: 30px 100px;
}
.search-content-number{
  align-items: center;
}

.search-content-number-message{
  position: relative;
  max-width: 100%;
  min-height: 1px;
}
.search-content-number-message p{
  font-size: 13px;
  color: #888;
  margin: 24px 0;
}
.search-content-list {
  list-style: none;
  display: flex;
  flex-flow: column nowrap;
  padding-left: 0;

}
.search-content-list li{
  display: list-item;
  text-align: -webkit-match-parent;
  background-color: white;
  margin: 10px;
}
a{
  text-decoration: none;
}
router-link-active{
  text-decoration: none;
}
.search-content-list-number-container{
  display: block;
  color: #777;
  background-color: #fff;
  padding: 20px;
  font-size: 16px;
  line-height: 1.6;
}
.search-content-name{
  font-size: 20px;
  font-weight: 700;
  color: #333333;
  padding-top: 10px;
  padding-bottom: 20px;
}
.search-content-message{
  border-top: 1px dashed #eaeaea;
  padding-top: 20px;
  font-size: 13px;
}
.search-content-list-number-container:hover{
  color: #40a9ff;
}
.pageBox{
  text-align: right;
  padding-right: 100px;
  font-size: 20px;
}
.left{
  padding: 10px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.right{
  padding: 10px;
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
  padding: 10px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
.noRight{
  padding: 10px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}

</style>
