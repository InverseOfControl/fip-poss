<form class="app-form" id="adjustImport-importFile-form" method="post" enctype="multipart/form-data">
    <div class="field" style="margin-top:40px;margin-left: 50px;">
        <input id="file" name="file" class="easyui-filebox" style="width: 260px; height: 26px;"
               data-options="onChange:function(){$('#showAdjustFileName').text($(this).filebox('getValue'))},
               label:'请选择调账文件：',labelWidth:'110',prompt:'请选择文件',accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel'">
    </div>
  <div class="field">
    <label class="textbox-label" style="width:360px;" id="showAdjustFileName"></label>
  </div>
</form>
