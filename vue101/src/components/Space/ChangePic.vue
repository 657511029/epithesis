<template>
<div class="item_bock head_p">
  <div class="head_img">
    <img :src="userInfo.avatar"/>
  </div>
  <div class="setting_right" >
    <div class="caption" @click.stop="uploadHeadImg">上传头像</div>
    <div class="caption" @click="modifyAvatar">更改头像</div>
  </div>
  <input type="file" accept="image/*" ref="uploadFile" enctype="multipart/form-data" @change="handleFile" class="hiddenInput"/>
</div>
</template>
<script>
export default {
  name: 'ChangePic',
  data () {
    return {
      userInfo: {
        avatar: ''
      }
    }
  },
  mounted () {
    this.getAvatarUrl()
  },
  methods: {
    modifyAvatar: function () {
      // let config = {
      //   headers: {
      //     'Content-Type': 'multipart/form-data'
      //   }
      // }
      let formData = new FormData()
      // 通过append()方法追加数据
      formData.append('avatarFile', this.$refs.uploadFile.files[0])
      console.log(this.$refs.uploadFile.files[0])
      formData.append('id', sessionStorage.getItem('userId'))
      this.$axios.post('http://localhost:8080/api/modifyUserAvatar', formData)
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            window.location.reload()
          } else {
            let message = resp.data.message
            this.$message({
              message: '更新头像失败! ' + message,
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
              message: '更新头像失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    getAvatarUrl: function () {
      this.$axios.post('http://localhost:8080/api/lookUserInfo', {
        'id': sessionStorage.getItem('userId')
      })
        .then(resp => {
          if (resp.status === 200) {
            console.log(resp)
            this.userInfo.avatar = resp.data.user.avatarUrl
          } else {
            let message = resp.data.message
            this.$message({
              message: '获取个人信息失败! ' + message,
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
              message: '获取个人信息失败! ' + message,
              type: 'warning',
              duration: 1500
            })
          } else {
            console.log(error)
            this.$message.error('发生错误！')
          }
        })
    },
    // 打开图片上传
    uploadHeadImg: function () {
      // this.$el.querySelector('.hiddenInput').click()
      this.$refs.uploadFile.click()
    },
    // 将头像显示
    handleFile: function (e) {
      let $target = e.target || e.srcElement
      let file = $target.files[0]
      let reader = new FileReader()
      reader.onload = (data) => {
        let res = data.target || data.srcElement
        this.userInfo.avatar = res.result
      }
      reader.readAsDataURL(file)
    }
  }
}
</script>

<style scoped>
.item_bock {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height:300px;
  width: 600px;
  padding:150px 150px 150px 150px;
  border-bottom: 1px solid #f7f7f7;
  background: #fff;
}
.head_p {
  height:300px;
}
.head_img{
  height: 300px;
}
.head_img img{
  width:300px;
  height:300px;
  border-radius:150px
}
.setting_right{
  display: inline-block;
  padding: 10px;
  width: 120px;
  height: 130px;
  justify-content: flex-end;
  align-items: center;
}
.hiddenInput{
  display: none;
}
.caption {
  margin-top: 30px;
  color: #8F8F8F;
  font-size: 26px;
  height: 37px;
  text-align: center;
  cursor: pointer;
}

</style>
