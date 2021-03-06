<script src="/static/js/charge/channelcharge.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true" >
    <div data-options="region:'north',border:false" style="height: 80px;padding: 10px;">
        <form id="channelcharge_search_from" class="searcher-form">
            <table>
                <tr>
                    <td><input name="channelCode" class="easyui-textbox field" label="渠道号：" labelWidth="60"  style="width:180px;"></td>
                    <td> <input name="channelName" class="easyui-textbox field" label="渠道名称：" labelWidth="80" style="width:250px;"></td>
                    <td> <input id="channelChargeScene" class="easyui-combobox" name="chargeScene" panelHeight="auto"  label="交易类型：" labelWidth="80" style="width:150px;"
                                data-options="panelHeight:'auto',valueField:'id',textField:'text',url:'/charge/selectEnum/getData?type=chargeScene'" /></td>
                    <td><input id="channelStatus" class="easyui-combobox" name="status"   label="状态：" labelWidth="50" style="width:120px;"
                               data-options="panelHeight:'auto',valueField:'id',textField:'text',url:'/charge/selectEnum/getData?type=chargeStatus'"/></td>
                    <td><a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a></td>
                    <td><a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="channelcharge_dg"></table>
    </div>
</div>
<!-- 创建dialog ，带滚动条 -->
<div id="channel_dlg">
</div>
