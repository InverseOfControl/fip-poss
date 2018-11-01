<script src="/static/js/account/balanceAcctDaily.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 50px;padding: 10px;" >
        <form id="balanceAcctDaily_search_from" class="searcher-form" style="width: 80%">
            <table>
                <tr>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td>
                        <input name="startDay" label="会计日期:" labelWidth="80" style="width: 190px;"/>
                        <input name="endDay" label="-" labelWidth="10" style="width:120px;"/>
                    </td>
                    <td>
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                        <a name="download" class="easyui-linkbutton download"  name="download"
                           data-options="iconCls:'fa fa-download'">下载</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="balanceAcctDaily_dg"></table>
    </div>
</div>
