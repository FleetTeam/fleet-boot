<template>
  <j-modal
    :title="title"
    :width="800"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">

        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ver" label="版本">
          <a-input placeholder="请输入版本" v-model="model.ver" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sysId" label="系统标识">
          <a-input placeholder="请输入系统标识" v-model="model.sysId" />
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="wordCode" label="词汇代码">
          <a-input placeholder="请输入词汇代码" v-model="model.wordCode" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="wordDesc" label="词汇描述">
          <a-input placeholder="请输入词汇描述" v-model="model.wordDesc" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="englishDesc" label="英文描述">
          <a-input placeholder="请输入英文描述" v-model="model.englishDesc" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="abbreviate" label="词汇缩写">
          <a-input placeholder="请输入词汇缩写" v-model="model.abbreviate" />
        </a-form-model-item>
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status" label="状态">
          <a-input placeholder="请输入状态" v-model="model.status" />
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark" label="备注">
          <a-input placeholder="请输入备注" v-model="model.remark" />
        </a-form-model-item>

      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>
  import { httpAction } from '@/api/manage'
  import moment from "moment"

  export default {
    name: "DevWordModal",
    data () {
      return {
        title:"操作",
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        wordCode:[{ required: true, message: '请输入词汇代码!' }],
        },
        url: {
          add: "/deve/devWord/add",
          edit: "/deve/devWord/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        // 初始化默认值
        this.edit({});
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      close () {
        this.$emit('close');
        this.visible = false;
        this.$refs.form.clearValidate();
      },
      handleOk () {
        const that = this;
        // 触发表单验证
         this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }else{
             return false;
          }
        })
      },
      handleCancel () {
        this.close()
      },

    }
  }
</script>

<style lang="less" scoped>

</style>