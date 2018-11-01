<script src="/static/js/acct/collectBatch.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 50px;padding: 10px;">
        <form id="acct_collectBatch_search_from" class="searcher-form">
            <table>
                <tr>
                    <td>
                        <input name="beginAccountingDay" label="会计日期：" labelWidth="80" style="width: 210px;"/>
                        <input name="endAccountingDay" label="-" labelWidth="10" style="width:130px;"/>
                    </td>
                    <td><input name="countType" class="easyui-combobox" label="汇总类型：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=collectType'" style="width: 190px;"/>
                    </td>
                    <td><input name="status" class="easyui-combobox" label="汇总状态：" labelWidth="80" 
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=collectStatus'" style="width: 190px;"/>
                    </td>
                    <td>
                        <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
                        <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="acct_collectBatch_dg"></table>
    </div>
</div>