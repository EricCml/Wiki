<template>
  <a-layout id="components-layout-demo-top-side-2">
    <a-layout-header class="header">
      <div class="logo"/>
      <a-menu
          theme="dark"
          mode="horizontal"
          v-model:selectedKeys="selectedKeys1"
          :style="{ lineHeight: '64px' }"
      >
        <a-menu-item key="1">
          nav 1
        </a-menu-item>
        <a-menu-item key="2">
          nav 2
        </a-menu-item>
        <a-menu-item key="3">
          nav 3
        </a-menu-item>
      </a-menu>
    </a-layout-header>
    <router-view/>
    <a-layout-footer style="text-align: center">
      甲蛙电子书
    </a-layout-footer>
  </a-layout>
</template>

<style>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}
</style>