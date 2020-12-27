<template>
  <div style="margin-top: 10px" v-loading="fullloading">
    <div style="margin-bottom: 10px;display: flex;justify-content: center;align-items: center">
      <el-form :model="ruleForm" :inline="true" ref="addEmpForm" style="margin: 0px;padding: 0px;">
        <el-form-item label="图片类型:"  prop="name" style="margin-left: -5px;">
          <el-select v-model="ruleForm.type" placeholder="请选择类型">
            <el-option
              v-for="item in imgType"
              :key="item.id"
              :label="item.type"
              :value="item.type">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称:"  prop="name" style="margin-left: -5px;">
          <el-input
            placeholder="通过图片名搜索更多..."
            prefix-icon="el-icon-search"
            size="small"
            style="width: 400px;margin-right: 10px"
            v-model="ruleForm.name">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div style="display: flex;justify-content: space-around;flex-wrap: wrap;text-align: left">
      <el-card style="width: 350px;margin-bottom: 20px" v-for="(item,index) in tableData" :key="item.id">
        <div>
          <div style="width: 100%;display: flex;justify-content: center">
            <router-link tag="a" target="_blank" :to="{path:'/imghost',query:{id:item.id}}">
              <img :src="item.url+'img/findById?imgid='+item.id" width="360px" alt="item.name" >
            </router-link>
          </div>
          <div style="text-align: right;">
            <div>
              <span class="user-info" style="color: #f62a09;" @click="deletegood(item.id,'g')"  v-if="item.gid != 0">
                <i class="fa fa-heart">已赞</i></span>
              <span class="user-info" style="color: #f62a09;" @click="insertgood(item.id,'g')"  v-else>
                <i class="fa fa-heart-o"></i>点赞</span>
              <span class="user-info" style="color: #f67609;" @click="deletegood(item.id,'f')" v-if="item.fid != 0">
                <i class="fa fa-star"></i>已收藏</span>
              <span class="user-info" style="color: #f67609;"  @click="insertgood(item.id,'f')"  v-else>
                <i class="fa fa-star-o"></i>收藏</span>
              <span class="user-info"  @click="downloadImg(item)" v-if="user.roleType == '会员'">
                <i class="fa fa-download"></i>下载</span>
              <span class="user-info" style="color: #0926f6;"
                    v-clipboard:copy="item.url+'img/findById?imgid='+item.id"
                    v-clipboard:success="onCopy"
                    v-clipboard:error="onError">
                <i class="el-icon-share"></i>分享</span></div>
        </div>
        </div>

        <div  style="text-align: center;padding: 10px">
          <span>{{item.name}}</span>
        </div>
      </el-card>
    </div>

    <el-pagination
      background
      :page-size="9"
      :current-page="currentPage"
      @current-change="currentChange"
      layout="prev, pager, next"
      :total="totalCount">
    </el-pagination>
  </div>
</template>
<script>
  export default{
    data(){
      return {
        ruleForm:{
          name:'',
          type:''
        },
        imgType:[],//图片类型
        message: '链接',
        name: '',
        //默认每页数据量
        pagesize: 9,
        tableData: [],
        totalCount: -1,//默认数据总数
        currentPage: 1, //当前页码
        fullloading: false,
        goodif:true,//是否点赞
        fif:true,//是否收藏
      }
    },
    mounted: function () {
      this.initCards(this.currentPage,this.pagesize);
      this.loadType();
    },
    methods: {
      onCopy: function (e) {
        this.$message({type: 'success', message: "链接复制成功，快去发送给小伙伴吧^^^^^"});
      },
      onError: function (e) {
        this.$message({type: 'success', message: "链接复制失败^^^^^"});
      },
      searchClick(){
        this.initCards(this.currentPage,this.pagesize);
      },

      loadType() {
        var _this = this;
        this.postRequest("/imgType/findAllList", {}).then(resp => {
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.imgType = data;
          }
        })
      },
      downloadImg(row){
        var src='data:image/png;base64,'+ row.picture;
        this.srcImg = src; //图片回显
        var link = document.createElement('a');
        link.href = src;
        link.download =row.name+ ".jpg";
        link.click();
      },
      initCards(pageNum, pageAmount){
        this.fullloading = true;
        var _this = this;
        var uid = this.$store.state.user.id;
        var param  ={name:_this.ruleForm.name,type:_this.ruleForm.type,uid:uid,pageNum:pageNum, pageAmount:pageAmount}
        this.postRequest("/img/findUserList" , param).then(resp=> {
          if (resp && resp.status == 200) {
            _this.tableData = resp.data.pagedata;
            console.log(_this.tableData)
            _this.totalCount = resp.data.total;
            _this.fullloading = false;
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
              _this.initCards(this.currentPage,this.pagesize);
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
              _this.initCards(this.currentPage,this.pagesize);
            }
          }
        })
      },

      //评论
      //分享
      //翻页
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.initCards(this.currentPage, this.pagesize);
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
      return this.$store.state.user;
     },
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
  .el-card__body {
    padding: 0px 20px;
  }
</style>
