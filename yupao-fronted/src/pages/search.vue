<template>
<!--  <form action="/">-->


    <van-search
        v-model="searchText"
        show-action
        autofocus="autofocus"
        placeholder="请输入搜索标签"
        @search="onSearch"
        @cancel="onCancel"
    />
<!--  </form>-->
  <van-divider content-position="left">已选标签</van-divider>
  <div v-if="activeIds.length === 0">请选择标签</div>

  <van-row gutter="16">
    <van-col v-for="tag in activeIds">
      <van-tag closeable size="small" type="primary" @close="doClose(tag)" style="margin-bottom:10px">
        {{ tag }}
      </van-tag>
    </van-col>
  </van-row>
  <van-tree-select
      v-model:active-id="activeIds"
      v-model:main-active-index="activeIndex"
      :items="tagList"
  />
</template>


<script setup lang="ts">
import {ref} from 'vue';

const activeIds = ref([]);
const activeIndex = ref("");

let searchText = ref("")
const originTagList = [{
  text: '性别',
  children: [
    {text: '男', id: '男'},
    {text: '女', id: '女'},
    {text: '嬲', id: '嬲'},
  ],
}, {
  text: '年级',
  children: [
    {text: '大一', id: '大一'},
    {text: '大二', id: '大二'},
    {text: '大三', id: '大三'},
    {text: '大四', id: '大四'},
    {text: '大五', id: '大五'},
    {text: '大六', id: '大六'},
  ],
}];
let tagList = ref(originTagList);


const onSearch = () =>{
  tagList.value = originTagList.map(item=>{
    const tempChildren = [...item.children];
    const tempParentTag = {...item};
    tempParentTag.children = tempChildren.filter(item => item.text.includes(searchText.value));
    return tempParentTag;
  })
}
const onCancel = () => {
  searchText.value = '';
  tagList.value = originTagList;
}
const doClose = (tag:string) => {
  activeIds.value = activeIds.value.filter(item => {
    return item !== tag;
  })
}
</script>

<style scoped>

</style>