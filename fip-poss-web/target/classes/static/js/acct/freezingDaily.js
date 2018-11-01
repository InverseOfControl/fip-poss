$(function () {
var dg = $("#acct_freezingDaily_dg");
var searchFrom = $("#acct_freezingDaily_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/freezingDaily/list',
    emptyMsg: "无信息",
    idField: "id",
    fit: true,
    rownumbers: true,
    fitColumns: true,
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
        {field: 'accountingDay',title: '会计日期',width: 50, align: 'center'},
        {field: 'accountNo',title: '账户号',width: 90, align: 'center'},
        {field: 'currency',title: '币种',width: 30, align: 'center'},
        {field: 'openingFreezingBalance',title: '期初冻结金额',width: 60, align: 'right'},
        {field: 'freezingAmount',title: '当日冻结金额',width: 60, align: 'right'},
        {field: 'unfreezingAmount',title: '当日解冻金额',width: 60, align: 'right'},
        {field: 'closingFreezingAmount',title: '期末冻结金额',width: 60, align: 'right'},
        {field: 'createTime',title: '创建时间',width: 70, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_freezingDaily_download": {
        	iconCls: 'fa fa-download',
        	text: "下载",
        	handler: function () {
        		window.location.href="/acct/freezingDaily/download?"+$("#acct_freezingDaily_search_from").serialize();
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
    setDaysPeriod(startDateEle,endDateEle,3);
    dg.datagrid('load', searchFrom.formToJson());
});

});