<template>
  <el-popover
    :visible="visible"
    placement="top"
    title="购物车"
    :width="300"
    content="this is content, this is content, this is content">
    <div class="cart-popover-content">
      <div v-for="(item, index) in cartItems" :key="index" class="cart-item">
        <div class="item-info">
          <p style="font-weight: bold">{{ item.name }}</p>
          <p>单价：{{ item.price }} RMB</p>
          <div class="quantity-control">
            <el-input-number v-model="item.quantity" :min="1" :max="99" label="数量" size="small"></el-input-number>
            <el-button size="small" type="danger" @click="removeItem(index)">删除</el-button>
          </div>
        </div>
      </div>
      <div class="cart-total">
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
        <a @click="gotop"><el-icon><CaretTop /></el-icon><br><span>顶部</span></a>
        </el-tooltip>
      </div>
    </template>
  </el-popover>
</template>

<script>
import { CaretTop, CoffeeCup, Service, ShoppingCart } from "@element-plus/icons-vue";
import QrImage from '@/assets/image/qrcode.png'

export default {
  name: 'RightBar',
  components: { CoffeeCup, CaretTop, Service, ShoppingCart },
  data() {
    return {
      showQR: false,
      tops: false,
      heights: window.innerHeight,
      scrolltop: '',
      showCart: false,  // 控制购物车详情对话框的显示
      cartItems: [],  // 存储购物车商品数据
      cartCount: 0,  // 购物车商品数量
      QrImage: QrImage,
      visible: false,
    };
  },
  mounted() {
    window.addEventListener('scroll', this.scrollToTop);
    // 模拟添加购物车商品
    this.cartItems = [
      { name: '《数据库系统原理》', price: 99.99, quantity: 1, total: 99.99 },
      { name: '《深入浅出Vue.js》', price: 129.99, quantity: 2, total: 259.98 },
    ];
    this.cartCount = this.cartItems.length;
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
    showQRCode() { // 显示二维码
      this.showQR = true;
    },
    hideQRCode() { // 隐藏二维码
      this.showQR = false;
    },
    // 滚动处理
    scrollToTop() {
      let scrollTop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
      this.scrolltop = scrollTop;
      if (parseInt(this.scrolltop) > (parseInt(this.heights) / 3)) {
        this.tops = true;
      } else {
        this.tops = false;
      }
    },
    gotop() {
      let timer = setInterval(() => {
        document.documentElement.scrollTop = document.body.scrollTop = this.scrolltop - 90;
        if (this.scrolltop === 0) {
          clearInterval(timer);
        }
      }, 15);
    },
    // 显示购物车详情
    showPurchase() {
      this.showCart = true;
    },
    // 删除购物车商品
    removeItem(item) {
      const index = this.cartItems.indexOf(item);
      if (index > -1) {
        this.cartItems.splice(index, 1);
        this.cartCount = this.cartItems.length;
      }
    },
    // 更新商品总价
    updateTotal() {
      this.cartItems.forEach(item => {
        item.total = item.price * item.quantity;
      });
    },
    // 清空购物车 (可选)
    clearCart() {
      this.cartItems = [];
      this.cartCount = 0;
    },
    // 结算 (示例方法，可以根据实际需求处理)
    checkout() {
      alert('结算功能待实现');
      this.showCart = false;
    },
    // 取消支付
    cancelPurchase() {
      this.showCart = false;
    },
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
  border-radius: 0px 0px 10px 10px;
}
.navbar a:first-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 55px;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 10px 10px 0px 0px;
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
  border-radius: 0px 0px 10px 10px;
}
.navbar-top a:first-child {
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 55px;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 10px 10px 0px 0px;
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
