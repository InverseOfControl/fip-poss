<form class="app-form" id="adjustAudit_audit_form" method="post">
	<input type="hidden" id="adjustId" name="adjustId" value="${adjustId}"/>
	<input type="hidden" id="auditStatus" name="auditStatus" value=""/>
  <div class="field">
    <label class="textbox-label textbox-label-before">调账流水号：</label>
    <label>${adjustId}</label>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">审核结果：</label>
    <input class="easyui-switchbutton" id="t_auditStatus" data-options="onText:'通过',offText:'驳回'"/>
  </div>
  <div class="field">
    <label class="textbox-label textbox-label-before">审核意见：</label>
    <textarea rows="5" cols="50" id="commitSummary" name="commitSummary" maxlength="80"></textarea>
  </div>
</form>
