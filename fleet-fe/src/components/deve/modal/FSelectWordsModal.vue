<template>
  <a-modal :title="title" :width="1200" :visible="visible" @ok="handleOk" @cancel="handleCancel" cancelText="关闭">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="词汇代码">
              <a-input placeholder="请输入词汇代码" v-model="queryParam.wordCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="词汇描述">
              <a-input placeholder="请输入词汇描述" v-model="queryParam.wordDesc"></a-input>
            </a-form-item>
          </a-col>

          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="英文描述">
                <a-input placeholder="请输入英文描述" v-model="queryParam.englishDesc"></a-input>
              </a-form-item>
            </a-col>
          </template>

          <a-col :span="6">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchByquery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <a-table
      ref="table"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange, onSelect: onSelect }"
      @change="handleTableChange"
    >
    </a-table>
  </a-modal>
</template>

<script>
import { filterObj } from '@/utils/util'
import { pushIfNotExist } from '@/utils/util'

import { getAction } from '@/api/manage'

export default {
  name: 'FSelectWordsModal',
  components: {},
  data() {
    return {
      title: '选择词汇',
      queryParam: {},
      columns: [
        {
          title: '词汇代码',
          align: 'center',
          dataIndex: 'wordCode',
        },
        {
          title: '词汇描述',
          align: 'center',
          dataIndex: 'wordDesc',
        },
        {
          title: '英文描述',
          align: 'center',
          dataIndex: 'englishDesc',
        },
        {
          title: '词汇缩写',
          align: 'center',
          dataIndex: 'abbreviate',
        },
      ],
      dataSource: [],
      ipagination: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '20'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' 共' + total + '条'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },
      isorter: {
        column: 'createTime',
        order: 'desc',
      },
      selectedRowKeys: [],
      selectionRows: [],
      visible: false,
      toggleSearchStatus: false,
    }
  },
  created() {
    this.loadData()
  },
  watch: {},
  methods: {
    show(wordIds) {
      this.visible = true
      if (wordIds && wordIds.length > 0) {
        this.selectedRowKeys = wordIds.split(',')
      } else {
        this.selectedRowKeys = []
      }
    },
    add(selectUser, wordIds) {
      this.visible = true
      this.edit(selectUser, wordIds)
    },
    edit(wordList, wordIds) {
      //控制台报错
      console.log('edit input parm wordIds', wordIds)
      if (wordIds && wordIds.length > 0) {
        this.selectedRowKeys = wordIds.split(',')
      } else {
        this.selectedRowKeys = []
      }
      if (!wordList) {
        this.selectionRows = []
      } else {
        var that = this
        that.selectionRows = []
        wordList.forEach(function (record, index) {
          console.log(record)
          that.selectionRows.push(record)
        })
        // this.selectionRows = selectUser;
      }
    },
    loadData(arg) {
      if (arg === 1) {
        this.ipagination.current = 1
      }
      let params = this.getQueryParams() //查询条件
      getAction('/deve/devWord/list', params).then((res) => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
      })
    },
    getQueryParams() {
      let param = Object.assign({}, this.queryParam, this.isorter)
      param.field = this.getQueryField()
      //--update-begin----author:scott---date:20190818------for:新建公告时指定特定用户翻页错误SelectUserListModal #379----
      // param.current = this.ipagination.current;
      // param.pageSize = this.ipagination.pageSize;
      param.pageNo = this.ipagination.current
      param.pageSize = this.ipagination.pageSize
      //--update-end----author:scott---date:20190818------for:新建公告时指定特定用户翻页错误SelectUserListModal #379---
      return filterObj(param)
    },
    getQueryField() {
      let str = 'id,'
      for (let a = 0; a < this.columns.length; a++) {
        str += ',' + this.columns[a].dataIndex
      }
      return str
    },
    onSelectChange(selectedRowKeys, selectionRows) {
      console.log('when change onSelectChange input parm!', selectedRowKeys, selectionRows)
      console.log('when change onSelectChange global var before eval:', this.selectedRowKeys, this.selectionRows)

      this.selectedRowKeys = selectedRowKeys
      //this.selectionRows = selectionRows
      // for (let data of selectionRows) {
      //     pushIfNotExist(this.selectionRows,data,'id')

      // }
      // console.log("when change onSelectChange global var after eval:!",this.selectedRowKeys,this.selectionRows)
    },
    onSelect(record, selected) {
      console.log('onSelect in ', record, selected, this.selectionRows)
      if (selected == true) {
        this.selectionRows.push(record)
      } else {
        this.selectionRows.forEach(function (item, index, arr) {
          if (item.id == record.id) {
            arr.splice(index, 1)
          }
        })
      }
    },

    searchReset() {
      let that = this
      Object.keys(that.queryParam).forEach(function (key) {
        that.queryParam[key] = ''
      })
      that.loadData(1)
    },
    handleTableChange(pagination, filters, sorter) {
      //TODO 筛选
      console.log('when your change handleTableChange', this.selectionRows)
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      this.loadData()
    },
    handleCancel() {
      this.selectionRows = []
      this.selectedRowKeys = []
      this.visible = false
    },

    handleOk() {
      let wordIds = ''
      for (var i = 0; i < this.selectionRows.length; i++) {
        wordIds += ',' + this.selectionRows[i].id
      }
      console.log('modal in wordIds:', wordIds.substring(1))

      this.$emit('ok', this.selectionRows, wordIds.substring(1))
      this.handleCancel()
    },
    searchByquery() {
      this.loadData(1)
    },
    handleToggleSearch() {
      this.toggleSearchStatus = !this.toggleSearchStatus
    },
  },
}
</script>
<style scoped>
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

.anty-img-wrap {
  height: 25px;
  position: relative;
}
.anty-img-wrap > img {
  max-height: 100%;
}
</style>