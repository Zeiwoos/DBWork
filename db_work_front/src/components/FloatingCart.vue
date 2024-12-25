<template>
  <el-popover
      :visible="visible"
      placement="top"
      title="购物车"
      :width="300"
      content="this is content, this is content, this is content">
    <div class="cart-popover-content">
      <!-- 当购物车有商品时渲染商品 -->
      <div v-if="cartItems.length > 0">
        <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
          <div class="item-info">
            <p style="font-weight: bold">{{ item.name }}</p>
            <p>单价：{{ item.price }} RMB</p>
            <div class="quantity-control">
              <el-input-number
                  v-model="item.quantity"
                  :min="1"
                  :max="99"
                  label="数量"
                  size="small"
                  @update:modelValue="updateTotal(item)" > <!-- 处理 modelValue 更新 -->
              </el-input-number>
              <el-button size="small" type="danger" @click="removeItem(index)">删除</el-button>
            </div>
          </div>
        </div>
      </div>
      <!-- 当购物车没有商品时显示提示信息 -->
      <div v-else>
        <p>购物车为空</p>
      </div>

      <div class="cart-total" v-if="cartItems.length > 0">
        <p>总价：￥{{ totalPrice }}</p>
        <div class="charge-buttons">
          <el-button @click="cancelPurchase" type="primary" size="small">取消支付</el-button>
          <el-button @click="checkout" type="primary" size="small">提交支付</el-button>
        </div>
      </div>
    </div>
    <template #reference>
      <div :class="tops ? 'navbar' : 'navbar-top'">
        <!-- 购物车图标 -->
        <el-tooltip class="tip" effect="dark" content="购物车" placement="left">
          <el-badge :value="cartCount" class="badge">
            <a @click="visible = !visible"><el-icon><ShoppingCart /></el-icon></a>
          </el-badge>
        </el-tooltip>
        <!-- 客服图标 -->
        <el-tooltip class="tip" effect="dark" content="客服" placement="left">
          <a><el-icon><Service /></el-icon></a>
        </el-tooltip>

        <!-- 二维码显示 -->
        <div class="qrShow right" v-if="showQR">
          <el-image
              style="width: 100px; height: 100px; border-radius: 8px;"
              :src="QrImage"
              fit="cover">
          </el-image>
        </div>
        <a @mouseover="showQRCode" @mouseleave="hideQRCode"><el-icon><CoffeeCup /></el-icon></a>
        <!-- 返回顶部 -->
        <el-tooltip class="item" effect="dark" content="返回顶部" placement="left" v-if="tops">
          <a @click="goTop"><el-icon><CaretTop /></el-icon><br><span>顶部</span></a>
        </el-tooltip>
      </div>
    </template>
  </el-popover>
</template>

<script>
import { CaretTop, CoffeeCup, Service, ShoppingCart } from "@element-plus/icons-vue";
import QrImage from '@/assets/image/qrcode.png';

export default {
  name: 'RightBar',
  components: { CoffeeCup, CaretTop, Service, ShoppingCart },
  props: {
    message: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showQR: false,
      tops: false,
      heights: window.innerHeight,
      scroll_top: '',
      showCart: false,  // 控制购物车详情对话框的显示
      cartItems: [],  // 存储购物车商品数据
      cartCount: 0,  // 购物车商品数量
      QrImage: QrImage,
      visible: false,  // 控制弹出框是否显示
    };
  },
  watch: {
    // 监听父组件传递的 message，当其发生变化时更新购物车内容
    message(newMessage) {
      this.updateCart(newMessage);
    }
  },
  mounted() {
    window.addEventListener('scroll', this.scrollToTop);
    this.updateCart(this.message); // 初始化时更新购物车
  },
  destroyed() {
    window.removeEventListener('scroll', this.scrollToTop);
  },
  computed: {
    totalPrice() {
      return this.cartItems.reduce((total, item) => total + item.total, 0);
    },
  },
  methods: {
    // 更新购物车数据
    updateCart(newMessage) {
      if (newMessage && newMessage.bookID) {
        let existingItem = this.cartItems.find(item => item.bookID === newMessage.bookID);
        if (existingItem) {
          existingItem.quantity += newMessage.num;  // 如果商品已存在，增加数量
        } else {
          this.cartItems.push({
            bookID: newMessage.bookID,
            name: newMessage.title,
            price: newMessage.price,
            quantity: newMessage.num,
            total: newMessage.price * newMessage.num
          });
        }
        this.cartCount = this.countNum();
      }
    },

    // 监听滚动事件，控制显示返回顶部按钮
    scrollToTop() {
      let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      this.scroll_top = scrollTop;
      if (parseInt(this.scroll_top) > (parseInt(this.heights) / 3)) {
        this.tops = true;
      } else {
        this.tops = false;
      }
    },

    // 返回顶部
    goTop() {
      let timer = setInterval(() => {
        document.documentElement.scrollTop = document.body.scrollTop = this.scroll_top - 90;
        if (this.scroll_top === 0) {
          clearInterval(timer);
        }
      }, 15);
    },

    // 显示二维码
    showQRCode() {
      this.showQR = true;
    },

    // 隐藏二维码
    hideQRCode() {
      this.showQR = false;
    },

    // 更新商品总价
    updateTotal(item) {
      item.total = item.price * item.quantity;
    },

    // 删除商品
    removeItem(index) {
      this.cartItems.splice(index, 1);
      this.cartCount = this.countNum();  // 更新购物车商品数量
    },

    // 计算购物车商品数量
    countNum() {
      let num = 0;
      this.cartItems.forEach(item => {
        num += item.quantity;
      });
      return num;
    },

    // 取消支付
    cancelPurchase() {
      this.cartItems = [];  // 清空购物车
      this.cartCount = 0;
    },

    // 提交支付
    checkout() {
      alert('提交支付');
    }
  }
}
</script>

<style scoped>
.navbar {
  text-align: center;
  background-color: white;
  position: fixed;
  top: 68%;
  right: 3%;
  width: 55px;
  height: 220px;
  z-index: 999;
  border-radius: 10px;
}
.navbar-top {
  text-align: center;
  background-color: white;
  position: fixed;
  top: 68%;
  right: 3%;
  width: 55px;
  height: 165px;
  z-index: 999;
  border-radius: 10px;
}
.navbar a {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 30px;
  padding-top: 5px;
}
.navbar a:last-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 15px;
  border-radius: 0 0 10px 10px;
}
.navbar a:first-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 55px;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 10px 10px 0 0;
}
.navbar a:hover {
  background-color: #d9534f;
  color: white;
}
.navbar-top a {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 30px;
  padding-top: 5px;
}
.navbar-top a:last-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 0 0 10px 10px;
}
.navbar-top a:first-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 55px;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 10px 10px 0 0;
}
.navbar-top a:hover {
  background-color: #d9534f;
  color: white;
}
.qrShow {
  position: fixed;
  top: calc(68% + 80px);
  right: calc(3% + 60px);
  z-index: 999;
  background-color: rgb(179, 179, 179);
  width: 110px;
  height: 110px;
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding-top: 5px;
}
.qrShow:after {
  content: "";
  position: absolute;
  width: 0;
  height: 0;
  border: 8px solid;
}
.right:after {
  border-left-color: rgb(179, 179, 179);
  left: 100%;
  top: 50%;
  margin-top: -7px;
}
.cart-popover-content{
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.cart-item{
  display: flex;
  gap: 10px;
  padding: 10px;
  border-bottom: 1px solid #f0f0f0;
}
.cart-total{
  display: flex;
  gap:10px;
  flex-direction: column;
}
.charge-buttons{
  display: flex;
  gap:20px;
  justify-content: center;
  align-items: center;
}
.quantity-control{
  display: flex;
  gap: 20px;
}
</style>
