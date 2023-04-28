<template>
<div>
 <div class="experiment-wrap">
   <div class="experiment-header-container">
      <h2 class="experiment-header-title">
        {{experiment.experimentName}}
        <span>
          {{browseTime}}
        </span>
     </h2>
   </div>
   <div class="experiment-body">
     <div class="body-left">
       <div class="experiment-content-router"  style="width: 580px">
         <div v-bind:class="{experimentContentRouterActive: this.componentNext === 'Description',experimentContentRouterNoActive: this.componentNext != 'Description'}" @click="toDescription">
           实验描述
         </div>
         <div v-bind:class="{experimentContentRouterActive: this.componentNext === 'Answer',experimentContentRouterNoActive: this.componentNext != 'Answer'}" @click="toAnswer">
           参考答案
         </div>
         <div v-bind:class="{experimentContentRouterActive: this.componentNext === 'Discuss',experimentContentRouterNoActive: this.componentNext != 'Discuss'}" @click="toDiscuss">
           讨论区
         </div>
       </div>
       <div class="experiment-content-router-content" style="background-color:white;width: 580px;height: calc(100vh - 202px);overflow: auto;">
         <component :is = "componentNext" :parentId="experimentId"></component>
         <router-view/>
       </div>
       <div class="body-left-footer">
         <div class="body-left-footer-textArea">
             <input id="inputId" placeholder="发表评论" type="text"  v-model="message" autocomplete="new-password">
             <el-button class="body-left-footer-button" @click="addDiscuss">
               发送
             </el-button>
         </div>
       </div>
     </div>
     <div class="body-right">
      <div class="code-container">
        <div class="body-right-title">
          代码文件(java)
        </div>
        <div class="code">
          <codemirror
            class="code-codemirror"
            ref="cmEditor"
            :value="code"
            :options="cmOptions"
            @ready="onCmReady"
            @focus="onCmFocus"
            @input="onCmCodeChange"
          />
        </div>
      </div>
       <div class="code-result">
         <div class="code-result-header">
           <span>
             测试结果
           </span>
         </div>
         <div class="code-result-body">
           <span>
             {{result}}
           </span>
         </div>
       </div>
       <div class="code-run">
         <div class="code-run-container">
           <div class="code-run-container-left">
             <span>
               只有通过全部数据集的测试才算通关实验
             </span>
           </div>
           <div class="code-run-container-right">
             <button>
               评测
             </button>
           </div>
         </div>
       </div>
     </div>
   </div>
 </div>
</div>
</template>

<script>
import Answer from '../components/Experiment/Answer.vue'
import Description from '../components/Experiment/Description.vue'
import Discuss from '../components/Experiment/Discuss.vue'

import 'codemirror/mode/clike/clike.js'
// 主题css
import 'codemirror/theme/icecoder.css'

// require active-line.js
import 'codemirror/addon/selection/active-line.js'
// closebrackets
import 'codemirror/addon/edit/closebrackets.js'
import {auto} from 'html-webpack-plugin/lib/chunksorter'
// import base style
// import 'codemirror/lib/codemirror.css'
// import more codemirror resource...
export default {
  name: 'ExperimentView',
  components: {
    // Codemirror,
    Description,
    Answer,
    Discuss
  },
  data () {
    return {
      result: '未开始评测',
      code: '',
      cmOptions: {
        tabSize: 4, // tabsize默认为4
        styleActiveLine: true,
        lineNumbers: true,
        line: true,
        mode: 'text/x-java', // 选择代码语言，我这里选的sql
        extraKeys: {'Ctrl': 'autocomplete'}, // 自动提示配置
        lineWrapping: true, // 自动换行
        theme: 'icecoder', // 主题根据需要自行配置
        viewportMargin: 15
      },
      componentNext: 'Description',
      experimentId: '',
      message: '',
      newDate: '', // 浏览时长初始值为 0
      browseTime: 0,
      clearTimeSet: null,
      experiment: {
        experimentName: '',
        description: '',
        example: '',
        answer: ''
      },
      startTime: {
        startY: 0,
        startM: 0,
        startD: 0,
        startH: 0,
        startMinute: 0,
        startSecond: 0
      }
    }
  },
  created () {
    this.experimentId = this.$route.query.experimentId
    // this.formatDateTime(new Date())
  },
  computed: {
    codemirror () {
      return this.$refs.cmEditor.codemirror
    }
  },
  mounted () {
    this.getExperiment()
    this.setTime()
    // 设置codemirror高度为600
  },
  beforeDestroy () {
    clearInterval(this.clearTimeSet) // 离开页面后清除定时器
  },

  methods: {
    // formatDateTime: function (date) {
    //   this.startTime.startY = date.getFullYear()
    //   this.startTime.startM = date.getMonth() + 1
    //   this.startTime.startM = this.startTime.startM < 10 ? ('0' + this.startTime.startM) : this.startTime.startM
    //   this.startTime.startD = date.getDate()
    //   this.startTime.startD = this.startTime.startD < 10 ? ('0' + this.startTime.startD) : this.startTime.startD
    //   this.startTime.startH = date.getHours()
    //   this.startTime.startH = this.startTime.startH < 10 ? ('0' + this.startTime.startH) : this.startTime.startH
    //   this.startTime.startMinute = date.getMinutes()
    //   this.startTime.startMinute = this.startTime.startMinute < 10 ? ('0' + this.startTime.startMinute) : this.startTime.startMinute
    //   this.startTime.startSecond = date.getSeconds()
    //   this.startTime.startSecond = this.startTime.startSecond < 10 ? ('0' + this.startTime.startSecond) : this.startTime.startSecond
    // },
    // formatDateTime2: function (date) {
    //   let y = date.getFullYear()
    //   let m = date.getMonth() + 1
    //   m = m < 10 ? ('0' + m) : m
    //   let d = date.getDate()
    //   d = d < 10 ? ('0' + d) : d
    //   let h = date.getHours()
    //   h = h < 10 ? ('0' + h) : h
    //   let minute = date.getMinutes()
    //   minute = minute < 10 ? ('0' + minute) : minute
    //   let second = date.getSeconds()
    //   second = second < 10 ? ('0' + second) : second
    //   let str = ''
    //   if ((y - this.startTime.startY) > 0) {
    //     str += (y - this.startTime.startY) + '-'
    //   }
    //   if ((m - this.startTime.startM) > 0) {
    //     str += (m - this.startTime.startM) + '-'
    //   }
    //   if ((d - this.startTime.startD) > 0) {
    //     str += (d - this.startTime.startD) + '-'
    //   }
    //   return str + (h - this.startTime.startH) + ':' + (minute - this.startTime.startMinute) + ':' + (second - this.startTime.startSecond)
    // },
    // setTime () {
    //   // 设置定时器
    //   this.clearTimeSet = setInterval(() => {
    //     this.newDate = this.formatDateTime2(new Date())
    //     console.log(this.newDate, '时长累计')
    //   }, 1000)
    // },
    formatDateTime: function (date) {
      let y = date.getFullYear()
      let m = date.getMonth() + 1
      m = m < 10 ? ('0' + m) : m
      let d = date.getDate()
      d = d < 10 ? ('0' + d) : d
      let h = date.getHours()
      h = h < 10 ? ('0' + h) : h
      let minute = date.getMinutes()
      minute = minute < 10 ? ('0' + minute) : minute
      let second = date.getSeconds()
      second = second < 10 ? ('0' + second) : second
      return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
    },
    onCmReady (cm) {
      console.log('the editor is readied!', cm)
      this.$refs.cmEditor.codemirror.setSize(auto, '500px')
    },
    onCmFocus (cm) {
      console.log('the editor is focused!', cm)
    },
    onCmCodeChange (newCode) {
      console.log('this is new code', newCode)
      this.code = newCode
    },
    toDescription: function () {
      this.componentNext = 'Description'
    },
    toAnswer: function () {
      this.componentNext = 'Answer'
    },
    toDiscuss: function () {
      this.componentNext = 'Discuss'
    },
    setTime: function () {
      this.clearTimeSet = setInterval(() => {
        this.browseTime++
      }, 1000)
    },
    getExperiment: function () {
      this.$axios.post('http://localhost:8080/api/lookExperiment', {
        'experimentId': this.experimentId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.experiment = resp.data.experiment
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取章节信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          }
        })
        .catch(error => {
          if (error.response) {
            console.log(error.response)
            let message = error.response.data.message
            this.$message({
              message: '获取章节信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    addDiscuss: function () {
      if (document.getElementById('inputId').value === '' || document.getElementById('inputId').value === null) {
        alert('评论不能为空')
      } else {
        this.$axios.post('http://localhost:8080/api/addDiscuss', {
          'userId': sessionStorage.getItem('userId'),
          'experimentId': this.experimentId,
          'message': this.message,
          'time': this.formatDateTime(new Date())
        })
          .then(resp => {
            if (resp.status === 200) {
              console.log(resp)
              document.getElementById('inputId').value = ''
              this.message = ''
            } else {
              let message = resp.data.message
              this.$message({
                message: '发表评论失败! ' + message,
                type: 'warning',
                duration: 1500
              })
            }
          })
          .catch(error => {
            if (error.response) {
              console.log(error.response)
              let message = error.response.data.message
              this.$message({
                message: '发表评论失败! ' + message,
                type: 'warning',
                duration: 1500
              })
            } else {
              console.log(error)
              this.$message.error('发生错误！')
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.experiment-wrap{
  width: 1350px;
  /*height: calc(100vh);*/
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  /*overflow: hidden;*/
}
.experiment-header-container{
  display: flex;
  /*flex-flow: row nowrap;*/
  align-items: center;
  height: 72px;
  background: #05101a;
  padding: 0 15px;
  color: #fff;
  position: relative;
  z-index: 9;
}
.experiment-body{
  height: calc(100vh - 72px);
  width: 1350px;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  background-color: #fafafa;
}
.experiment-header-title{
  flex: 1 0 auto;
  text-align: center;
  font-size: 16px;
  color: #fff;
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  margin: 0;
}
.body-left{
  /*padding-top: 60px;*/
  width: 580px;
  /*flex: 2 0 auto;*/
  position: relative;
  /*height: 685px;*/
  background-color: #fafafa;
  margin-left: 20px;
}
.experiment-content-router{
  width: 100%;
  transform: translate(0px, 0px);
  position: relative;
  display: flex;
  transition: transform 0.3s;
  height: 76px;
  align-items: center;
  border-top: 1px solid #f3f3f3;
  text-align: center;
  border-bottom: 1px solid #ddd;
}
.experiment-content-router div{
  width: 162px;
  height: 76px;
  background: transparent;
  position: relative;
  display: inline-flex;
  align-items: center;
  font-size: 16px;
  outline: none;
  cursor: pointer;
  text-align: center;
  padding-left: 40px;
}
.experiment-content-router div:hover{
  color: #2572e6;
}
.experimentContentRouterActive{
  color: #0152d9;
  text-shadow: 0 0 0.25px currentcolor;
  border-bottom: 2px solid #0152d9;
}
.body-left-footer{
  position: relative;
  background: #fff;
  width: 100%;
  box-shadow: 0px -3px 5px 0px rgba(76, 172, 255, 0.2);
}
.body-left-footer-textArea{
  display: flex;
  flex-flow: row nowrap;
  padding: 10px 20px;
  align-items: center;
}
.body-left-footer-textArea input{
  border-radius: 15px;
  border: 1px solid #eaeaea;
  background-color: #fff;
  overflow: hidden;
  height: 32px;
  padding: 0 16px;
  line-height: 32px;
  outline: none;
  flex: 1 0;
  margin-right: 10px;
}
.body-left-footer-button{
  color: rgb(255, 255, 255);
  cursor: pointer;
  display: inline-block;
  border: none;
  padding: 0 12px;
  color: #fff;
  background: #0152d9;
  letter-spacing: 1px;
  text-align: center;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  border-radius: 2px;
  font-weight: 400;
}
.body-right{
  width: 750px;
  position: relative;
  height: 100%;
  background: #111c24;
  display: flex;
  flex-direction: column;
}
.code-container{
  height: 70%;
  background: #111c24;
  position: relative;
}
.body-right-title{
  height: 30px;
  background: #111c24;
  padding: 5px 10px;
  display: flex;
  color: #bfbfbf;
  flex-flow: row nowrap;
  align-items: center;
  position: relative;
  z-index: 10;
}
.code{
  height: 500px;
}
.code-codemirror{
  font-size : 20px;
  line-height : 150%;
}
.code-result{
  height: calc(30% - 60px);
  border-top: 1px solid #19527b;
  border-bottom: 1px solid #222c34;
  position: relative;
  display: flex;
  flex-direction: column;
  background: #111c24;
  overflow: hidden auto;
  color: #f4f1ed;
}
.code-result-header{
  width: 100%;
  height: 34px;
  padding: 0 25px;
  line-height: 34px;
  color: #f4f1ed;
  display: flex;
  flex-flow: row nowrap;
  justify-content: space-between;
  align-items: center;
  background-color: #111c24;
  border-bottom: 1px solid #222c34;
}
.code-result-body{
  padding-top: 20px;
  padding-left: 40px;
  font-size: 14px;
  overflow: auto;
}
.code-result-header span{
  font-size: 12px;
  position: relative;
  display: block;
  cursor: pointer;
  padding: 0 25px;
}
.code-run{
  position: relative;
  background: #05101a;
  height: 51px;
}
.code-run-container{
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
  color: #747a7f;
  padding: 0 15px;
  height: 100%;
}
.code-run-container-left{
  flex: 2 1 auto;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
}
.code-run-container-left span{
  font-size: 12px;
  flex: 3 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-right: 15px;
}
.code-run-container-right{
  justify-content: flex-end;
  flex: 2 1 auto;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
}
.code-run-container-right button{
  min-width: 72px;
  background: #0152d9;
  color: #fff;
  font-weight: bold;
  border: 0px solid transparent;
  padding: 0 12px;
  letter-spacing: 1px;
  outline: none;
  text-align: center;
  font-size: 14px;
  height: 30px;
  line-height: 30px;
  border-radius: 3px;
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
  flex: 0 0 auto;
}
</style>
