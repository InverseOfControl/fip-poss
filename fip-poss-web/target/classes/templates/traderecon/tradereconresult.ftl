<script>
    <#if tradeRecon??>
    $(function () {
        //需要延迟一点执行，等待页面所有组件都初始化好，再执行数据初始化
        var result = ${tradeRecon};
        $("#tradereconresult_search_from").form("load", result);
    });
    </#if>
</script>
<script src="/static/js/traderecon/tradereconresult.js" charset="utf-8"></script>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="height: 45px;padding: 10px;overflow: hidden;">
        <form id="tradereconresult_search_from" class="searcher-form">

            <input name="channelOrderId" class="easyui-textbox field" label="渠道订单号：" labelWidth="80">
            <input id="batchNo" name="batchNo" class="easyui-textbox field" label="批次号：" labelWidth="70">

            <input id="orgCode" class="easyui-combobox" name="orgCode" style="width:180px;"
                   data-options="valueField:'code',textField:'message',panelWidth:'auto', panelHeight:'auto',
             url:'/recon/global/channelList'" label="渠道：" labelWidth="65"/>

            <input id="reconResult" class="easyui-combobox" name="reconResult" style="width:180px;"
                   data-options="valueField:'code',textField:'message',panelWidth:'auto', panelHeight:'auto',
        url:'/recon/channelfile/reconResultList',editable:true,multiple:false" label="对账结果：" labelWidth="65"/>

            <input id="correctionType" class="easyui-combobox" name="correctionType" style="width:180px;"
                   data-options="valueField:'code',textField:'message',panelWidth:'auto', panelHeight:'auto',
        url:'/recon/global/correctionTypeList',editable:true,multiple:false" label="差错处理方式：" labelWidth="90"/>

            <input id="auditStatus" class="easyui-combobox" name="auditStatus" style="width:180px;"
                   data-options="valueField:'code',textField:'message',panelWidth:'auto', panelHeight:'auto',
        url:'/recon/global/auditStatusList',editable:true,multiple:false" label="审核状态：" labelWidth="75"/>

            <a class="easyui-linkbutton searcher" data-options="iconCls:'fa fa-search'">检索</a>
            <a class="easyui-linkbutton reset" data-options="iconCls:'fa fa-repeat'">重置</a>
        </form>
    </div>

    <div data-options="region:'center',border:false" style="border-top: 1px solid #D3D3D3">
        <table id="tradereconresult_dg"></table>
    </div>
</div>

