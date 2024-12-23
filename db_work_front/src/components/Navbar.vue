<template>
  <div class="Navbar">
    <el-row class="row">
    <el-col class="content">
      <H1 class="logo">OnlineBookStore</H1>
      <!-- 根据用户是否登录来显示不同的菜单 -->
      <Menu_Logged v-if="isLoggedIn" />
      <Menu_Unlogged v-else />
    </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref } from 'vue';
import Menu_Logged from "@/components/Menu_Logged.vue";
import Menu_Unlogged from "@/components/Menu_Unlogged.vue";

export default {
  components: {
    Menu_Logged,
    Menu_Unlogged
  },
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
  height: 80px;
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
}
</style>

