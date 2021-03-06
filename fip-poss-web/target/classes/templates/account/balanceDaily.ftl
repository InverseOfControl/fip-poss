<script src="/static/js/account/balanceDaily.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 100px;padding: 10px;" >
        <form id="balance_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="titleNo" class="easyui-textbox" label="科目号：" labelWidth="80" style="width: 300px;"/>
                    </td>
                    <td>
                        <input name="currency"  class="easyui-combobox" label="*币种：" labelWidth="80" style="width: 300px;"
                               data-options="valueField:'id',textField:'text',url:'/account/selectEnum/getData?type=currency'"/>

                    </td>
                    <td>
                        <select name="checkBalance"  class="easyui-combobox" editable="false" data-options="panelHeight:'auto', required:true" label="试算结果：" labelWidth="80" style="width: 300px;" >
                            <option value="">请选择</option>
                            <option value="Y">平衡</option>
                            <option value="N">不平衡</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input name="startDay" label="会计日期:" labelWidth="80" style="width: 190px;"/>
                        <input name="endDay" label="-" labelWidth="10" style="width:120px;"/>
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
        <table id="balance_dg"></table>
    </div>
</div>
