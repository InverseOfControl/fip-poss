$(function () {
var dg = $("#acct_banace_dg");
var searchFrom = $("#acct_banace_search_from");
dg.datagrid({
    url: '/acct/balance/list',
    emptyMsg: "无信息",
    idField: "accountNo",
    fit: true,
    rownumbers: true,
    fitColumns: true,
    border: false,
    pagination: true,
    singleSelect: true,
    ignore: ['roles'],
    pageSize: 30,
    columns: [[
        {field: 'userId',title: '用户号',width: 50, align: 'center'},
        {field: 'userName',title: '用户名称',width: 60, align: 'center'},
        {field: 'accountNo',title: '账户号',width: 80, align: 'center'},
        {field: 'accountDesc',title: '账户类型',width: 80, align: 'center'},
        {field: 'accountCurrecny',title: '账户币种',width: 30, align: 'center'},
        {field: 'balanceAmount',title: '余额',width: 40, align: 'right'},
        {field: 'freezingAmount',title: '冻结金额',width: 40, align: 'right'},
        {field: 'availableAmount',title: '可用余额',width: 40, align: 'right'},
        {field: 'createTime',title: '开户时间',width: 70, align: 'center'},
        {field: 'updateTime',title: '最后更新时间',width: 70,align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_balance_download": {
        	iconCls: 'fa fa-download',
        	text: "下载",
        	handler: function () {
        		window.location.href="/acct/balance/download?"+$("#acct_balance_search_from").serialize();
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
    dg.datagrid('load', {});
});

});