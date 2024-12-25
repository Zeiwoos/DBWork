<template>
  <div class="Navbar">
    <el-row class="row">
      <el-col class="content">
        <h1 class="logo">OnlineBookStore-AdminDashBoard</h1>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    // 假设 isLoggedIn 是从某处获取的登录状态
    const isLoggedIn = ref(false); // 修改为 true 以测试已登录状态
    return {
      isLoggedIn
    }
  }
}
</script>


<style scoped>
.Navbar {
  height: 10%;
  background-color: #409EFF;
  color: white;
  display: flex;
  align-items: center;
  position: fixed;
  top: 0;
  z-index: 1000;
}

.row{
  width: 100vw;
}

.content {
  display: flex;
  align-items: center;
  flex-direction: row;
}

.logo {
  font-size: 32px;
  font-weight: bold;
  margin-left: 20px;
  font-style: italic;
}

</style>

