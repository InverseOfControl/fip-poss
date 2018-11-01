<script src="/static/js/liquidation/mert-fee.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="mert_fee_search_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="60" style="width: 160px;"/></td>
                    <td><input name="merchantName" class="easyui-textbox" label="商户名称" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="transType" name="transType" class="easyui-combobox" label="交易类型" labelWidth="60" style="width: 160px;"/></td>
                    <td colspan="2">
                        <input id="beginSettleDate" name="beginSettleDate" class="easyui-datebox" label="结算日期" labelWidth="60" style="width: 160px;"/>
                        <input id="endSettleDate" name="endSettleDate" class="easyui-datebox" label="-" labelWidth="10" style="width: 110px;"/>
                    </td>
                    <td><input id="financeType" name="amountType" class="easyui-combobox" label="费用类型" labelWidth="60" style="width: 160px;"/></td>
                </tr>
                <tr>
                    <td><input id="chargeMethod" name="feeSettleMethod" class="easyui-combobox" label="收费方式" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="settleStatus" name="settleStatus" class="easyui-combobox" label="结算状态" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="currencyType" name="transCurrency" class="easyui-combobox" label="订单币种" labelWidth="60" style="width: 160px;"/></td>
                    <td colspan="3">
                        <input id="beginOrderDate" name="beginOrderDate" class="easyui-datebox" label="订单日期" labelWidth="60" style="width: 160px;"/>
                        <input id="endOrderDate" name="endOrderDate" class="easyui-datebox" label="-" labelWidth="10" style="width: 110px;"/>
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
        <table id="mert_fee_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>