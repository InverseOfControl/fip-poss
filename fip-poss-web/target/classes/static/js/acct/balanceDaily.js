$(function () {
var dg = $("#acct_balanceDaily_dg");
var searchFrom = $("#acct_balanceDaily_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/balanceDaily/list',
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
        {field: 'accountingDay',title: '会计日期',width: 80, align: 'center'},
        {field: 'accountNo',title: '账户号',width: 150, align: 'center'},
        {field: 'currency',title: '币种',width: 50, align: 'center'},
        {field: 'openingBalance',title: '期初余额',width: 100, align: 'right'},
        {field: 'incomeAmount',title: '收入金额',width: 100, align: 'right'},
        {field: 'costAmount',title: '支出金额',width: 100, align: 'right'},
        {field: 'closingAmount',title: '期末余额',width: 100, align: 'right'},
        {field: 'createTime',title: '创建时间',width: 140, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_balanceDaily_download": {
        	iconCls: 'fa fa-download',
        	text: "下载",
        	handler: function () {
        		window.location.href="/acct/balanceDaily/download?"+$("#acct_balanceDaily_search_from").serialize();
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