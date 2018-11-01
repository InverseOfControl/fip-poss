$(function () {
var dg = $("#acct_freezing_dg");
var searchFrom = $("#acct_freezing_search_from");
dg.datagrid({
    url: '/acct/freezing/list',
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
        {field: 'titleNo',title: '科目号',hidden:'true'},
        {field: 'accountNo',title: '账户号',width: 60, align: 'center'},
        {field: 'userId',title: '用户号',width: 40, align: 'center'},
        {field: 'accountDesc',title: '账户类型',width: 40, align: 'center'},
        {field: 'accountCurrecny',title: '账户币种',width: 30, align: 'center'},
        {field: 'balanceAmount',title: '账户余额',width: 20, align: 'right'},
        {field: 'freezingAmount',title: '冻结金额',width: 20, align: 'right'},
        {field: 'availableAmount',title: '可用余额',width: 40, align: 'right'},
        {field: 'updateTime',title: '最后更新时间',width: 60,align: 'center'},
        {field: 'test',title: '操作',width: 30,align: 'center',formatter: function (value, row, index) {
        	return authToolBar({
        		"cmp_acct_freezing_freeze": '<a data-id="'+row.accountNo+'" data-titleno="'+row.titleNo+'" class="ctr ctr-freeze">冻结</a>',
        		"cmp_acct_freezing_unfreeze": '<a data-id="'+row.accountNo+'" data-titleno="'+row.titleNo+'" class="ctr ctr-unfreeze">解冻</a>'
        		}).join("");
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
dg.datagrid("getPanel").on('click', "a.ctr-freeze", function () {//冻结
	createForm.call(this, this.dataset.id,this.dataset.titleno,'freeze');
}).on('click', "a.ctr-unfreeze", function () {//解冻
	createForm.call(this, this.dataset.id,this.dataset.titleno,'unfreeze');
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


/**
 * 创建表单窗口
 *
 * @returns
 */
function createForm(accountNo,titleNo,freezeType) {
	if(titleNo == 220201 || titleNo == 220204){
	    var dialog = $("<div/>", {class: 'noflow'}).dialog({
	        title: 'freeze' == freezeType ? "冻结金额" : "解冻金额",
	        iconCls: 'fa ' + (accountNo ? "fa-edit" : "fa-plus-square"),
	        height: 430,
	        width: 360,
	        href: '/acct/freezing/form',
	        queryParams: {accountNo: accountNo,freezeType:freezeType},
	        modal: true,
	        onClose: function () {
	            $(this).dialog("destroy");
	        },
	        onLoad: function () {
	            form = $("#freezing-form");
	        },
	        buttons: [{
	            iconCls: 'fa fa-save',
	            text: '保存',
	            handler: function () {
	                if (form.form('validate')) {
	                    $.post("/acct/freezing/save", form.serialize(), function (res) {
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
	}else{
		$.messager.alert("系统提示", '非主账户不允许冻结/解冻');
	}
}

});