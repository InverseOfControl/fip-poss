<form class="app-form" id="account-form">
  <input type="hidden" name="accountNo" value="${account.accountNo}">
  <input type="hidden" id="inLimit" name="inLimit" value="${account.inLimit}">
  <input type="hidden" id="outLimit" name="outLimit" value="${account.outLimit}">
  <div class="field">
    <label class="textbox-label textbox-label-before">用户id：</label>
    <label>${account.userId}</label>
  </div>
  <div class="field">
  	<label class="textbox-label textbox-label-before">账户号：</label>
  	<label>${account.accountNo}</label>
  </div>
  <div class="field">
  	<label class="textbox-label textbox-label-before">账户类型：</label>
  	<label>${account.accountDesc}</label>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">账户币种：</label>
    <label>${account.accountCurrecny}</label>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">是否止入：</label>
    <input class="easyui-switchbutton" id="t_inLimit" data-options="onText:'是',offText:'否'"/>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">是否止出：</label>
    <input class="easyui-switchbutton" id="t_outLimit" data-options="onText:'是',offText:'否'"/>
  </div>
</form>
<script>
    $(function () {
    	$('#t_inLimit').switchbutton({
    		checked: '${account.inLimit}'=='Y',
    		onChange: function(checked){
    			if(checked){
    				$('#inLimit').val('Y');
    			}else{
    				$('#inLimit').val('N');
    			}
            },
    	});
    	$('#t_outLimit').switchbutton({
    		checked: '${account.outLimit}'=='Y',
    		onChange: function(checked){
    			if(checked){
    				$('#outLimit').val('Y');
    			}else{
    				$('#outLimit').val('N');
    			}
            },
    	});
    });
</script>