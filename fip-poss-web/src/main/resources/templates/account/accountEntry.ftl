<script src="/static/js/account/accountEntry.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 150px;padding: 10px;" >
        <form id="entry_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="titleNo" class="easyui-textbox" label="科目号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td><input name="accountNo" class="easyui-textbox" label="账户号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td>
                        <input name="currency"  class="easyui-combobox" label="币种：" labelWidth="80" style="width: 300px;"
                               data-options="valueField:'id',textField:'text',url:'/account/selectEnum/getData?type=currency'"/>
                    </td>
                </tr>
                <tr>
                    <td><input name="voucharNo" class="easyui-textbox" label="会计凭证号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td><input name="txnOrderNo" class="easyui-textbox" label="会计流水号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td>
                        <select name="drCr"  class="easyui-combobox" editable="false" data-options="panelHeight:'auto', required:true" label="借贷标志：" labelWidth="80" style="width: 300px;" >
                            <option value="">请选择</option>
                            <option value="DR">借</option>
                            <option value="CR">贷</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="orderType" class="easyui-combobox" label="交易类型：" labelWidth="80" style="width: 300px;"
                               data-options="valueField:'id',textField:'text',url:'/account/selectEnum/getData?type=orderType'"/>
                    </td>
                    <td>
                        <input name="financeType" class="easyui-combobox" label="金额类型：" labelWidth="80" style="width: 300px;"
                               data-options="valueField:'id',textField:'text',url:'/account/selectEnum/getData?type=financeType'"/>
                    </td>
                    <td>
                        <input name="orgCode" class="easyui-textbox" label="渠道号：" labelWidth="80" style="width: 300px;">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="startDay" label="会计日期:" labelWidth="80" style="width: 190px;"/>
                        <input name="endDay" label="-" labelWidth="10" style="width:120px;"/>
                    </td>
                    <td>
                        <input name="startCompletionTime" label="订单日期:" labelWidth="80"  class="easyui-datebox" style="width: 190px;">
                        <input name="endCompletionTime" label="-"  labelWidth="10" class="easyui-datebox" style="width:120px;" >
                    </td>
                </tr>
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
        <table id="entry_dg"></table>
    </div>
</div>
