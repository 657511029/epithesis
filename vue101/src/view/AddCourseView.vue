<template>
<div class="add-warp">
   <div class="add-container">
     <div class="add-title">
       <span>
         创建课程
       </span>
     </div>
     <div class="add-form">
       <el-form :model="addCourseForm"
       :rules="rules"
       ref="addCourseForm"
       class="add-form-container"
       autocomplete="new-password">
         <el-form-item prop="courseName">
           <el-input type="text" placeholder="课程名称" v-model="addCourseForm.courseName" autocomplete="new-password"></el-input>
         </el-form-item>
         <el-form-item prop="introduction">
           <el-input type="text" placeholder="课程介绍" v-model="addCourseForm.introduction" autocomplete="new-password"></el-input>
         </el-form-item>
         <el-form-item prop="courseInstitution">
           <el-input type="text" placeholder="课程单位" v-model="addCourseForm.courseInstitution" autocomplete="new-password"></el-input>
         </el-form-item>
       </el-form>
     </div>
   </div>
</div>
</template>

<script>
export default {
  name: 'AddCourseView',
  data () {
    return {
      addCourseForm: {
        courseName: '',
        introduction: '',
        courseInstitution: '',
        chooseName: '',
        displayUrl: '',
        backgroundUrl: ''
      },
      rules: {
        courseName: [{required: true, message: '请输入课程名称', trigger: 'blur'}],
        introduction: [{required: true, message: '请输入课程介绍', trigger: 'blur'}],
        courseInstitution: [{required: true, message: '请输入课程单位', trigger: 'blur'}],
        chooseName: [{required: true, message: '请输入课程方向', trigger: 'blur'}]
      }
    }
  }
}
</script>

<style scoped>
.add-warp{
  padding-top: 50px;
  background-color: #ffffff;
  overflow: hidden;
  width: 1300px;
  min-height: 2400px;
}
.add-container{
  margin: 0 auto;
  width: 1200px;
}
.add-title{
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #dbdce0;
  padding-bottom: 4px;
  padding-right: 17px;
}
.add-title span{
  font-weight: 500;
  color: #202124;
  font-size: 16px;
}
</style>
