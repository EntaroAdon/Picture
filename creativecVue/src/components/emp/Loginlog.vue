<template>
  <div>
    <el-container>
      <el-header style="padding: 0px;display:flex;justify-content:space-between;align-items: center">
        <div style="display: inline">
          <el-input
            placeholder="通过登录名搜索,记得回车哦..."
            clearable
            style="width: 300px;margin: 0px;padding: 0px;"
            @keyup.enter.native="searchEmp"
            prefix-icon="el-icon-search"
            v-model="loginName">
          </el-input>
          <el-button type="primary" size="mini" style="margin-left: 5px" icon="el-icon-search" @click="searchEmp">搜索
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
              prop="name"
              align="left"
              fixed
              label="姓名">
            </el-table-column>
            <el-table-column
              prop="loginName"
              align="left"
              label="登录名">
            </el-table-column>
            <el-table-column
              prop="createTime"
              align="left"
              label="操作日期">
            </el-table-column>
          </el-table>
          <div style="display: flex;justify-content: space-between;margin: 2px">
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
  </div>
</template>
<script>
  export default {
    data() {
      return {
        //搜索条件
        criteria: '',
        //默认每页数据量
        pagesize: 10,
        tableData: [],
        loginName: '',
        multipleSelection: [],
        totalCount: -1,//默认数据总数
        currentPage: 1, //当前页码
        tableLoading: false,
      };
    },
    mounted: function () {
      this.loadData(this.criteria,this.currentPage,this.pagesize);
    },
    methods: {
      loadData(criteria,pageNum, pageAmount) {
        var _this = this;
        this.tableLoading = true;
        var param  ={loginName:_this.loginName,pageNum:pageNum, pageAmount:pageAmount}
        this.postRequest("/loginlog/findList", param).then(resp => {
          this.tableLoading = false;
          if (resp && resp.status == 200) {
            var data = resp.data;
            _this.tableData = data.pagedata;
            _this.totalCount = data.total;
          }
        })
      },

      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      searchEmp() {
        this.loadData(this.criteria,this.currentPage,this.pagesize);
      },
      currentChange(currentChange) {
        this.currentPage = currentChange;
        this.loadData(this.criteria,this.currentPage, this.pagesize);
      },
    }
  };
</script>
