<template>
  <div id="app">
    <div>
      <router-view></router-view>
    </div>
    <FloatingCart />
  </div>
</template>
<script setup>
import {RouterLink,RouterView} from "vue-router"
import FloatingCart from "@/components/FloatingCart.vue";
</script>
<style>

</style>

