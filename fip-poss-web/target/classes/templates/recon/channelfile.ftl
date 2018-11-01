<script src="/static/js/recon/channelfile.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 45px;padding: 10px;">
        <form id="channel-file-form" class="searcher-form">
            <input name="startImportDate" class="easyui-datebox" style="width:160px" data-options="label:'导入日期：',labelWidth:'65',editable:true"> --
            <input name="endImportDate" class="easyui-datebox" style="width:95px" data-options="editable:true">
            <span class="datagrid-btn-separator" style="vertical-align: middle;display:inline-block;float:none"></span>
            <input class="easyui-combobox" name="orgCode" style="width:180px;" data-options="label:'渠道：',labelWidth:'45',valueField:'code',panelWidth:'auto', panelHeight:'auto',textField:'name',url:'/recon/channelfile/channels',editable:true,multiple:false"/>
            <span class="datagrid-btn-separator" style="vertical-align: middle;display:inline-block;float:none"></span>
            <input class="easyui-combobox" name="fileType" style="width:180px;" data-options="label:'文件类型：',labelWidth:'65',valueField:'code',panelWidth:'auto', panelHeight:'auto',textField:'message',url:'/recon/channelfile/fileType',editable:true,multiple:false"/>
            <span class="datagrid-btn-separator" style="vertical-align: middle;display:inline-block;float:none"></span>
            <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">查询</a>
            <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
            <a name="download"  class="easyui-linkbutton download" data-options="iconCls:'fa fa-download'">下载</a>
        </form>
    </div>
    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="channelFile_dg"></table>
    </div>
</div>
