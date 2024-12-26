<template xmlns="http://www.w3.org/1999/html">
  <Navbar />
  <div class="main-body">
    <div class="register-block">
      <!-- Logo -->
      <div class="logo-box">
        <p class="logo">OnlineBookStore</p>
      </div>

      <!-- 错误提示 -->
      <div v-if="errorMessage" class="error-box">
        <el-alert
            type="error"
            :closable="false"
            :description="errorMessage"
            show-icon
        />
      </div>

      <!-- 动态渲染表单输入项 -->
      <div v-for="(item, index) in inputItems" :key="index" class="input-box">
        <span class="character">{{ item.label }}</span>
        <el-input v-model="item.v_model.value" style="width: 240px" :placeholder="item.placeholder" />
      </div>

      <div v-for="(item, index) in secretItems" :key="index" class="input-box">
        <span class="character">{{ item.label }}</span>
        <el-input v-model="item.v_model.value" style="width: 240px" :placeholder="item.placeholder" type="password" show-password />
      </div>

      <!-- 服务协议 -->
      <div>
        <el-checkbox v-model="regulation_checked">
          <span class="mix-character">
            <span>我已阅读并接受OnlineBookStore</span>
            <el-link :underline="false" href="#" target="_blank">《服务协议》</el-link>
            <span>和</span>
            <el-link :underline="false" href="#" target="_blank">《账户协议》</el-link>
          </span>
        </el-checkbox>
      </div>

      <!-- 已有账号 -->
      <div>
        <span class="mix-character">
          <span>还未注册？</span>
          <el-link :underline="false" href="#" target="_blank">前往注册</el-link>
        </span>
      </div>

      <!-- 注册按钮 -->
      <div>
        <el-button type="primary" class="login-button" @click="handleLogin">
          <p class="button-font">登录</p>
        </el-button>
      </div>
    </div>

    <!-- 右侧图片 -->
    <div class="img-box">
      <el-image class="img" :src=url fit="cover" />
      <div class="img-character-box">
        <p class="img-character-1">All You Want Is in</p>
        <P class="img-character-2">OnlineBookStore</P>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import Navbar from "@/components/Navbar.vue";
import logo from "@/assets/image/logo.webp";
import { login } from '@/api/Customer';  // 导入 login 函数
import { useRouter } from 'vue-router';

// 定义输入项及其模型
const input_UserID = ref('');
const input_password = ref('');
const regulation_checked = ref(false);
const errorMessage = ref('');
const router = useRouter();  // 用于页面跳转

// 动态生成输入项数据
const inputItems = [
  { label: "用户ID", v_model: input_UserID, placeholder: "请输入用户ID" },
];
const secretItems = [
  { label: "密码", v_model: input_password, placeholder: "请输入密码" },
];

// Logo 图片路径
const url = logo;

// 登录逻辑
const handleLogin = async () => {
  // 验证用户ID和密码是否为空
  if (!input_UserID.value || !input_password.value) {
    errorMessage.value = "用户ID或密码不能为空！";
    return;
  }

  if (!regulation_checked.value) {
    return alert("请先同意服务协议！");
  }
  // 创建登录数据对象
  const loginData = {
    customerID: Number(input_UserID.value),  // 将用户ID转换为数字
    password: input_password.value
  };

  // 调用 API 进行登录验证
  try {
    const response = await login(loginData);
    if (response.data.code === 1) {
      // 登录成功，清除错误信息，跳转到主页
      errorMessage.value = "";
      alert("登录成功！");
      localStorage.setItem('isLoggedIn', 'true');
      localStorage.setItem('customerId',input_UserID.value);
      await router.push('/home'); // 跳转到首页或目标页面
    } else {
      // 登录失败，显示错误信息
      errorMessage.value = response.data.msg || "登录失败！";
    }
  } catch (error) {
    // 网络请求失败的处理
    errorMessage.value = "登录请求失败，请稍后再试！";
  }
};
</script>

<style scoped>
.main-body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  gap: 20px;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}

.register-block {
  display: flex;
  justify-content: center;
  flex-direction: column;
  gap: 20px;
  margin: 120px auto auto 200px;
}

.input-box {
  display: flex;
}

.character {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.logo {
  font-size: 32px;
  color: #409EFF;
  font-style: italic;
  font-weight: bold;
}

.logo-box {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.login-button {
  width: 100%;
  height: 40px;
  background-color: #409EFF;
}

.mix-character {
  display: flex;
  justify-content: left;
  align-items: center;
}

.button-font {
  color: white;
  font-size: large;
}

.img-box {
  margin-right: 0;
  height: 100%;
  width: auto;
}

.img {
  height: 100%;
  width: 50vw;
  display: flex;
  position: relative;
}

.img-character-box {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.img-character-1 {
  font-size: 24px;
  color: #409EFF;
  font-weight: bold;
  font-style: italic;
}

.img-character-2 {
  font-size: 32px;
  color: white;
  font-weight: bold;
  font-style: italic;
}
</style>
