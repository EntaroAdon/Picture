<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center;margin-top:20px">
        <div style="display: inline">
          <el-form :model="form" :inline="true" ref="addEmpForm" style="padding: 0px;">
            <el-form-item label="图片类型:"  prop="name" style="margin-left: -5px;">
              <el-select v-model="form.type" placeholder="请选择类型">
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
                placeholder="通过图片名搜索,记得回车哦..."
                clearable
                style="width: 300px;margin: 0px;padding: 0px;"
                @keyup.enter.native="searchEmp"
                prefix-icon="el-icon-search"
                v-model="form.name">
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div style="margin-left: 5px;margin-right: 20px;display: inline">
          <el-button type="primary" size="mini" icon="el-icon-plus"
                     @click="showAddEmpView">
            添加图片
          </el-button>
        </div>
      </el-header>
      <el-main style="padding-left: 0px;padding-top: 0px">
        <div>
          <el-table
            :data="tableData"
            v-loading="tableLoading"
            border
            stripe
            @selection-change="handleSelectionChange"
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              align="left"
              width="30">
            </el-table-column>
            <el-table-column
              align="left"
              label="图片">
              <template slot-scope="scope">
                <img  :src="scope.row.url+'img/findById?imgid='+scope.row.id" width="200px"/>
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              align="left"
              label="名称">
            </el-table-column>
            <el-table-column
              prop="type"
              align="left"
              label="类型">
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="195">
              <template slot-scope="scope">
                <el-button @click="showEditEmpView(scope.row)" style="padding: 3px 4px 3px 4px;margin: 2px"
                           size="mini">编辑
                </el-button>
                <el-button slot="reference" type="primary" size="mini" style="padding: 3px 4px 3px 4px;margin: 2px"
                                        @click="downloadImg(scope.row)">下载
               </el-button>
                <el-button type="danger" style="padding: 3px 4px 3px 4px;margin: 2px" size="mini"
                           @click="deleteEmp(scope.row)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
            <el-button type="danger" size="mini" v-if="tableData.length>0" :disabled="multipleSelection.length==0"
                       @click="deleteManyEmps">批量删除
            </el-button>
            <el-pagination
              background
              :page-size="10"
              :current-page="currentPage"
              @current-change="currentChange"
              layout="prev, pager, next"
              :total="totalCount">
            </el-pagination>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-form :model="ruleForm" :rules="rules" :inline="true" ref="addEmpForm" style="margin: 0px;padding: 0px;width:1%">
      <div style="text-align: left">
        <el-dialog
          :title="dialogTitle"
          style="padding: 0px;"
          :close-on-click-modal="false"
          :visible.sync="dialogVisible"
          width="27%">
            <el-form-item label="名称:"  prop="name" style="margin-left: -5px;">
              <el-input v-model="ruleForm.name" ></el-input>
            </el-form-item>
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
            <el-form-item label="图片地址:"  prop="url" style="margin-left: -5px;">
              <el-input v-model="ruleForm.url" ></el-input>
            </el-form-item>
            <el-form-item prop="picture" style="margin-right: 10px;width: 130px;">
              <el-upload
                class="avatar-uploader"
                action="aaa"
                list-type="picture-card"
                :on-remove="handleImgRemovephoto"
                :before-upload="beforeuploadphoto"
                :show-file-list="false"
              >
                <img v-if="imgurl" :src=imgurl style="width: 120px;" @error="imgurl=false">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          <span slot="footer" class="dialog-footer">
              <el-button size="mini" @click="cancelEidt">取 消</el-button>
              <el-button size="mini" type="primary" @click="addEmp('addEmpForm')">确 定</el-button>
            </span>
        </el-dialog>
      </div>
    </el-form>
  </div>
</template>
<script>
  import $axios from 'axios'
  export default {
    mounted: function () {
      this.loadData(this.criteria,this.currentPage,this.pagesize);
      this.loadType();
    },
    methods: {
      loadData(criteria,pageNum, pageAmount) {
        var _this = this;
        this.tableLoading = true;
        var param  ={name:_this.form.name,type:_this.form.type,pageNum:pageNum, pageAmount:pageAmount}
        this.postRequest("/img/findList", param).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.tableData = data.pagedata;
            _this.totalCount = data.total;
          }
        })
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
      /*图片上传*/
      //图片删除事件
      handleImgRemovephoto: function (file, fileList) {
        if(file.status == 'ready') return;//预览图片时返回
        console.log(file, fileList);
        console.log("删除事件");
        this.imgurl = '';
      },
      //阻止upload的自己上传，进行再操作
      beforeuploadphoto(file) {
        const isJPG = file.type === 'image/png' || file.type === 'image/gif' || file.type === 'image/jpg' || file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 < 5000;

        if (!isJPG) {
          this.$message.error('上传图片只能是 png、gif、jpg、jpeg 格式!');
          return false;
        }
        if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 50KB!');
          return false;
        }
        //创建临时的路径来展示图片
        var windowURL = window.URL || window.webkitURL;
        var src = windowURL.createObjectURL(file);
        this.param.append('file', file, file.name);
        //图片加入预览内容
        this.imgurl = src;
        return false;
      },

      downloadImg(row){
          var src='data:image/png;base64,'+ row.picture;
          this.srcImg = src; //图片回显
          var link = document.createElement('a');
          link.href = src;
          link.download =row.name+ ".jpg";
          link.click();
        },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      deleteManyEmps() {
        this.$confirm('此操作将删除[' + this.multipleSelection.length + ']条数据, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var ids = '';
          for (var i = 0; i < this.multipleSelection.length; i++) {
            ids += this.multipleSelection[i].id + ",";
          }
          this.doDelete(ids);
        }).catch(() => {
        });
      },
      deleteEmp(row) {
        this.$confirm('此操作将永久删除[' + row.name + '], 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.doDelete(row.id);
        }).catch(() => {
        });
      },
      doDelete(ids) {
        this.tableLoading = true;
        var _this = this;
        this.deleteRequest("/img/delete/" + ids).then(resp => {
          _this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.loadData(this.criteria,this.currentPage,this.pagesize);
          }
        })
      },
      searchEmp() {
        this.loadData(this.criteria,this.currentPage,this.pagesize);
      },
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadData(this.criteria,this.currentPage, this.pagesize);
      },
      addEmp(formName) {
        var _this = this;
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.ruleForm.id) {
              //更新
              this.tableLoading = true;
              this.param.append('id', _this.ruleForm.id);//名称
              this.param.append('name', _this.ruleForm.name);//名称
              this.param.append('type', _this.ruleForm.type);//
              this.param.append('url', _this.ruleForm.url);//
              this.uploadFileRequest("/img/update", this.param).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;
                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadData(this.criteria,this.currentPage,this.pagesize);
                }
              })
            } else {
              //添加
              if(_this.imgurl == false){
                _this.$alert('请上传图片', '提示', {
                  confirmButtonText: '确定',
                  callback: action => {
                  }
                });
              }
              this.tableLoading = true;
              this.param.append('name', _this.ruleForm.name);//名称
              this.param.append('type', _this.ruleForm.type);//
              this.param.append('url', _this.ruleForm.url);//
              this.uploadFileRequest("/img/insert", this.param).then(resp => {
                _this.tableLoading = false;
                if (resp && resp.status == 200) {
                  var data = resp.data;

                  _this.dialogVisible = false;
                  _this.emptyEmpData();
                  _this.loadData(this.criteria,this.currentPage,this.pagesize);
                }
              })
            }
          } else {
            return false;
          }
        });
      },
      cancelEidt() {
        this.dialogVisible = false;
        this.emptyEmpData();
      },
      showEditEmpView(row) {
        console.log(row)
        this.dialogTitle = "编辑用户";
        this.ruleForm = row;
        this.dialogVisible = true;
      },
      showAddEmpView() {
        this.dialogTitle = "添加用户";
        this.dialogVisible = true;
        this.emptyEmpData();
      },
      emptyEmpData() {
        this.imgurl = '';//图片url
        this.param = new FormData();//表单上传的方法
        this.ruleForm = {
          id:'',//主键
          name:'', //名称
          picture:'',//图片
          type:'', //类型
          url:'http://localhost:8089/'
        }
      }
    },

    data() {
      return {
        form:{
          name:'',
          type:''
        },
        //搜索条件
        criteria: '',
        //默认每页数据量
        pagesize: 10,
        tableData: [],
        picture:'',
        name: '',
        subItem:'',
        fileUploadBtnText: '导入数据',
        beginDateScope: '',
        dialogTitle: '',
        multipleSelection: [],
        totalCount: -1,//默认数据总数
        currentPage: 1, //当前页码
        dialogVisible: false,
        tableLoading: false,
        imgType:[],//图片类型
        imgurl:'',//图片url
        param: new FormData(),//表单上传的方法
        //详情修改添加
        ruleForm:{
          id:'',//主键
          name:'', //名称
          picture:'',//图片
          type:'', //类型
          url:'http://localhost:8089/'
        },
        rules: {
          name: [{required: true, message: '必填', trigger: 'blur'}],
          type: [{required: true, message: '必填', trigger: 'blur'}]
        }
      };
    },
  };
</script>
<style>
  .el-dialog__body {
    padding-top: 0px;
    padding-bottom: 0px;
  }

  .slide-fade-enter-active {
    transition: all .8s ease;
  }

  .slide-fade-leave-active {
    transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
  }

  .slide-fade-enter, .slide-fade-leave-to {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
