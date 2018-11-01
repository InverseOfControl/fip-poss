<script src="/static/js/acct/adjustImport.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 80px;padding: 10px;">
        <form id="acct_adjustImport_search_from" class="searcher-form">
            <table>
                <tr>
                	<td><input name="batchId" class="easyui-textbox" label="批次号：" labelWidth="80" style="width: 250px;"/></td>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 250px;"/></td>
                    <td colspan="2">
                        <input name="beginApplyDate" label="经办日期：" labelWidth="80" style="width: 210px;"/>
                        <input name="endApplyDate" label="-" labelWidth="10" style="width:130px;"/>
                    </td>
                </tr>
                <tr>
                    <td><input name="adjustId" class="easyui-textbox" label="调账订单号：" labelWidth="80" style="width: 250px;"/></td>
                    <td><input name="auditStatus" class="easyui-combobox" label="审核状态：" labelWidth="80" style="width: 190px;"
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=adjustAuditStatus'"/>
                    </td>
                    <td colspan="2">
                        <input name="beginAuditDate" class="easyui-datebox field" label="审核日期：" labelWidth="80" style="width: 210px;" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                        <input name="endAuditDate" class="easyui-datebox field" label="-" labelWidth="10" style="width:130px; " onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                    </td>
                    <td colspan="3">
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="acct_adjustImport_dg"></table>
    </div>
</div>