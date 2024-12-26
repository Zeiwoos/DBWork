<template>
  <div class="Navbar">
    <el-row class="row">
      <el-col class="content">
        <h1 class="logo">OnlineBookStore</h1>
        <!-- 根据用户是否登录来显示不同的菜单 -->
        <Menu_Logged v-if="isLoggedIn" />
        <Menu_Unlogged v-else />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import Menu_Logged from "@/components/Menu_Logged.vue";
import Menu_Unlogged from "@/components/Menu_Unlogged.vue";

export default {
  components: {
    Menu_Logged,
    Menu_Unlogged
  },
  setup() {
    // 初始化 isLoggedIn 状态
    const isLoggedIn = ref(false);

    // 在组件挂载时获取登录状态
    onMounted(() => {
      const loggedInStatus = localStorage.getItem('isLoggedIn');
      const id = localStorage.getItem('customerId');
      isLoggedIn.value = loggedInStatus === 'true'; // 如果返回的值为 'true'，表示用户已登录
    });

    return {
      isLoggedIn
    };
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

.row {
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
