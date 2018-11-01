<script src="/static/js/acct/account.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 80px;padding: 10px;">
        <form id="acct_account_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="userId" class="easyui-textbox field" label="用户号：" labelWidth="80" style="width: 190px;"/></td>
                    <td><input name="accountCurrecny" class="easyui-combobox" label="账户币种：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=currency'" style="width: 190px;"/>
                    </td>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 250px;"/></td>
                </tr>
                <tr>
                    <td><input name="titleNo" class="easyui-textbox field" label="科目号：" labelWidth="80" style="width: 190px;"/></td>
                    <td colspan="2">
                        <input name="openStartTime" label="开户日期：" labelWidth="80" style="width: 210px;"/>
                        <input name="openEndTime" label="-" labelWidth="10" style="width:130px;"/>
                    </td>
                    <td colspan="3">
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="acct_account_dg"></table>
    </div>
</div>