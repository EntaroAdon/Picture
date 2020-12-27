<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">素材中国下载平台</span>
        <div style="display: flex;align-items: center;margin-right: 7px">

            <span class="el-dropdown-link home_userinfo" style="margin-right: 20px"v-if="user.roleType != '会员'">
                <el-popover v-if="user.roleType != '会员'"
                    placement="bottom"
                    width="200"
                    trigger="click"
                style="text-align: center">
                    <span>微信扫码支付</span>
                    <img src="../img/copy.jpg" width="200px" />
                    <el-button type="primary" class="el-dropdown-link home_userinfo" style="margin-right: 20px" @click="ismember" >
                      充值成功
                    </el-button>
                <el-button  slot="reference">加入会员</el-button>
              </el-popover>
             </span>
          <span class="el-dropdown-link home_userinfo" style="margin-right: 20px" @click="ismember" v-else>
              会员到期时间：{{user.endTime}}
             </span>
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center">
              {{user.name}}
              <i><img :src="'http://localhost:8089/user/findById?id='+user.id"
                      style="width: 40px;height: 40px;margin-right: 5px;margin-left: 5px;border-radius: 40px"/></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item :to="{ path: '/UserHost' }">
                <router-link tag="a" target="_blank" :to="{path:'/UserHost'}"  style="text-decoration: none">
                  个人中心
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-main>
          <sysvue></sysvue>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
  import sysvue from "./system/system.vue"
  export default{
    components:{
      sysvue
    },
    mounted: function () {

    },
    methods: {
      ismember(){
        this.$confirm('确定加入会员, 确定请继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var param  ={id:this.$store.state.user.id}
          this.postRequest("/user/ismember" , param).then(resp=> {
            if (resp && resp.status == 200) {
              var  data = resp.data;
              this.$message({type: data.returnValue, message: data.message});
            }
          })
        }).catch(() => {
        });
      },
      handleCommand(cmd){
        var _this = this;
        if (cmd == 'logout') {
          this.$confirm('注销登录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            //_this.getRequest("/logout");
            _this.$store.commit('logout');
            _this.$router.replace({path: '/'});
          }).catch(() => {
            _this.$message({
              type: 'info',
              message: '取消'
            });
          });
        }
      }
    },
    data(){
      return {
        isDot: false
      }
    },
    computed: {
      user(){
        console.log(this.$store.state.user);
        return this.$store.state.user;
      },
      routes(){
        return this.$store.state.routes
      }
    }
  }
</script>
<style>
  .home-container {
    height: 100%;
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
  }

  .home-header {
    background-color: #20a0ff;
    color: #333;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: content-box;
    padding: 0px;
  }

  .home-aside {
    background-color: #ECECEC;
  }

  .home-main {
    background-color: #fff;
    color: #000;
    text-align: center;
    margin: 0px;
    padding: 0px;;
  }

  .home_title {
    color: #fff;
    font-size: 22px;
    display: inline;
    margin-left: 8px;
  }

  .home_userinfo {
    color: #fff;
    cursor: pointer;
  }

  .home_userinfoContainer {
    display: inline;
    margin-right: 20px;
  }

  .el-submenu .el-menu-item {
    width: 180px;
    min-width: 175px;
  }
</style>
