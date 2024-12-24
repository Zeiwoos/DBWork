<template>
  <div :class="tops ? 'navbar' : 'navbar-top'">
    <!-- 购物车图标 -->
      <el-tooltip class="tip" effect="dark" content="购物车" placement="left">
        <el-badge :value="cartCount" class="badge">
          <a @click="showPurchase"><el-icon><ShoppingCart /></el-icon></a>
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

    <!-- 购物车详情对话框 -->
    <el-dialog
        :visible.sync="showCart"
        title="购物车"
        width="50%"
        @close="clearCart"
    >
      <el-table :data="cartItems" style="width: 100%">
        <el-table-column label="商品名称" prop="name"></el-table-column>
        <el-table-column label="单价" prop="price"></el-table-column>
        <el-table-column label="数量" prop="quantity"></el-table-column>
        <el-table-column label="总价" prop="total"></el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showCart = false">取消</el-button>
        <el-button type="primary" @click="checkout">结算</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {CaretTop, CoffeeCup, Service, ShoppingCart} from "@element-plus/icons-vue";
import QrImage from '@/assets/image/qrcode.png'

export default {
  name: 'RightBar',
  components: {CoffeeCup, CaretTop, Service, ShoppingCart},
  data () {
    return {
      showQR:false,
      tops:false,
      heights:window.innerHeight,
      scrolltop:'',
      showCart: false,  // 控制购物车详情对话框的显示
      cartItems: [],  // 存储购物车商品数据
      cartCount: 0,  // 购物车商品数量
      QrImage:QrImage
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
  destroyed () {
    window.removeEventListener('scroll', this.scrollToTop)
  },
  methods:{
    showQRCode(){//显示二维码
      this.showQR = true
    },
    hideQRCode(){//隐藏二维码
      this.showQR = false
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
  }
}
</script>
style部分
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
.navbar-top{
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
.navbar a:last-child{
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 15px;
  border-radius: 0px 0px 10px 10px;
}
.navbar a:first-child{
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
.navbar-top a:last-child{
  color: rgb(0, 0, 0);
  text-decoration: none;
  width: 100%;
  height: 55px;
  display: block;
  font-size: 30px;
  border-radius: 0px 0px 10px 10px;
}
.navbar-top a:first-child{
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
.qrShow{
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
/* 购物车详情对话框样式 */
.el-dialog {
  border-radius: 10px;
}

.el-dialog .el-table {
  margin-top: 20px;
}

.el-dialog .el-button {
  margin-left: 10px;
}
</style>
