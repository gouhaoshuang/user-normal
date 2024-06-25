<template>
  <van-nav-bar
      title="标题"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
      @click-right="searchClick"
  >
    <template #right>
      <van-icon name="search" size="18"/>
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
import {useRouter} from "vue-router";


let router = useRouter();
const onClickLeft = () => {
  router.push("/")
}

const searchClick = () => {
  router.push("/search");
}

const active = ref("index");



</script>
<style scoped>

</style>