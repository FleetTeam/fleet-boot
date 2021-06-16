<template>
  <f-select-dev-component :width="1000" v-bind="configs" v-on="$listeners" />
</template>

<script>
import FSelectDevComponent from './FSelectDevComponent'

export default {
  name: 'FSelectDataDict',
  components: { FSelectDevComponent },
  props: {
    value: null, // any type
    queryConfig: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      settings: {
        name: '数据字典',
        displayKey: 'unitDataCode',
        returnKeys: ['unitDataCode','unitDataDesc'],
        listUrl: '/deve/devDataDict/list',
        multiple: false,
        placeholder: '请选择数据字典',
        queryParamCode: 'unitDataCode',
        queryParamText: '数据字典代码',
        columns: [
          { title: '数据字典代码', dataIndex: 'unitDataCode', align: 'center', width: '30%', widthRight: '70%' },
          { title: '数据字典描述', dataIndex: 'unitDataDesc', align: 'center', width: '35%'},
          // { title: '类型', dataIndex: 'type', align: 'center', width: '25%' },
          // { title: '长度', dataIndex: 'length', align: 'center', width: '25%' },
          // { title: '小数', dataIndex: 'point', align: 'center', width: '25%' }
        ],
      },
      // 多条件查询配置
      queryConfigDefault: [
        {
          key: 'unitDataDesc',
          label: '数据字典描述',
        },
        // {
        //   key: 'type',
        //   label: '类型',
        // },
        // {
        //   key: 'length',
        //   label: '长度',
        // },
        // {
        //   key: 'point',
        //   label: '小数',
        // }
      ],
    }
  },
  computed: {
    configs() {
      return Object.assign({ value: this.value }, this.settings, this.$attrs, {
        queryConfig: this.queryConfigDefault.concat(this.queryConfig),
      })
    },
  },
}
</script>

<style lang="less" scoped></style>