<template>
  <a-layout-header class="header">
    <div class="logo"/>
    <a class="login-menu" v-show="!user.id" @click="showLoginModal">
      <span>登录</span>
    </a>
    <a-popconfirm
        title="确认退出登录?"
        ok-text="是"
        cancel-text="否"
        @confirm="logout()"
    >
      <a class="login-menu" v-show="user.id">
        <span>退出登录</span>
      </a>
    </a-popconfirm>
    <a class="login-menu" v-show="user.id">
      <span>您好：{{ user.name }}</span>
    </a>

    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/user" v-if="user.id">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook" v-if="user.id">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category" v-if="user.id">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
    </a-menu>

    <a-modal
        title="登录"
        v-model:visible="loginModalVisible"
        :confirm-loading="loginModalLoading"
        @ok="login"
    >
      <a-form
          ref="formRef"
          :model="loginUser"
          :label-col="{ span: 6 }"
          :wrapper-col="{ span: 18 }"
          :rules="rules"
      >
        <a-form-item has-feedback label="登录名" name="loginName">
          <a-input v-model:value="loginUser.loginName"/>
        </a-form-item>
        <a-form-item has-feedback label="密码" name="password">
          <a-input v-model:value="loginUser.password" type="password"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </a-layout-header>
</template>

<script lang="ts">
import {computed, defineComponent, ref, reactive, UnwrapRef} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue';
import store from "@/store";
import router from "@/router";
import {RuleObject, ValidateErrorEntity} from 'ant-design-vue/es/form/interface'

declare let hexMd5: any;
declare let KEY: any;

interface LoginUser {
  loginName: string;
  password: string;
}

export default defineComponent({
  name: 'the-header',
  setup() {


    // 登录后保存
    const user = computed(() => store.state.user);

    // 用来登录
    const formRef = ref();
    const loginUser: UnwrapRef<LoginUser> = reactive({
      loginName: "test",
      password: "test123"
    });
    // const loginUser = ref({
    //   loginName: "test",
    //   password: "test123"
    // });
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    // 校验登录名
    let validateName = async (rule: RuleObject, value: string) => {
      if (value === '') {
        return Promise.reject('请输入登录名');
      } else {
        // if (value === '') {
        //   return Promise.reject('Please input the password');
        // }
        return Promise.resolve();
      }
    };

    // 校验密码
    let validatePass = async (rule: RuleObject, value: string) => {
      if (value === '') {
        return Promise.reject('请输入密码');
      } else {
        if (!/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$/.test(value)) {
          return Promise.reject('请输入符合规则的密码');
        }
        return Promise.resolve();
      }
    };

    // 定义校验规则
    const rules = {
      loginName: [{required: true, validator: validateName, trigger: 'change'}],
      password: [{required: true, validator: validatePass, trigger: 'change'}],
    }

    // 登录
    const login = () => {
      formRef.value
          .validate()
          .then(() => {
            console.log("开始登录");
            loginModalLoading.value = true;
            const formPassword = loginUser.password;
            loginUser.password = hexMd5(loginUser.password + KEY);
            axios.post('/user/login', loginUser).then((response) => {
              loginModalLoading.value = false;
              const data = response.data;
              if (data.success) {
                loginModalVisible.value = false;
                message.success("登录成功！");
                store.commit("setUser", data.content);
              } else {
                loginUser.password = formPassword;
                message.error(data.message);
              }
            });
          })
          .catch((error: ValidateErrorEntity<LoginUser>) => {
            console.error(error);
          });
    };

    // 退出登录
    const logout = () => {
      console.log("退出登录开始");
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("退出登录成功！");
          store.commit("setUser", {});
          // 清空表单项
          formRef.value.resetFields();

          // 退出登录后跳回到首页
          router.push('/');
        } else {
          message.error(data.message);
        }
      });
    };

    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      user,
      logout,

      formRef,
      rules,
    }
  }
});
</script>

<style>
.login-menu {
  float: right;
  color: white;
  padding-left: 10px;
}
</style>
