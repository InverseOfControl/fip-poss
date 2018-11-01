<form class="app-form" id="freezing-form">
  <input type="hidden" name="accountNo" value="${balanceDetail.accountNo}">
  <input type="hidden" name="freezeType" value="${freezeType}">
  <div class="field">
    <label class="textbox-label textbox-label-before">用户id：</label>
    <label>${balanceDetail.userId}</label>
  </div>
  <div class="field">
  	<label class="textbox-label textbox-label-before">账户号：</label>
  	<label>${balanceDetail.accountNo}</label>
  </div>
  <div class="field">
  	<label class="textbox-label textbox-label-before">账户类型：</label>
  	<label>${balanceDetail.titleNo}</label>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">账户币种：</label>
    <label>${balanceDetail.accountCurrecny}</label>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">可用余额：</label>
    <label>${balanceDetail.availableAmount}</label>
  </div>
  <div class="field">
    <label id="freezeType" class="textbox-label textbox-label-before">冻结金额：</label>
    <input type="text" name="amount" value="" class = "easyui-numberbox" data-options="min:0,precision:4">
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">备注：</label>
    <input type="text" name="freezingSummary" value="">
  </div>
</form>
<script>
    $(function () {
    	var freezeType = '${freezeType}';
    	if(freezeType == 'freeze' ){
    		$('#freezeType').text('冻结金额：');
    	}else{
    		$('#freezeType').text('解冻金额：');
    	}
    });
</script>