<script src="/static/js/liquidation/settlement-detail.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="settlement_detail_search_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="60" style="width: 160px;"/></td>
                    <td><input name="merchantOrderId" class="easyui-textbox" label="商户订单号" labelWidth="80" style="width: 180px;"/></td>
                    <td><input name="orderId" class="easyui-textbox" label="收单订单号" labelWidth="70" style="width: 170px;"/></td>
                    <td><input id="transType" name="transType" class="easyui-combobox" label="交易类型" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="financeType" name="amountType" class="easyui-combobox" label="金额类型" labelWidth="60" style="width: 160px;"/></td>
                </tr>
                <tr>
                    <td><input id="currencyType0" name="transCurrency" class="easyui-combobox" label="订单币种" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="chargeMethod" name="feeSettleMethod" class="easyui-combobox" label="费用结算方式" labelWidth="80" style="width: 180px;"/></td>
                    <td><input id="currencyType1" name="settleCurrency" class="easyui-combobox" label="结算币种" labelWidth="70" style="width: 170px;"/></td>
                    <td><input name="settlementId" class="easyui-textbox" label="结算单号" labelWidth="60" style="width: 160px;"/></td>
                    <td><input id="settleStatus" name="settleStatus" class="easyui-combobox" label="结算状态" labelWidth="60" style="width: 160px;"/></td>
                </tr>
                <tr>
                    <td><input id="accountStatus" name="accountingStatus" class="easyui-combobox" label="记账状态" labelWidth="60" style="width: 160px;"/></td>
                    <td colspan="4">
                        <input id="beginSettleDate" class="easyui-datebox" name="beginSettleDate" label="结算日期" labelWidth="80" style="width: 180px;"/>
                        <input id="endSettleDate" class="easyui-datebox" name="endSettleDate" label="-" labelWidth="10" style="width: 110px;"/>
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
        <table id="settlement_detail_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>