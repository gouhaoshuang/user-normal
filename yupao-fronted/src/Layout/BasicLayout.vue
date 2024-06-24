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

  <div id="content">
    <template v-if="active === 'index'">
      <Index />
    </template>
    <template v-if="active === 'team'">
      <team/>
    </template>
    <template v-if="active === 'user'">
      <user/>
    </template>
  </div>
  <van-tabbar v-model="active">
    <van-tabbar-item icon="home-o" name="index">首页</van-tabbar-item>
    <van-tabbar-item icon="friends-o" name="team">团队</van-tabbar-item>
    <van-tabbar-item icon="setting-o" name="user">个人</van-tabbar-item>
  </van-tabbar>

</template>
<script setup lang="ts">
import {ref} from "vue";
import Index from "../pages/index.vue";
import team from "../pages/team.vue";
import User from "../pages/user.vue";
// const onClickLeft = () => history.back();
// const onClickRight = () => history.back();
// const searchClick = () => history.back();

const active = ref("index");

</script>
<style scoped>

</style>