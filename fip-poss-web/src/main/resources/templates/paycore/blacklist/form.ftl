<form class="app-form" id="blacklist-form">
  <input type="hidden" name="id">
<#if blacklist??>
  <input type="hidden" name="id">
</#if>
  <div class="field">
    <input class="easyui-textbox" name="merchantId" style="width:80%" data-options="label:'商户号：',required:true" />
  </div>
  <div class="field">
    <input id="cc" class="easyui-combobox" style="width:70%" name="currency" data-options="label:'币种：',valueField:'id',panelMaxHeight:200,panelHeight:'auto',textField:'text',url:'/paycore/blacklist/getData?type=currencyType',editable:false,multiple:false,required:true" />
  </div>
    <div class="field">
        <input id="cc" class="easyui-combobox" style="width:70%" name="tradeType" data-options="label:'交易类型：',valueField:'id',panelMaxHeight:200,panelHeight:'auto',textField:'text',url:'/paycore/blacklist/getData?type=transType',editable:false,multiple:false,required:true" />
    </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">状态：</label>
    <input class="easyui-switchbutton" name="status" data-options="onText:'启用',offText:'禁用',checked:true" value="1"/>
  </div>
</form>
<script>
	<#if blacklist??>
    $(function () {
      //需要延迟一点执行，等待页面所有组件都初始化好，再执行数据初始化
      setTimeout(function () {
        var blacklist = ${blacklist};
        $("#blacklist-form").form("load", blacklist);
      }, 200);
    });
	</#if>
</script>