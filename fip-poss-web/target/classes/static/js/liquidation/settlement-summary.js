$(function () {
    var dg = $("#settlement_summary_dg");
    var searchFrom = $("#settlement_summary_search_form");

    // 日期初始化
    var $beginDate = $('#beginDate');
    var $endDate = $('#endDate');
    initDatebox($beginDate, $endDate, false);
    initDate($beginDate, $endDate, -6);

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/settlement-summary/list',
        emptyMsg: "未查询到数据",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: false,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 10,
        queryParams: {
            'beginDate': $beginDate.val(),
            'endDate': $endDate.val(),
        },
        columns: [[
            {field: 'merchantId', title: '商户号', width: 100},
            {field: 'merchantName', title: '商户名称', width: 100},
            {field: 'summaryDate', title: '结算汇总单日期', width: 100},
            {field: 'summaryOrderId', title: '结算汇总单号', width: 150},
            {field: 'summaryCurrency', title: '结算币种', width: 100},
            {field: 'summaryAmount', title: '结算金额', width: 100},
            {field: 'autoWithdrawFlag', title: '是否自动提现', width: 100},
            {field: 'withdrawStatus', title: '自动提现状态', width: 100},
            {
                field: 'gmtCreateTime', title: '创建时间', width: 150, formatter: function (value) {
                    return new Date(value).toLocaleString();
                }
            },
            {
                field: 'gmtUpdateTime', title: '最后更新时间', width: 150, formatter: function (value) {
                    return new Date(value).toLocaleString();
                }
            }
        ]],
    });

    // 交易币种
    selectData("currencyType");
    // 是否自动提现
    selectData("autoWithdrawal");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        initDate($beginDate, $endDate, -6);
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/settlement-summary/download";
        createTempForm(url, tempDiv, rows, searchFrom);
    })

    function selectData(type){
        searchFrom.find("input[id='"+type+"']").combobox({
            url:'/select/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }

});
