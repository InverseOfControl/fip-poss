<script src="/static/js/liquidation/liquidation-order.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="liquidation_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="70" style="width: 170px;"/></td>
                    <td><input id="transType" name="transType" class="easyui-combobox" label="交易类型" labelWidth="70" style="width: 170px;"/></td>
                    <td><input id="transStatus" name="transStatus" class="easyui-combobox" label="订单状态" labelWidth="70" style="width: 170px;"/></td>
                    <td><input id="payMethod" name="payMethod" class="easyui-textbox" label="支付方式" labelWidth="80" style="width: 180px;"/></td>
                    <td><input id="currencyType" name="transCurrency" class="easyui-combobox" label="订单币种" labelWidth="60" style="width: 160px;"/></td>
                </tr>
                <tr>
                    <td><input name="merchantOrderId" class="easyui-textbox field" label="商户订单号" labelWidth="70" style="width: 170px;"/></td>
                    <td><input name="orderId" class="easyui-textbox" label="收单订单号" labelWidth="70" style="width: 170px;"/></td>
                    <td><input name="payOrderId" class="easyui-textbox" label="支付订单号" labelWidth="70" style="width: 170px;"/></td>
                    <td colspan="2">
                        <input id="beginPayCompleteTime" name="beginPayCompleteTime" label="订单完成日期" labelWidth="80" style="width: 180px;"/>
                        <input id="endPayCompleteTime" name="endPayCompleteTime" label="-" labelWidth="10" style="width:110px; "/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                        <a class="easyui-linkbutton download" data-options="iconCls:'fa fa-download'">下载</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="liquidation_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>