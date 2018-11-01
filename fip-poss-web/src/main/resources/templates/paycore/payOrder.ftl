<script src="/static/js/paycore/payOrder.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="pay_order_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="70" style="width: 200px;"/></td>
                    <td><input id="transType" name="tradeType" class="easyui-combobox" label="交易类型" labelWidth="70" style="width: 220px;"/></td>
                    <td><input id="bizStatus" name="payStatus" class="easyui-combobox" label="支付状态" labelWidth="70" style="width: 240px;"/></td>
                    <td><input id="currencyType" name="currency" class="easyui-combobox" label="交易币种" labelWidth="60" style="width: 170px;"/></td>
                    <#--<td><input id="payMethod" name="payType" class="easyui-combobox" label="支付类型" labelWidth="50" style="width: 150px;"/></td>-->
                    <td>支付类型&nbsp;&nbsp;<select class="easyui-combobox" name="payType" style="width:100px;">
                        <option value=""></option>
                        <option value="EDC">EDC</option>
                        <option value="DCC">DCC</option>
                    </select></td>
                </tr>
                <tr>
                    <td><input name="merchantOrderId" class="easyui-textbox" label="商户订单号" labelWidth="70" style="width: 200px;"/></td>
                    <td><input name="acquireOrderId" class="easyui-textbox" label="收单订单号" labelWidth="70" style="width: 220px;"/></td>
                    <td><input name="paymentOrderId" class="easyui-textbox" label="支付订单号" labelWidth="70" style="width: 240px;"/></td>
                    <#--<td><input id="payMode" name="payMode" class="easyui-combobox" label="支付模式" labelWidth="70" style="width: 170px;"/></td>-->
                    <td colspan="2">
                        <input name="tradeDateBegin" label="交易日期" labelWidth="60" style="width: 200px;"/>
                        <input name="tradeDateEnd" label="-" labelWidth="10" style="width:150px;"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="5">
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                        <#--<a class="easyui-linkbutton download" data-options="iconCls:'fa fa-download'">下载</a>-->
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="pay_order_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>