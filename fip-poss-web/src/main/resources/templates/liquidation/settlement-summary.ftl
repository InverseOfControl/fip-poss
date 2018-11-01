<script src="/static/js/liquidation/settlement-summary.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="settlement_summary_search_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="50" style="width: 150px;"/></td>
                    <td><input name="summaryOrderId" class="easyui-textbox" label="结算汇总单号" labelWidth="80" style="width: 180px;"/></td>
                    <td><input id="currencyType" name="summaryCurrency" class="easyui-combobox" label="结算币种" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="autoWithdrawal" name="autoWithdrawFlag" class="easyui-combobox" label="是否自动提现" labelWidth="80" style="width: 180px;"/></td>
                    <td>
                        <input id="beginDate" name="beginDate" label="结算汇总单日期" labelWidth="90" style="width: 190px;"/>
                        <input id="endDate" name="endDate" label="-" labelWidth="10" style="width: 110px;"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="5">
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                        <a class="easyui-linkbutton download" data-options="iconCls:'fa fa-download'">下载</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="settlement_summary_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>