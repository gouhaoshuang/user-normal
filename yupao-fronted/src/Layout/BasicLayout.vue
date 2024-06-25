<template>
  <van-nav-bar
      title="标题"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
  >
    <template #right>
      <van-icon name="search" size="18" @click="searchClick" />
    </template>
  </van-nav-bar>

  <RouterView/>
  <van-tabbar route >
    <van-tabbar-item icon="home-o" to="/" name="index">首页</van-tabbar-item>
    <van-tabbar-item icon="friends-o" to="/team" name="team">团队</van-tabbar-item>
    <van-tabbar-item icon="setting-o" to="user" name="user">个人</van-tabbar-item>
  </van-tabbar>

</template>
<script setup lang="ts">
import {ref} from "vue";
import {Toast} from "vant";
import Index from "../pages/index.vue";
import team from "../pages/team.vue";
import User from "../pages/user.vue";
const onClickLeft = () => alert('左');
const onClickRight = () => alert("右");

const active = ref("index");



</script>
<style scoped>

</style>