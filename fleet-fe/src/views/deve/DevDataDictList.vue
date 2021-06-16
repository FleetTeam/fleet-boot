<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="ver">
              <a-input placeholder="请输入ver" v-model="queryParam.ver"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="系统标识">
              <a-input placeholder="请输入系统标识" v-model="queryParam.sysId"></a-input>
            </a-form-item>
          </a-col> -->
        <!-- <template v-if="toggleSearchStatus"> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="元数据字典代码">
              <a-input placeholder="请输入元数据字典代码" v-model="queryParam.unitDataCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="元数据字典描述">
              <a-input placeholder="请输入元数据字典描述" v-model="queryParam.unitDataDesc"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="参考字典">
              <f-select-ref-dict placeholder="请入参考字典" v-model="queryParam.refDict">
              <f-select-ref-dict :buttons="false" v-model="formData.selectRefDict" />

            </a-form-item>
          </a-col> -->
          <!-- </template> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>   -->
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('数据字典')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        class="j-table-force-nowrap"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <devDataDict-modal ref="modalForm" @ok="modalFormOk"></devDataDict-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import DevDataDictModal from './modules/DevDataDictModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "DevDataDictList",
    mixins:[JeecgListMixin],
    components: {
      DevDataDictModal       
    },
    data () {
      return {
        description: '数据字典管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		  //  {
      //       title: 'ver',
      //       align:"center",
      //       dataIndex: 'ver'
      //      },
		  //  {
      //       title: '系统标识',
      //       align:"center",
      //       dataIndex: 'sysId'
      //      },
		   {
            title: '元数据字典代码',
            align:"center",
            dataIndex: 'unitDataCode'
           },
		   {
            title: '元数据字典描述',
            align:"center",
            dataIndex: 'unitDataDesc'
           },
		   {
            title: '参考字典',
            align:"center",
            dataIndex: 'refDict'
           },
		   {
            title: '状态',
            align:"center",
            dataIndex: 'status'
           },
		   {
            title: '选项代码',
            align:"center",
            dataIndex: 'optionCode'
           },
		   {
            title: '选项组别',
            align:"center",
            dataIndex: 'optionGroup'
           },
		   {
            title: '选项版本',
            align:"center",
            dataIndex: 'optionVer'
           },
		   {
            title: '控制状态',
            align:"center",
            dataIndex: 'ctrlStatus'
           },
		  //  {
      //       title: 'verNo',
      //       align:"center",
      //       dataIndex: 'verNo'
      //      },
		  //  {
      //       title: '版本状态',
      //       align:"center",
      //       dataIndex: 'verStatus'
      //      },
		  //  {
      //       title: '检出用户',
      //       align:"center",
      //       dataIndex: 'checkUser'
      //      },
		   {
            title: '备注',
            align:"center",
            dataIndex: 'remark'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/deve/devDataDict/list",
          delete: "/deve/devDataDict/delete",
          deleteBatch: "/deve/devDataDict/deleteBatch",
          exportXlsUrl: "deve/devDataDict/exportXls",
          importExcelUrl: "deve/devDataDict/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
     
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>