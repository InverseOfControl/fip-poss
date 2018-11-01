<script src="/static/js/paycore/blacklist.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="pay_blacklist_form" class="searcher-form">
            <table>
                <tr>
                    <td><input name="merchantId" class="easyui-textbox" label="商户号" labelWidth="60" style="width: 200px;"/></td>
                    <td><input id="transType" name="tradeType" class="easyui-combobox" label="交易类型" labelWidth="60" style="width: 180px;"/></td>
                    <td><input id="currencyType" name="currency" class="easyui-combobox" label="交易币种" labelWidth="60" style="width: 180px;"/></td>
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
        <table id="pay_blacklist_dg"></table>
    </div>
</div>