<template>
  <Navbar />
  <div class="main-body"></div>
</template>
<script>

import {defineComponent} from "vue";
import Navbar from "@/components/Navbar.vue";

export default defineComponent({
  components: {Navbar}
})
</script>
<style scoped>
.main-body{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 20px;
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}

</style>
