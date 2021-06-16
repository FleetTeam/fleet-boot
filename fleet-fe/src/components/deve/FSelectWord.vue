<template>
  <f-select-dev-component :width="1000" v-bind="configs" v-on="$listeners"/>
</template>

<script>
  import FSelectDevComponent from './FSelectDevComponent'

  export default {
    name: 'FSelectWord',
    components: { FSelectDevComponent },
    props: {
      value: null, // any type
      queryConfig: {
        type: Array,
        default: () => []
      },
    },
    data() {
      return {
        settings: {
          name: '词汇',
          displayKey: 'wordCode',
          returnKeys: ['wordCode'],
          returnId: true,
          listUrl: '/deve/devWord/list',
          placeholder: '请选择词汇',
          queryParamCode: 'wordCode',
          queryParamText: '词汇代码',
          columns: [
            { title: '词汇代码', dataIndex: 'wordCode', align: 'center', width: '30%', widthRight: '70%' },
            { title: '词汇描述', dataIndex: 'wordDesc', align: 'center', width: '35%' },
            { title: '词汇缩写', dataIndex: 'abbreviate', align: 'center', width: '25%' }
          ]
        }
        ,
                  // 多条件查询配置
        queryConfigDefault: [
          {
            key: 'wordDesc',
            label: '词汇描述'
          },
          {
            key: 'abbreviate',
            label: '词汇缩写'
          },
        ],
      }
    },
    computed: {
      configs() {
        return Object.assign({ value: this.value }, this.settings, this.$attrs,{
          queryConfig: this.queryConfigDefault.concat(this.queryConfig)
        })
      }
    }
  }
</script>

<style lang="less" scoped></style>