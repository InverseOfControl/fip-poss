$(function () {
var dg = $("#acct_pipeLog_dg");
var searchFrom = $("#acct_pipeLog_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,2);
dg.datagrid({
    url: '/acct/pipeLog/list',
    emptyMsg: "无信息",
    idField: "pipeLogId",
    fit: true,
    rownumbers: true,
    fitColumns: false,
    border: false,
    pagination: true,
    singleSelect: true,
    ignore: ['roles'],
    pageSize: 30,
    queryParams:{
    	'beginAccountingDay':startDateEle.datebox('getValue'),
    	'endAccountingDay':endDateEle.datebox('getValue')
    },
    columns: [[
        {field: 'requestId',title: '请求流水号',width: 250, align: 'center'},
        {field: 'accountingDay',title: '会计日期',width: 80, align: 'center'},
        {field: 'bookType',title: '记账类型',width: 60, align: 'center'},
        {field: 'status',title: '记账状态',width: 60, align: 'center'},
        {field: 'result',title: '记账结果描述',width: 120, align: 'center'},
        {field: 'sysTraceNo',title: '收单订单号',width: 180, align: 'center'},
        {field: 'merchantOrderId',title: '商户订单号',width: 180, align: 'center'},
        {field: 'txnTime',title: '记账时间',width: 140,align: 'center'},
        {field: 'orderType',title: '订单类型',width: 80, align: 'center'},
        {field: 'sysCode',title: '系统编码',width: 90, align: 'center'},
        {field: 'pipeLogId',title: '日志id',width: 160, align: 'center'},
        {field: 'content',title: '记账内容',width: 60, align: 'center'},
        {field: 'operator',title: '操作员',width: 80, align: 'center'},
        {field: 'createTime',title: '创建时间',width: 140, align: 'center'},
        {field: 'completeTime',title: '完成时间',width: 140, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    }
});

/**
 * 搜索区域事件
 */
searchFrom.on('click', 'a.searcher', function () {//检索
    dg.datagrid('load', searchFrom.formToJson());
}).on('click', 'a.reset', function () {//重置
    searchFrom.form('reset');
    setDaysPeriod(startDateEle,endDateEle,2);
    dg.datagrid('load', searchFrom.formToJson());
});

});