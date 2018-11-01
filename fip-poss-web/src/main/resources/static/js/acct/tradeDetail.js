$(function () {
var dg = $("#acct_tradeDetail_dg");
var searchFrom = $("#acct_tradeDetail_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,2);
dg.datagrid({
    url: '/acct/tradeDetail/list',
    emptyMsg: "无信息",
    idField: "detailId",
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
        {field: 'requestId',title: '请求流水号',width: 160, align: 'center'},
        {field: 'userId',title: '商户号',width: 90, align: 'center'},
        {field: 'accountingDay',title: '会计日期',width: 80, align: 'center'},      
        {field: 'accountNo',title: '账户号',width: 150, align: 'center'},      
        {field: 'currency',title: '币种',width: 50, align: 'center'},
        {field: 'amount',title: '收支金额',width: 80, align: 'right'},
        {field: 'sysTraceNo',title: '收单订单号',width: 180, align: 'center'},
        {field: 'merchantOrderId',title: '商户订单号',width: 180, align: 'center'},
        {field: 'orderType',title: '订单类型',width: 80, align: 'center'},
        {field: 'financeType',title: '金额类型',width: 80, align: 'center'},
        {field: 'orgCode',title: '渠道号',width: 80, align: 'center'},
        {field: 'psCode',title: '支付服务码',width: 70, align: 'center'},
        {field: 'bookType',title: '记账类型',width: 60, align: 'center'},
        {field: 'createTime',title: '创建时间',width: 140, align: 'center'},
        {field: 'txnTime',title: '记账时间',width: 140,align: 'center'},
        {field: 'operator',title: '操作员',width: 80, align: 'center'},
        {field: 'sysCode',title: '系统CODE',width: 80, align: 'center'},
        {field: 'pipeLogId',title: '日志id',width: 160, align: 'center'},
        {field: 'detailId',title: '交易流水号',width: 160, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_tradeDetail_download": {
        	iconCls: 'fa fa-download',
        	text: "下载",
        	handler: function () {
        		window.location.href="/acct/tradeDetail/download?"+$("#acct_tradeDetail_search_from").serialize();
        	}
        }
    })
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