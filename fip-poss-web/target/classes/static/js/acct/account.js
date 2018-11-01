$(function () {
var dg = $("#acct_account_dg");
var searchFrom = $("#acct_account_search_from");
var startDateEle = searchFrom.find(":input[name='openStartTime']");
var endDateEle = searchFrom.find(":input[name='openEndTime']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/account/list',
    emptyMsg: "无信息",
    idField: "accountNo",
    fit: true,
    rownumbers: true,
    fitColumns: false,
    border: false,
    pagination: true,
    singleSelect: true,
    ignore: ['roles'],
    pageSize: 30,
    queryParams:{
    	'openStartTime':startDateEle.datebox('getValue'),
    	'openEndTime':endDateEle.datebox('getValue')
    },
    columns: [[
        {field: 'accountNo',title: '账户号',width: 150, align: 'center'},       
        {field: 'accountDesc',title: '账户类型',width: 160, align: 'center'},
        {field: 'accountCurrecny',title: '账户币种',width: 60, align: 'center'},
        {field: 'financeDirection',title: '资金方向',width: 60, align: 'center'},
        {field: 'inLimit',title: '止入',width: 40, align: 'center'},
        {field: 'outLimit',title: '止出',width: 40, align: 'center'},
        {field: 'negativePermit',title: '可用余额是否允许为负',width: 130, align: 'center'},
        {field: 'sysCode',title: '开通渠道',width: 80, align: 'center'},
        {field: 'userId',title: '用户号',width: 100, align: 'center'},
        {field: 'openTime',title: '开户时间',width: 150, align: 'center'},
        {field: 'updateTime',title: '更新时间',width: 150,align: 'center'},
        {field: 'test',title: '操作',width: 50,align: 'center',formatter: function (value, row, index) {
        	return authToolBar(
                    {"cmp_acct_account_update": '<a data-id="' + row.accountNo + '" class="ctr ctr-edit">修改</a>'}
                ).join("");
            }
        }
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
    setDaysPeriod(startDateEle,endDateEle,3);
    dg.datagrid('load', searchFrom.formToJson());
});


/**
 * 创建表单窗口
 *
 * @returns
 */
function createForm(accountNo) {
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
        title: "修改账户",
        iconCls: 'fa ' + (accountNo ? "fa-edit" : "fa-plus-square"),
        height: 400,
        width: 320,
        href: '/acct/account/form',
        queryParams: {accountNo: accountNo},
        modal: true,
        onClose: function () {
            $(this).dialog("destroy");
        },
        onLoad: function () {
            form = $("#account-form");
        },
        buttons: [{
            iconCls: 'fa fa-save',
            text: '保存',
            handler: function () {
                if (form.form('validate')) {
                    $.post("/acct/account/update", form.serialize(), function (res) {
                    	//ajaxResult(res);
                    	if (res.success) {
                    		dg.datagrid('reload');
                            dialog.dialog('close');
                    	}else {
                    		$.messager.alert("系统提示", res.message);
                    	}
                    });
                }
            }
        }]
    });
}

});