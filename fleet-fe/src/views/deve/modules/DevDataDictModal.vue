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
      
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ver" label="ver">
          <a-input placeholder="请输入ver" v-model="model.ver" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sysId" label="系统标识">
          <a-input placeholder="请输入系统标识" v-model="model.sysId" />
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="unitDataCode" label="元数据字典代码">
          <a-input placeholder="请输入元数据字典代码" v-model="model.unitDataCode" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="unitDataDesc" label="元数据字典描述">
          <a-input placeholder="请输入元数据字典描述" v-model="model.unitDataDesc" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="refDict" label="参考字典">
          <a-input placeholder="请输入参考字典" v-model="model.refDict" />
        </a-form-model-item>
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="status" label="状态">
          <a-input placeholder="请输入状态" v-model="model.status" />
        </a-form-model-item> -->
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="optionCode" label="选项代码">
          <a-input placeholder="请输入选项代码" v-model="model.optionCode" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="optionGroup" label="选项组别">
          <a-input placeholder="请输入选项组别" v-model="model.optionGroup" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="optionVer" label="选项版本">
          <a-input placeholder="请输入选项版本" v-model="model.optionVer" />
        </a-form-model-item>
        <!-- <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="ctrlStatus" label="控制状态">
          <a-input placeholder="请输入控制状态" v-model="model.ctrlStatus" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="verNo" label="verNo">
          <a-input placeholder="请输入verNo" v-model="model.verNo" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="verStatus" label="版本状态">
          <a-input placeholder="请输入版本状态" v-model="model.verStatus" />
        </a-form-model-item>
        <a-form-model-item :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkUser" label="检出用户">
          <a-input placeholder="请输入检出用户" v-model="model.checkUser" />
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
    name: "DevDataDictModal",
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
        unitDataCode:[{ required: true, message: '请输入元数据字典代码!' }],
        unitDataDesc:[{ required: true, message: '请输入元数据字典描述!' }],
        },
        url: {
          add: "/deve/devDataDict/add",
          edit: "/deve/devDataDict/edit",
        },
      }
    },
    created () {
    },
    methods: {
      add () {
        //初始化默认值
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