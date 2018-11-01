$(function () {
var dg = $("#acct_freezingDetail_dg");
var searchFrom = $("#acct_freezingDetail_search_from");
var startDateEle = searchFrom.find(":input[name='beginAccountingDay']");
var endDateEle = searchFrom.find(":input[name='endAccountingDay']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/freezingDetail/list',
    emptyMsg: "无信息",
    idField: "freezingId",
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
               {field: 'accountingDay',title: '会计日期',width: 80, align: 'center'},
               {field: 'freezingId',title: '流水号',width: 180, align: 'center'},
               {field: 'accountNo',title: '账户号',width: 180, align: 'center'},
               {field: 'currency',title: '币种',width: 50, align: 'center'},
               {field: 'freezingType',title: '冻结/解冻',width: 70, align: 'center'},
               {field: 'freezingSummary',title: '备注',width: 120, align: 'center'},
               {field: 'amount',title: '金额',width: 80, align: 'right'},
               {field: 'status',title: '状态',width: 80, align: 'center'},
               {field: 'remark',title: '失败原因',width: 180, align: 'center'},
               {field: 'operator',title: '操作员',width: 80, align: 'center'},
               {field: 'createTime',title: '创建时间',width: 130,align: 'center'},
               {field: 'completeTime',title: '完成时间',width: 130, align: 'center'}
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_freezingDetail_download": {
        	iconCls: 'fa fa-download',
        	text: "下载",
        	handler: function () {
        		window.location.href="/acct/freezingDetail/download?"+$("#acct_freezingDetail_search_from").serialize();
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