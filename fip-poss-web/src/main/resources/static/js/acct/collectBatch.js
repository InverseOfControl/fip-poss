$(function () {
var dg = $("#acct_collectBatch_dg");
var searchFrom = $("#acct_collectBatch_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/collectBatch/list',
    emptyMsg: "无信息",
    idField: "batchId",
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
        {field: 'batchId',title: '汇总id',width: 180, align: 'center'},
        {field: 'accountingDay',title: '会计日期',width: 80, align: 'center'},
        {field: 'countType',title: '汇总类型',width: 180, align: 'center'},
        {field: 'status',title: '汇总状态',width: 150, align: 'center'},
        {field: 'jobCount',title: '任务数',width: 150, align: 'center'},
        {field: 'doneJobCount',title: '已完成任务数',width: 150, align: 'center'},
        {field: 'createTime',title: '创建时间',width: 140,align: 'center'},
        {field: 'completeTime',title: '完成时间',width: 140,align: 'center'}
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
    setDaysPeriod(startDateEle,endDateEle,3);
    dg.datagrid('load', searchFrom.formToJson());
});

});