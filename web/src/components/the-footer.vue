<template>
  <a-layout-footer style="text-align: center">
    <div>
      边城知识库<span v-show="user.id">，欢迎：{{ user.name }}</span>
    </div>
    <div>
      Copyright © 2021 BianChengCS , All rights reserved.
    </div>

    <div style="width:500px; margin:0 auto; padding:20px 0; text-align:center;">
      <span>
        <img
            src="../assets/gongan.png"
            style="height:20px; width:20px;"/>
        <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42011202002035"
           style="display:inline-block; text-decoration:none; height:20px; line-height:20px;">
            <p style="height:20px; line-height:20px; color:#939393;">
              鄂公网安备42011202002035号
            </p>
        </a>
      </span>
      <span>  •  </span>
      <span>
        <a target="_blank" href="https://beian.miit.gov.cn/"
           style="display:inline-block; text-decoration:none; height:20px; line-height:20px;">
            <p style="height:20px; line-height:20px; color:#939393;">
              湘ICP备2021019370号-1
            </p>
        </a>
      </span>
    </div>

    <!--    <div style="width:400px;margin:0 auto; padding:20px 0;">-->
    <!--      <span>-->
    <!--        <a target="_blank"-->
    <!--           href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=42011202002035"-->
    <!--           class="beian">-->
    <!--          <img-->
    <!--              src="../../dist/image/gongan.png"-->
    <!--              style="float:left;"/>-->
    <!--          <p class="beian"-->
    <!--             style="float:left;">-->
    <!--            鄂公网安备42011202002035号-->
    <!--          </p>-->
    <!--        </a>-->
    <!--      </span>-->
    <!--      <span>-->
    <!--        &lt;!&ndash;        <p class="beian"> • </p>&ndash;&gt;-->
    <!--        <a target="_blank"-->
    <!--           href="https://beian.miit.gov.cn/"-->
    <!--           class="beian">-->
    <!--          <p class="beian">-->
    <!--            湘ICP备2021019370号-1-->
    <!--          </p>-->
    <!--        </a>-->
    <!--      </span>-->
    <!--    </div>-->
  </a-layout-footer>
</template>

<script lang="ts">
import {computed, defineComponent, onMounted} from 'vue';
import store from "@/store";
import {Tool} from "@/util/tool";
import {notification} from 'ant-design-vue';

export default defineComponent({
  name: 'the-footer',
  setup() {
    const user = computed(() => store.state.user);

    let websocket: any;
    let token: any;
    const onOpen = () => {
      console.log('WebSocket连接成功，状态码：', websocket.readyState)
    };
    const onMessage = (event: any) => {
      console.log('WebSocket收到消息：', event.data);
      notification['info']({
        message: '收到消息',
        description: event.data,
      });
    };
    const onError = () => {
      console.log('WebSocket连接错误，状态码：', websocket.readyState)
    };
    const onClose = () => {
      console.log('WebSocket连接关闭，状态码：', websocket.readyState)
    };
    const initWebSocket = () => {
      // 连接成功
      websocket.onopen = onOpen;
      // 收到消息的回调
      websocket.onmessage = onMessage;
      // 连接错误
      websocket.onerror = onError;
      // 连接关闭的回调
      websocket.onclose = onClose;
    };

    onMounted(() => {
      // WebSocket
      if ('WebSocket' in window) {
        token = Tool.uuid(10);
        // 连接地址：ws://127.0.0.1:8880/ws/xxx
        websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/ws/' + token);
        initWebSocket()

        // 关闭
        // websocket.close();
      } else {
        alert('当前浏览器 不支持')
      }
    });

    return {
      user
    }
  }
});
</script>

<!--<style>-->
<!--.beian p {-->
<!--  height: 20px;-->
<!--  line-height: 20px;-->
<!--  margin: 0px 0px 0px 5px;-->
<!--  color: #939393;-->
<!--}-->

<!--.beian a {-->
<!--  display: inline-block;-->
<!--  text-decoration: none;-->
<!--  height: 20px;-->
<!--  line-height: 20px;-->
<!--}-->
<!--</style>-->