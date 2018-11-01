<script src="/static/js/traderecon/settleTransDetail.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 45px;padding: 10px;overflow: hidden;">
        <form id="settleTransDetail_search_from" class="searcher-form">

            <input name="channelOrderId" class="easyui-textbox field" label="渠道订单号：" labelWidth="80">
            <input id="reconResult" class="easyui-combobox" name="reconResult" style="width:180px;"
                   data-options="valueField:'code',textField:'message',panelWidth:'auto', panelHeight:'auto',
        url:'/recon/channelfile/reconHandleResultList',editable:true,multiple:false" label="对账状态：" labelWidth="65"/>

            <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>

            <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
            
            <a name="download" href="/recon/channelfile/download" class="easyui-linkbutton download"
               data-options="iconCls:'fa fa-download'">下载</a>
        </form>
    </div>

    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="settleTransDetail_dg"></table>
    </div>
</div>
