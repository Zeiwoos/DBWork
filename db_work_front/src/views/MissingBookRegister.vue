<template xmlns="http://www.w3.org/1999/html">
  <Navbar />
  <div class="main-body">
    <div class="register-block">
      <!-- Logo -->
      <div class="logo-box">
        <span class="logo">OnlineBookStore</span>
      </div>
      <!-- 动态渲染表单输入项 -->
      <div v-for="(item, index) in inputItems" :key="index" class="input-box">
        <span class="character">{{ item.label }}</span>
        <el-input v-model="item.model.value"  style="width: 240px" :placeholder="item.placeholder" />
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
      <!-- 注册按钮 -->
      <div>
        <el-button type="primary" class="login-button">
          <p class="button-font">提交缺书记录</p>
        </el-button>
      </div>
    </div>

    <!-- 右侧图片 -->
    <div class="img-box">
      <el-image class="img" :src="url" fit="cover" />
      <div class="img-character-box">
        <p class="img-character-1">All You Want Is In</p>
        <P class="img-character-2">OnlineBookStore</P>
      </div>
    </div>
  </div>
</template>


<script>
import { defineComponent, ref } from "vue";
import Navbar from "@/components/Navbar.vue";
import logo from "@/assets/image/logo.webp";

export default defineComponent({
  components: { Navbar },
  setup() {
    // 定义输入项及其模型
    const input_UserID = ref('')
    const input_password = ref('')
    const regulation_checked = ref(false)

    // 动态生成输入项数据
    const inputItems = [
      {label: "用户ID", model: input_UserID, placeholder: "Please input UserID"},

    ];


    const url = logo;

    return {
      inputItems,
      regulation_checked,
      url,
    };
  },
});
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
  margin: 150px auto auto 200px;
}

.input-box {
  display: flex;
}

.character {
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  letter-spacing: 2px;
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
