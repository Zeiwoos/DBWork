<template xmlns="http://www.w3.org/1999/html">
  <Navbar />
  <div class="main-body">
    <div class="register-block">
      <!-- Logo -->
      <div class="logo-box">
        <p class="logo">OnlineBookStore</p>
      </div>
      <!-- 动态渲染表单输入项 -->
      <div v-for="(item, index) in inputItems" :key="index" class="input-box">
        <span class="character">{{ item.label }}</span>
        <el-input v-model="item.v_model.value"  style="width: 240px" :placeholder="item.placeholder" />
      </div>
      <div v-for="(item, index) in secretItems" :key="index" class="input-box">
        <span class="character">{{ item.label }}</span>
        <el-input v-model="item.v_model.value"  style="width: 240px" :placeholder="item.placeholder" type="password" show-password />
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
          <span>已有账号？</span>
          <el-link :underline="false" href="#" target="_blank">登录</el-link>
        </span>
      </div>

      <!-- 注册按钮 -->
      <div>
        <el-button type="primary" class="register-button" @click="handleRegister">
          <p class="button-font">注册</p>
        </el-button>
      </div>
    </div>

    <!-- 右侧图片 -->
    <div class="img-box">
      <el-image class="img" :src="url" fit="cover" />
      <div class="img-character-box">
        <p class="img-character-1">All You Want Is in</p>
        <p class="img-character-2">OnlineBookStore</p>
      </div>
    </div>
  </div>
</template>


<script>
import { defineComponent, ref, onMounted} from "vue";
import Navbar from "@/components/Navbar.vue";
import logo from "@/assets/image/logo.webp";
import { register} from '@/api/Customer'

export default defineComponent({
  components: { Navbar },
  setup() {
    // 定义输入项及其模型
    const input_name = ref('');
    const input_address = ref('');
    const input_email = ref('');
    const input_phone = ref('');
    const input_password = ref('');
    const input_password_again = ref('');
    const regulation_checked = ref(false);

    // 动态生成输入项数据
    const inputItems = [
      { label: "用户名称", v_model: input_name, placeholder: "Please input Username" },
      { label: "地址", v_model: input_address, placeholder: "Please input Address" },
      { label: "邮箱", v_model: input_email, placeholder: "Please input Email" },
      { label: "电话号码", v_model: input_phone, placeholder: "Please input phone-number" },
    ];
    const secretItems = [
      { label: "密码", v_model: input_password, placeholder: "Please input password" },
      { label: "再次输入密码", v_model: input_password_again, placeholder: "Please input password again" },
    ];

    const url = logo;

    // 注册按钮的处理函数
    const handleRegister = async () => {
      // 检查是否同意服务协议
      if (!regulation_checked.value) {
        return alert("请先同意服务协议！");
      }

      // 检查密码是否一致
      if (input_password.value !== input_password_again.value) {
        return alert("两次输入的密码不一致！");
      }

      // 调用 register API，将用户输入的数据传递给 API
      try {
        const response = await register({
          customerName: input_name.value,
          email: input_email.value,
          phone: input_phone.value,
          address: input_address.value,
          password: input_password.value,
        });
        console.log(response)
        // 注册成功后的处理逻辑
        if (response.data.code === 1) {
          alert("注册成功！");
          localStorage.setItem('isLoggedIn', 'true');
          localStorage.setItem('customerId', response.data.data.customerID);
          console.log(response.data)
          window.location.href = 'http://localhost:5173/';
        } else {
          alert("注册失败！" + response.message);
        }
      } catch (error) {
        console.error("注册请求失败：", error);
        alert("请求失败，请重试！");
      }
    };

    return {
      inputItems,
      secretItems,
      regulation_checked,
      url,
      handleRegister,
    };
  },
});
</script>

<style scoped>
.main-body{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
  gap: 20px;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}
.register-block{
  display: flex;
  justify-content: center;
  flex-direction: column;
  gap: 20px;
  margin: 150px auto auto 200px;
}
.input-box{
  display: flex;
}
.character{
  width: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.logo{
  font-size: 32px;
  color: #409EFF;
  font-style: italic;
  font-weight: bold;
}
.logo-box{
  display: flex;
  align-items: center;
  justify-content: center;
}
.register-button{
  width: 100%;
  height:40px;
  background-color: #409EFF;
}
.mix-character{
  display: flex;
  justify-content: left;
  align-items: center;
}
.button-font{
  color: white;
  font-size: large;
}
.img-box{
  margin-right: 0;
  height: 100%;
  width: auto;
}
.img{
  height:100%;
  width: 50vw;
  display: flex;
  position: relative;
}
.img-character-box{
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.img-character-1{
  font-size: 24px;
  color: #409EFF;
  font-weight: bold;
  font-style: italic;
}
.img-character-2{
  font-size: 32px;
  color: white;
  font-weight: bold;
  font-style: italic;
}
</style>
