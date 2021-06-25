<template>
  <div class="components-input-demo-presuffix">
    <!---->
    <a-input @click="openModal" placeholder="请点击选择词汇" v-model="wordCodes" readOnly :disabled="disabled">
      <a-icon slot="prefix" type="file-done" title="词汇选择控件" />
      <a-icon
        v-if="wordCodes && disabled == false"
        slot="suffix"
        type="close-circle"
        @click="handleEmpty"
        title="清空"
      />
    </a-input>

    <f-select-words-modal ref="innerWordsSelectModal" @ok="handleOK" />
  </div>
</template>

<script>
import FSelectWordsModal from './modal/FSelectWordsModal'
export default {
  name: 'FSelectWords',
  components: {
    FSelectWordsModal,
  },
  props: {
    value: {
      type: String,
      required: false,
    },
    disabled: {
      type: Boolean,
      required: false,
      default: false,
    },
  },
  data() {
    return {
      confirmLoading: false,
      wordIds: [],
      wordCodes: '',
      wordList: [],
    }
  },
  mounted() {
    this.wordCodes = this.value
  },
  watch: {
    value(val) {
      //update-begin-author:wangshuai date:20201124 for:组件 JSelectDepart.vue不是默认id时新内容编辑问题 gitee I247X2
      // if (this.customReturnField === 'id') {
      //this.wordIds = val
      this.wordCodes = val

      // }
      //update-end-author:wangshuai date:20201124 for:组件 JSelectDepart.vue不是默认id时新内容编辑问题 gitee I247X2
    },
  },
  model: {
    prop: 'value',
    event: 'change',
  },
  methods: {
    openModal() {
      this.$refs.innerWordsSelectModal.add(this.wordList, this.wordIds)
      //this.$refs.innerWordsSelectModal.show(this.wordIds)
    },
    handleOK(wordList, wordIds) {
      console.log('modal return wordList', wordList)
      console.log('modal return wordIds', wordIds)
      let value = ''
      for (var i = 0; i < wordList.length; i++) {
        value += wordList[i].wordCode
      }
      this.wordCodes = value
      this.wordIds = wordIds
      console.log('dataaddddddddd', value)

      this.$emit('change', this.wordCodes)
      this.$emit('moreWordInfo', wordList)
      this.wordList = wordList
    },
    handleEmpty() {
      this.handleOK('')
    },
  },
  computed: {
    clearShow: function () {
      return this.wordCodes != '' && this.disabled == false
    },
  },
}
</script>

<style scoped>
.components-input-demo-presuffix .anticon-close-circle {
  cursor: pointer;
  color: #ccc;
  transition: color 0.3s;
  font-size: 12px;
}
.components-input-demo-presuffix .anticon-close-circle:hover {
  color: #f5222d;
}
.components-input-demo-presuffix .anticon-close-circle:active {
  color: #666;
}
</style>