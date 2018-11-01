<form class="app-form" id="adjustImport_uploadVoucher_form" method="post" enctype="multipart/form-data">
    <input type="hidden" id="adjustId" name="adjustId" value="${adjustId}"/>
    <div class="field">
    	<label class="textbox-label textbox-label-before">调账流水号：</label>
    	<label>${adjustId}</label>
    </div>
    <div class="field" style="margin-top:40px;">
        <input id="file" name="file" class="easyui-filebox" style="width: 260px; height: 26px;"
               data-options="onChange:function(){$('#showVoucherName').text($(this).filebox('getValue'))},
               label:'请选择调账凭证：',labelWidth:'110',prompt:'请选择文件',accept:'image/jpeg,image/png'">
    </div>
  <div class="field">
    <label class="textbox-label" style="width:360px;" id="showVoucherName"></label>
  </div>
</form>
