<script src="/static/js/acct/tradeDetail.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 110px;padding: 10px;">
        <form id="acct_tradeDetail_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="pipeLogId" class="easyui-textbox" label="日志id：" labelWidth="80" style="width: 250px;"/></td>
                    <td><input name="financeType" class="easyui-combobox" label="金额类型：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=financeType'" style="width: 190px;"/>
                    </td>
                    <td><input name="bookType" class="easyui-combobox" label="记账类型：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=bookType'" style="width: 190px;"/>
                    </td>
                	<td><input name="orderType" class="easyui-combobox" label="订单类型：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=orderType'" style="width: 190px;"/>
                    </td>
                </tr>
                <tr>
                	<td><input name="merchantOrderId" class="easyui-textbox" label="商户订单号：" labelWidth="80" style="width: 250px;"/></td>
                	<td><input name="sysTraceNo" class="easyui-textbox" label="收单订单号：" labelWidth="80" style="width: 250px;"/></td>
                	<td><input name="requestId" class="easyui-textbox" label="请求流水号：" labelWidth="80" style="width: 250px;"/></td>
                	<td><input name="userId" class="easyui-textbox" label="商户号：" labelWidth="80" style="width: 200px;"/></td>
                </tr>
                <tr>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 250px;"/></td>
                    <td colspan="2">
                        <input name="beginAccountingDay" label="会计日期：" labelWidth="80" style="width: 210px;"/>
                        <input name="endAccountingDay" label="-" labelWidth="10" style="width:130px;"/>
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
        <table id="acct_tradeDetail_dg"></table>
    </div>
</div>