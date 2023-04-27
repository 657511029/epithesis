<template>
<div class="discuss-wrap">
  <div class="discuss-container">
    <div class="discuss-container-item" v-for="(item) in discussList.slice(start,end)" :key="item.id">
      <a>
        <img alt="用户头像" height="50" v-bind:src="item.userPic" width="50">
      </a>
      <section>
        <div class="discuss-user-info">
          <span style="margin-right: 15px;color: #999;">
            {{item.username}}
          </span>
          <span style="color: #ccc;margin-right: 15px;">
            {{item.time}}
          </span>
        </div>
        <div class="discuss-user-message">
          <p>
            {{item.message}}
          </p>
        </div>
      </section>
    </div>
  </div>
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
</template>

<script>
export default {
  name: 'Discuss',
  props: ['parentId'],
  data () {
    return {
      experimentId: 0,
      totalPage: 1,
      currentPage: 1, // 当前页数 ，默认为1
      pageSize: 6, // 每页显示数量
      discussNumber: 0,
      discussList: []
    }
  },
  created () {
    this.experimentId = this.parentId
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
  mounted () {
    this.getDiscussList()
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
    },
    getDiscussList: function () {
      // this.discussList = [
      //   {
      //     id: 0,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 1,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 2,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 3,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 4,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 5,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   },
      //   {
      //     id: 6,
      //     userPic: 'static/userPic/userPic.jpg',
      //     username: '黄一雄',
      //     time: '2023-1-1 1:12:23',
      //     message: 'import matplotlib.pyplot as plt\n' +
      //       'xt,yt=5000，3000\n' +
      //       'plt.plot(xt,yt,\'ro\')\n' +
      //       'plt.grid(\'on\')\n' +
      //       'plt.axis([1,5000,1,3000]) \n' +
      //       'plt.show()'
      //   }
      // ]
      this.$axios.post('http://localhost:8080/api/lookDiscussList', {
        'experimentId': this.experimentId
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.discussList = resp.data.discussList
            this.discussNumber = this.discussList.length
            this.totalPage = Math.ceil(this.discussNumber / this.pageSize)
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取讨论区信息失败! ' + message,
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
              message: '获取讨论区信息失败! ' + message,
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
</script>

<style scoped>
.discuss-wrap{
  overflow-y: auto;
  flex: 1 1;
}
.discuss-container{
  box-sizing: border-box;
  word-break: break-word;
}
.discuss-container-item{
  display: flex;
  flex-flow: row nowrap;
  padding: 15px;
  border-bottom: 1px solid #e3e3e3;
  overflow: hidden;
}
.discuss-container-item a{
  color: #1890ff;
  background-color: transparent;
}
.discuss-container-item a img{
  border-style: none;
  vertical-align: middle;
  overflow-clip-margin: content-box;
  overflow: clip;
}
.discuss-container-item section{
  margin-left: 15px;
  width: 100%;
  flex: 1 1 auto;
  display: block;
}
.discuss-user-info{
  display: flex;
  flex-flow: row wrap;
  align-items: center;
  color: #999;
  line-height: 16px;
  font-size: 12px;
}
.discuss-user-message{
  margin: 4px 0;
  word-break: break-word;
  word-wrap: break-word;
}
.discuss-user-message p{
  margin-bottom: 0 !important;
}
.pageBox{
  padding-top: 20px;
  padding-bottom: 20px;
  text-align: center;
  font-size: 20px;
}
.left{
  padding: 5px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
}
.right{
  padding: 5px;
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
  padding: 5px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
.noRight{
  padding: 5px;
  display: inline-block;
  text-align: center;
  border: 1px solid rgba(195, 207, 224, 0.5);
  background-color: rgba(248, 249, 252, 0.5);
  color: rgba(0, 0, 0, 0.25);
  pointer-events: none;
}
</style>
