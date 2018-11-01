<script src="/static/js/acct/rule.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 50px;padding: 10px;">
        <form id="acct_rule_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="sceneCode" class="easyui-combobox" label="场景：" labelWidth="80" style="width: 240px;"
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=sceneCode'"/>
                    </td>
                    <td><input name="tradeType" class="easyui-combobox" label="交易类型：" labelWidth="80" style="width: 190px;"
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=tradeType'"/>
                    </td>
                    <td><input name="financeType" class="easyui-combobox" label="金额类型：" labelWidth="80" style="width: 190px;"
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=financeType'"/>
                    </td>
                    <td><input name="status" class="easyui-combobox" label="状态：" labelWidth="80" style="width: 190px;"
                    	data-options="valueField:'id',textField:'text',url:'/acct/selectEnum/getData?type=ruleStatus'"/>
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
        <table id="acct_rule_dg"></table>
    </div>
</div>