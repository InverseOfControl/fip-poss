<script src="/static/js/liquidation/mert-settlement-config.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="padding: 10px;">
        <form id="mert_settlement_config_search_from" class="searcher-form">
            <td><input name="merchantId" class="easyui-textbox field" label="商户号" labelWidth="50" style="width: 150px;"/></td>
            <td><input name="merchantName" class="easyui-textbox field" label="商户名称" labelWidth="60" style="width: 160px;"/></td>
            <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
            <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="mert_settlement_config_dg"></table>
    </div>
</div>
