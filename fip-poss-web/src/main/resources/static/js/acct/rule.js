$(function () {
var dg = $("#acct_rule_dg");
var searchFrom = $("#acct_rule_search_from");
dg.datagrid({
    url: '/acct/rule/list',
    emptyMsg: "无信息",
    idField: "ruleId",
    fit: true,
    rownumbers: true,
    fitColumns: false,
    border: false,
    pagination: true,
    singleSelect: true,
    ignore: ['roles'],
    pageSize: 30,
    columns: [[
        {field: 'status',title: '状态',width: 40, align: 'center'},       
        {field: 'prodName',title: '产品',width: 70, align: 'center'},
        {field: 'sceneName',title: '场景',width: 140, align: 'center'},
        {field: 'tradeType',title: '交易类型',width: 100, align: 'center'},
        {field: 'financeType',title: '金额类型',width: 80, align: 'center'},
        {field: 'psCode',title: '支付服务码',width: 80, align: 'center'},
        {field: 'currencyType',title: '币种类型',width: 100, align: 'center'},
        {field: 'dcDirection',title: '借贷方向',width: 70, align: 'center'},
        {field: 'titleNo',title: '科目号',width: 60, align: 'center'},
        {field: 'titleName',title: '科目名称',width: 150, align: 'center'},
        {field: 'accountType',title: '账户类型',width: 60, align: 'center'},
        {field: 'createTime',title: '添加时间',width: 150, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    }
});


/**
 * 操作按钮绑定事件
 */
dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {// 编辑按钮事件
	createForm.call(this, this.dataset.id)
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