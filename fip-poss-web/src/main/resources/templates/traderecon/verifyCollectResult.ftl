<script src="/static/js/traderecon/verifyCollectResult.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 45px;padding: 10px;overflow: hidden;" >
        <form id="verifyCollectResult_search_from" class="searcher-form">

            <input id="orgCode" class="easyui-combobox" name="orgCode"
                   data-options="valueField:'code',textField:'message',
             url:'/recon/global/channelList'" label="渠道：" labelWidth="50"/>

            <input name="startImportDate" class="easyui-datebox" style="width:160px"
                   data-options="label:'导入日期：',labelWidth:'65',editable:true"/> --
            <input name="endImportDate" class="easyui-datebox" style="width:95px" data-options="editable:true"/>

            <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
            <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
            <a name="download" href="/recon/channelfile/download" class="easyui-linkbutton download"
               data-options="iconCls:'fa fa-download'">下载</a>
        </form>
    </div>

    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="verifyCollectResult_dg"></table>
    </div>
</div>
