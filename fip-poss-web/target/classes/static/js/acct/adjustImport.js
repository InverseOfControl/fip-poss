$(function () {
var dg = $("#acct_adjustImport_dg");
var searchFrom = $("#acct_adjustImport_search_from");
var startDateEle = searchFrom.find(":input[name='beginApplyDate']");
var endDateEle = searchFrom.find(":input[name='endApplyDate']");
setDaysPeriod(startDateEle,endDateEle,3);
dg.datagrid({
    url: '/acct/adjustImport/list',
    emptyMsg: "无信息",
    idField: "adjustId",
    fit: true,
    rownumbers: true,
    fitColumns: false,
    border: false,
    pagination: true,
    singleSelect: true,
    ignore: ['roles'],
    pageSize: 30,
    queryParams:{
    	'beginApplyDate':startDateEle.datebox('getValue'),
    	'endApplyDate':endDateEle.datebox('getValue')
    },
    columns: [[
        {field: 'batchId',title: '批次号',width: 100, align: 'center'},
        {field: 'accountNo',title: '账户号',width: 150, align: 'center'},
        {field: 'dcDirection',title: '借贷方向',width: 60, align: 'center'},
        {field: 'currency',title: '币种',width: 40, align: 'center'},
        {field: 'amount',title: '金额',width: 80, align: 'right'},
        {field: 'adjustSummary',title: '摘要',width: 120, align: 'left'},
        {field: 'orgCode',title: '机构号',width: 80, align: 'center'},
        {field: 'sysTraceNo',title: '收单订单号',width: 120, align: 'center'},
        {field: 'pipeLogId',title: '记账日ID',width: 150, align: 'center'},
        {field: 'accountingDay',title: '会计日',width: 70, align: 'center'},
        {field: 'accountingFlag',title: '记账是否成功',width: 80, align: 'center'},
        {field: 'adjustId',title: '调账订单号',width: 100, align: 'center'},
        {field: 'adjustOperator',title: '提交人',width: 70, align: 'center'},
        {field: 'createTime',title: '提交时间',width: 130, align: 'center'},
        {field: 'adjustCommitor',title: '审核人',width: 70, align: 'center'},
        {field: 'updateTime',title: '审核时间',width: 130, align: 'center'},
        {field: 'auditStatusDesc',title: '审核状态',width: 80, align: 'center'},
        {field: 'commitSummary',title: '审核摘要',width: 100, align: 'center'},
        {field: 'test',title: '操作',width: 120,align: 'center',formatter: function (value, row, index) {
        	var toolBar = {};
        	if(typeof(row.voucherOssToken) != "undefined" && row.voucherOssToken != ''){
        		toolBar.cmp_acct_adjustImport_downloadVoucher = '<a data-id="' + row.adjustId + '" class="ctr ctr-download">下载凭证</a>';
        	}
        	if(row.auditStatus == '0'){
        		toolBar.cmp_acct_adjustImport_uploadVoucher = '<a data-id="' + row.adjustId + '" class="ctr ctr-upload">上传凭证</a>';
        	}
        	return authToolBar(toolBar).join("");
          }
        }
    ]],
    onLoadSuccess:function(data){
    	if(data.errorMsg != null){
    		$.messager.alert("系统提示", data.errorMsg);
    	}
    },
    toolbar: authToolBar({
        "cmp_acct_adjustImport_downloadTemplate": {
        	iconCls: 'fa fa-download',
        	text: "调账模板下载",
        	handler: function () {
        		window.location.href="/acct/adjustImport/downloadTemplate";
        	}
        },
        "cmp_acct_adjustImport_importFile": {
        	iconCls: 'fa fa-upload',
        	text: "导入调账文件",
        	handler: function () {
        		importAdjustFile();
        	}
        }
    })
});


/**
 * 操作按钮绑定事件
 */
dg.datagrid("getPanel").on('click', "a.ctr-upload", function () {//上传凭证
	uploadVoucher(this.dataset.id);
}).on('click', "a.ctr-download", function () {//下载凭证
	window.location.href="/acct/adjustImport/downloadVoucher?adjustId="+this.dataset.id;
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
 * 导入文件
 */
function importAdjustFile() {
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
        title: "请选择调账文件",
        iconCls: "fa fa-plus-square",
        height: 260,
        width: 420,
        href: '/acct/adjustImport/importFile',
        modal: true,
        onClose: function () {
            $(this).dialog("destroy");
        },
        buttons: [{
            iconCls: 'fa fa-save',
            text: '保存',
            handler: function () {
            	var fileName = $("#file").filebox('getValue');
            	if(fileName == ''){
            		$.messager.alert("系统提示", '请选择调账文件');
            		return 
            	}
                $('#adjustImport-importFile-form').form('submit', {
                    type: "post",
                    url: "/acct/adjustImport/doImport",
                    dateType: "json",
                    success: function (res) {
                    	var result = $.parseJSON(res);
                    	if(!result.success){
                    		$.messager.alert("系统提示", result.message);
                    	}
                    	dg.datagrid('reload', searchFrom.formToJson());
                        dialog.dialog('close');
                    }
                });
            }
        }],
    });
}
function uploadVoucher(adjustId) {
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
        title: "上传调账凭证",
        iconCls: "fa fa-plus-square",
        height: 260,
        width: 420,
        href: '/acct/adjustImport/uploadVoucher',
        queryParams: {adjustId: adjustId},
        modal: true,
        onClose: function () {
            $(this).dialog("destroy");
        },
        buttons: [{
            iconCls: 'fa fa-save',
            text: '保存',
            handler: function () {
            	var fileName = $("#file").filebox('getValue');
            	if(fileName == ''){
            		$.messager.alert("系统提示", '请选择文件');
            		return 
            	}
                $('#adjustImport_uploadVoucher_form').form('submit', {
                    type: "post",
                    url: "/acct/adjustImport/doUploadVoucher",
                    dateType: "json",
                    success: function (res) {
                    	var result = $.parseJSON(res);
                    	if (result.success) {
                    		dg.datagrid('reload');
                            dialog.dialog('close');
                    	}else {
                    		$.messager.alert("系统提示", result.message);
                    	}
                    }
                });
                dg.datagrid('reload');
                dialog.dialog('close');
            }
        }]
    });
};

});
