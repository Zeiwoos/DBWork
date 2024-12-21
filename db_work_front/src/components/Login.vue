<template>
  <el-container>
    <el-header>
      <h2>用户登录</h2>
    </el-header>
    <el-main>
      <el-form :model="form" ref="formRef" label-width="80px">
        <el-form-item label="用户名" :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitLogin">登录</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'Login',
  setup() {
    const form = ref({
      username: '',
      password: ''
    })

    const submitLogin = () => {
      if (!form.value.username || !form.value.password) {
        return
      }
      // 提交登录
      this.$message.success(`欢迎，${form.value.username}`)
    }

    return {
      form,
      submitLogin
    }
  }
}
</script>
