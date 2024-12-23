<template>
  <div class="info-box">
    <el-form>
      <el-form-item label="用户名">
        <el-input v-model="username" disabled></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="password" disabled></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="email" disabled></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="phone" disabled></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="address" disabled></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary">修改信息</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script lang="ts" setup>

</script>
<style scoped>

</style>
