<script src="/static/js/liquidation/liquidation-sub-order.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="liquidation_sub_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="liquidationOrderId" class="easyui-textbox" label="清算主订单号" labelWidth="80" style="width: 180px;"/></td>
                    <td><input name="id" class="easyui-textbox" label="清算子订单号" labelWidth="80" style="width: 180px;"/></td>
                    <td><input name="channelOrderNo" class="easyui-textbox" label="渠道流水单号" labelWidth="80" style="width: 180px;"/></td>
                    <td>
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="liquidation_sub_dg"></table>
    </div>
</div>
