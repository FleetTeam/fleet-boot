<template>
  <f-select-dev-component :width="1000" v-bind="configs" v-on="$listeners" />
</template>

<script>
import FSelectDevComponent from './FSelectDevComponent'

export default {
  name: 'FSelectRefDict',
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
        name: '参考字典',
        displayKey: 'dictCode',
        returnKeys: ['dictCode','dictDesc'],
        listUrl: '/deve/devRefDict/list',
        multiple: false,
        placeholder: '请选择参考字典',
        queryParamCode: 'dictCode',
        queryParamText: '字典代码',
        columns: [
          { title: '字典代码', dataIndex: 'dictCode', align: 'center', width: '30%', widthRight: '70%' },
          { title: '字典描述', dataIndex: 'dictDesc', align: 'center', width: '35%'},
          { title: '类型', dataIndex: 'type', align: 'center', width: '25%' },
          { title: '长度', dataIndex: 'length', align: 'center', width: '25%' },
          { title: '小数', dataIndex: 'point', align: 'center', width: '25%' },
        ],
      },
      // 多条件查询配置
      queryConfigDefault: [
        {
          key: 'dictDesc',
          label: '字典描述',
        },
        {
          key: 'type',
          label: '类型',
        },
        {
          key: 'length',
          label: '长度',
        },
        {
          key: 'point',
          label: '小数',
        }
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