<template>
  <a-card :bordered="false" style="height: 100%; padding-bottom: 200px">
    <div class="table-page-search-wrapper">
      <a-form layout="inline" :form="form">

        <!-- 词汇选择 -->
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="选择词汇">
              <f-select-words v-model="wordValue" />
            </a-form-item>
          </a-col>
          <!-- <a-col :span="12">选中值：{{ formData.selectWord}}</a-col> -->
          <a-col :span="12">选中值：:{{wordValue}}</a-col>
        </a-row>
        <!-- 参考字典选择 -->
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="选择参考字典">
              <f-select-ref-dict :buttons="false" v-model="formData.selectRefDict" />
            </a-form-item>
          </a-col>
          <a-col :span="12">选中值：{{ formData.selectRefDict }}</a-col>
        </a-row>
        <!-- 数据字典选择 -->
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="选择数据字典">
              <f-select-data-dict :buttons="false" v-model="formData.selectDataDict" />
            </a-form-item>
          </a-col>
          <a-col :span="12">选中值：{{ formData.selectDataDict }}</a-col>
          
        </a-row>
                <!--  通过部门选择用户控件 -->
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="选择用户">
              <j-select-user-by-dep v-model="userIds" :multi="true" :backUser="true" @back="back"></j-select-user-by-dep>
            </a-form-item>
          </a-col>
          <a-col :span="12">选中的用户(v-model):{{ userIds }}</a-col>
        </a-row>

       </a-form>
    </div>
  </a-card>
</template>

<script>

import FSelectWords from '@/components/deve/FSelectWords'
import FSelectRefDict from '@/components/deve/FSelectRefDict'
import FSelectDataDict from '@/components/deve/FSelectDataDict'
import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'


export default {
  name: 'DeveDemo',
  inject: ['closeCurrent'],
  components: {
    FSelectWords,
    FSelectRefDict,
    FSelectDataDict,
    JSelectUserByDep
  },
  data() {
    return {
      wordValue:"",
      selectList: [],
      formData: {
        areaLinkage1: '110105',
        areaLinkage2: '140221',
        sex: 1,
      },
      form: this.$form.createForm(this),
      userIds: 'admin,test',
      info: []
    }
  },
  computed: {
    nameList: function () {
      var names = []
      for (var a = 0; a < this.selectList.length; a++) {
        names.push(this.selectList[a].name)
      }
      return names
    }
  },
  methods: {
    handleChange() {},
    changeMe() {
      console.log('you so ...  , change Me')
    },
    selectOK: function (data) {
      this.selectList = data
      console.log("outside selectOK data:-----"+data)
    },
    back: function(info){
      console.log("outside parent"+info[0].value)
    }
  },
}
</script>
<style lang="less" scoped>
.ant-card-body .table-operator {
  margin-bottom: 18px;
}

.ant-table-tbody .ant-table-row td {
  padding-top: 15px;
  padding-bottom: 15px;
}

.anty-row-operator button {
  margin: 0 5px;
}

.ant-btn-danger {
  background-color: #ffffff;
}

.ant-modal-cust-warp {
  height: 100%;
}

.ant-modal-cust-warp .ant-modal-body {
  height: calc(100% - 110px) !important;
  overflow-y: auto;
}

.ant-modal-cust-warp .ant-modal-content {
  height: 90% !important;
  overflow-y: hidden;
}
</style>