$(function () {
    var dg = $("#settlement_order_dg");
    var searchFrom = $("#settlement_order_search_form");

    // 日期初始化
    var $beginDate = $('#orderBeginDate');
    var $endDate = $('#orderEndDate');
    initDatebox($beginDate, $endDate, false);
    initDate($beginDate, $endDate, "-6");

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/settlement-order/list',
        emptyMsg: "未查询到数据",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: false,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 10,
        columns: [[
            {field: 'merchantId', title: '商户号', width: 90},
            {field: 'merchantName', title: '商户名称', width: 90},
            {field: 'settlementOrderId', title: '结算单号', width: 150},
            {field: 'settlementDate', title: '结算单日期', width: 90},
            {field: 'transType', title: '交易类型', width: 90},
            {field: 'amountType', title: '金额类型', width: 90},
            {field: 'transCurrency', title: '订单币种', width: 90},
            {field: 'transAmount', title: '订单金额', width: 90},
            {field: 'settleCurrency', title: '结算币种', width: 90},
            {field: 'settleAmount', title: '结算金额', width: 90},
            {field: 'settlementSummaryId', title: '结算汇总单号', width: 150},
            {field: 'summaryStatus', title: '汇总状态', width: 90},
            {field: 'accountingStatus', title: '记账状态', width: 90},
            {
                field: 'accountingTime', title: '记账时间', width: 150, formatter: function (value, row, index) {
                    if(value){
                        return new Date(value).toLocaleString();
                    }
                }
            },
            {
                field: 'gmtCreateTime', title: '创建时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            }
        ]],
    });

    // 交易类型
    selectData("transType");
    // 金额类型
    selectData("financeType");
    // 交易币种
    selectData("currencyType0");
    // 结算币种
    selectData("currencyType1");
    // 记账状态
    selectData("accountStatus");
    // 汇总状态
    selectData("summaryStatus");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        initDate($beginDate, $endDate, "-6");
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/settlement-order/download";
        createTempForm(url, tempDiv, rows, searchFrom);
    })

    function selectData(type) {
        searchFrom.find("input[id='" + type + "']").combobox({
            url: '/select/getData?type=' + type,
            valueField: 'id',
            textField: 'text'
        });
    }

});
