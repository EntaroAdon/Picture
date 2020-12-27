<template>
  <div style="margin-top: 10px" v-loading="fullloading">
      <el-header class="home-header">
        <span class="home_title">
          <router-link tag="a" target="_blank" :to="{path:'/home'}" style="color: #ffffff;text-decoration: none;cursor: pointer;">素材中国下载平台</router-link>
        </span>
        <div style="display: flex;align-items: center;margin-right: 7px">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center">
              {{user.name}}
              <i><img :src="'http://localhost:8089/user/findById?id='+user.id"
                      style="width: 40px;height: 40px;margin-right: 5px;margin-left: 5px;border-radius: 40px"/></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <router-link tag="a" target="_blank" :to="{path:'/UserHost'}"  style="text-decoration: none">
                  个人中心
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
    <div style="margin-bottom: 10px;display: flex;">
      <div style="width: 50%;">
        <div style="width: 100%;">
          <img :src="image.url+'img/findById?imgid='+image.id" width="100%" alt="item.name" >
        </div>
        <div style="margin-top: 20px;text-align: right;">
          <div>
              <span class="user-info" style="color: #f62a09;" @click="deletegood(image.id,'g')"  v-if="image.gid != 0">
                <i class="fa fa-heart">已赞</i></span>
            <span class="user-info" style="color: #f62a09;" @click="insertgood(image.id,'g')"  v-else>
                <i class="fa fa-heart-o"></i>点赞</span>
            <span class="user-info" style="color: #f67609;" @click="deletegood(image.id,'f')" v-if="image.fid != 0">
                <i class="fa fa-star"></i>已收藏</span>
            <span class="user-info" style="color: #f67609;"  @click="insertgood(image.id,'f')"  v-else>
                <i class="fa fa-star-o"></i>收藏</span>
            <span class="user-info"  @click="downloadImg(image)" v-if="user.roleType == '会员'">
                <i class="fa fa-download"></i>下载</span>
            <span class="user-info" style="color: #0926f6;"
                  v-clipboard:copy="image.url+'img/findById?imgid='+image.id"
                  v-clipboard:success="onCopy"
                  v-clipboard:error="onError">
                <i class="el-icon-share"></i>分享</span></div>
        </div>
        <div  style="text-align: center;padding: 10px">
          <span>{{image.name}}</span>
        </div>
      </div>
      <div style="width: 50%;">
        <div style="margin-top:20px">
          <el-input type="textarea" v-model="context" style="width: 80%" :rows="4" placeholder="评论区请留言。。。。"></el-input>
          <el-button size="small" type="primary" icon="el-icon-search" @click="submitClick">提交</el-button>
        </div>
        <div style="margin-top:20px;    padding: 0px 4%;">
          <el-row style="margin-bottom: 20px;width: 80%;"  v-if="tableyo">
            <div>
              暂无评论，快来抢沙发啦啦啦啦啦
            </div>
          </el-row>
          <el-row style="border-bottom: 1px solid #190a0a3b" v-for="(item,index) in tableData" :key="item.id" v-else>
            <el-col :span="6" style="padding: 10px;height: 100px;background: #ddd;">
              <el-row>
                <img :src="'http://localhost:8089/user/findById?id='+item.uid"
                     style="width: 40px;height: 40px;margin-right: 5px;margin-left: 5px;border-radius: 40px"/>
              </el-row>
              <el-row>
                {{item.name}}
              </el-row>
            </el-col>
             <el-col :span="18" style="padding: 10px;text-align: left">
               {{item.context}}
             </el-col>
         </el-row>

        </div>
        <el-pagination
          background
          :page-size="3"
          :current-page="currentPage"
          @current-change="currentChange"
          layout="prev, pager, next"
          :total="totalCount">
        </el-pagination>
      </div>
    </div>

  </div>
</template>
<script>
  export default{
    data(){
      return {
        context:'',
        image:{
          id:'',
          url:''
        },
        id:this.$route.query.id,//图片id
        //默认每页数据量
        pagesize: 3,
        tableData: [],
        totalCount: -1,//默认数据总数
        currentPage: 1, //当前页码
        fullloading: false,
        tableyo:true
      }
    },
    mounted: function () {
      this.initData();
      this.initCData(this.currentPage,this.pagesize);
    },
    methods: {
      onCopy: function (e) {
        this.$message({type: 'success', message: "链接复制成功，快去发送给小伙伴吧^^^^^"});
      },
      onError: function (e) {
        this.$message({type: 'success', message: "链接复制失败^^^^^"});
      },
      downloadImg(row){
        var src='data:image/png;base64,'+ row.picture;
        this.srcImg = src; //图片回显
        var link = document.createElement('a');
        link.href = src;
        link.download =row.name+ ".jpg";
        link.click();
      },
      initData(){
        var _this = this;
        var uid = this.$store.state.user.id;
        var param  ={id:_this.id,uid:uid}
        this.postRequest("/img/findListById" , param).then(resp=> {
          if (resp && resp.status == 200) {
            _this.image = resp.data[0];
            console.log(_this.image)
          }
        })
      },
      initCData(pageNum, pageAmount){
        var _this = this;
        //var uid = this.$store.state.user.id;
        var param  ={imid:_this.id,pageNum:pageNum, pageAmount:pageAmount}
        this.postRequest("/comment/findList" , param).then(resp=> {
          if (resp && resp.status == 200) {
            _this.tableData = resp.data.pagedata;
            _this.tableyo = _this.tableData.length ==0;
            console.log(_this.tableData)
            _this.totalCount = resp.data.total;
          }
        })
      },
      //提交评论
      submitClick(){
        var _this = this;
        var uid = this.$store.state.user.id;
        var param  ={imid:_this.id,uid:uid,context:_this.context}
        this.postRequest("/comment/insert" , param).then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            this.$message({type: data.returnValue, message: data.message});
            _this.initCData(this.currentPage,this.pagesize);
          }
        })
      },
      //添加点赞、收藏
      insertgood(id,m){
        var _this = this;
        var url ='';
        if(m=='g'){
          url = "/goodlike/insert";
        }
        if(m=='f'){
          url = "/favorite/insert";
        }
        var uid = this.$store.state.user.id;
        var param  ={uid:uid,imid:id};
        this.postRequest(url , param).then(resp=> {
          _this.fullloading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            if (data.returnValue == 'success') {
              _this.initData();
            }
          }
        })
      },
      //删除点赞、收藏
      deletegood(id,m){
        var _this = this;
        var url ='';
        if(m=='g'){
          url = "/goodlike/delete";
        }
        if(m=='f'){
          url = "/favorite/delete";
        }
        var uid = this.$store.state.user.id;
        var param  ={uid:uid,imid:id};
        this.postRequest(url,param).then(resp=> {
          if (resp && resp.status == 200) {
            var data = resp.data;
            if (data.returnValue == 'success') {
              _this.initData();
            }
          }
        })
      },

      //评论
      //分享
      //翻页
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.initCData(this.currentPage, this.pagesize);
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
    computed: {
      user(){
        console.log(this.$store.state.user);
        console.log(this.$router.options.routes);
        return this.$store.state.user;
      },
      routes(){
        console.log(this.$router.options.routes);
        return this.$store.state.routes
      }
    }
  }
</script>
<style>
  .user-info {
    font-size: 12px;
    color: #09c0f6;
    cursor: pointer;
    margin-right: 5px;
  }
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
