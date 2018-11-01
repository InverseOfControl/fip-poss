<script src="/static/js/account/titleBalanceCompare.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 100px;padding: 10px;" >
        <form id="titleBalanceCompare_search_from" class="searcher-form">
            <table>
                <tr>
                    <td>
                        <input name="startDay" label="会计日期:" labelWidth="80" style="width: 190px;"/>
                        <input name="endDay" label="-" labelWidth="10" style="width:120px;"/>
                    </td>
                    <td>
                        <input name="checkEntryResult"  class="easyui-combobox" label="核对结果：" labelWidth="80" style="width: 300px;"
                               data-options="valueField:'id',textField:'text',url:'/account/selectEnum/getData?type=checkEntryResult'"/>

                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="startCreateTime" label="创建日期:" labelWidth="80"  class="easyui-datebox" style="width: 190px;">
                        <input name="endCreateTime" label="-"  labelWidth="10" class="easyui-datebox" style="width:120px;" >
                    </td>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                </tr>

                <tr>
                <tr>
                    <td>
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
                    </td>
                    <td>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                    </td>
                    <td>
                        <a name="download" class="easyui-linkbutton download"  name="download"
                           data-options="iconCls:'fa fa-download'">下载</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="titleBalanceCompare_dg"></table>
    </div>
</div>
