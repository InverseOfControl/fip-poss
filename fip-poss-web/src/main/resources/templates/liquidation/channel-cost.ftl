<script src="/static/js/liquidation/channel-cost.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="channel_cost_search_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="orgCode" class="easyui-textbox" label="渠道" labelWidth="60" style="width:160px;"/></td>
                    <td><input name="orgSubCode" class="easyui-textbox" label="子渠道" labelWidth="60" style="width:160px;"/></td>
                    <td><input id="payMethod" name="payMethod" class="easyui-combobox" label="支付方式" labelWidth="60" style="width:160px;"/></td>
                    <td>
                        <input id="beginCompleteDate" name="beginChannelCompleteTime" class="easyui-datebox" label="交易完成日期" labelWidth="80" style="width: 180px;"/>
                        <input id="endCompleteDate" name="endChannelCompleteTime" class="easyui-datebox" label="-" labelWidth="10" style="width: 110px;"/>
                    </td>
                    <td><input id="transType" name="transType" class="easyui-combobox" label="交易类型" labelWidth="70" style="width:170px;"/></td>
                </tr>
                <tr>
                    <td><input id="transStatus" name="transStatus" class="easyui-combobox" label="交易状态" labelWidth="60" style="width:160px;"/></td>
                    <td><input id="currencyType" name="payCurrency" class="easyui-combobox" label="交易币种" labelWidth="60" style="width:160px;"/></td>
                    <td><input id="chargeMethod" name="chargeCostMethod" class="easyui-combobox" label="收费方式" labelWidth="60" style="width:160px;"/></td>
                    <td>
                        <input id="beginSettleDate" name="beginCostSettleDate" class="easyui-datebox" label="结算日期" labelWidth="80" style="width: 180px;"/>
                        <input id="endSettleDate" name="endCostSettleDate" class="easyui-datebox" label="-" labelWidth="10" style="width: 110px;"/>
                    </td>
                    <td><input name="channelOrderId" class="easyui-textbox" label="渠道流水号" labelWidth="70" style="width:170px;"/></td>
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
        <table id="channel_cost_dg"></table>
    </div>
</div>
<div id="tempDiv"></div>