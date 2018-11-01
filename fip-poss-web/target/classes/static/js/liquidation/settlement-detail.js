$(function () {
    var dg = $("#settlement_detail_dg");
    var searchFrom = $("#settlement_detail_search_form");

    // 日期初始化
    var $beginDate = $('#beginSettleDate');
    var $endDate = $('#endSettleDate');
    initDatebox($beginDate, $endDate, false);
    initDate($beginDate, $endDate, -1);

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/settlement-detail/list',
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
            {field: 'id', title: '结算明细单号', width: 150},
            {field: 'merchantId', title: '商户号', width: 90},
            {field: 'merchantName', title: '商户名称', width: 90},
            {field: 'merchantOrderId', title: '商户订单号', width: 150},
            {field: 'orderId', title: '收单订单号', width: 150},
            {field: 'transType', title: '交易类型', width: 80},
            {field: 'amountType', title: '金额类型', width: 80},
            {field: 'payMethod', title: '支付方式', width: 80},
            {field: 'feeSettleMethod', title: '费用结算方式', width: 80},
            {field: 'transCurrency', title: '订单币种', width: 70},
            {field: 'transAmount', title: '订单金额', width: 70},
            {field: 'settleCurrency', title: '结算币种', width: 70},
            {field: 'settleAmount', title: '结算金额', width: 70},
            {field: 'settleRate', title: '结算汇率', width: 70},
            {field: 'settleDate', title: '结算日期', width: 70},
            {field: 'settleStatus', title: '结算状态', width: 70},
            {field: 'accountingStatus', title: '记账状态', width: 70},
            {field: 'settlementId', title: '结算单号', width: 150},
            {
                field: 'settleTime', title: '订单完成时间', width: 150, formatter: function (value, row, index) {
                    if(value){
                        return new Date(value).toLocaleString();
                    }
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
    // 费用结算方式
    selectData("chargeMethod");
    // 结算币种
    selectData("currencyType1");
    // 结算状态
    selectData("settleStatus");
    // 记账状态
    selectData("accountStatus");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        initDate($beginDate, $endDate, -1);
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/settlement-detail/download";
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
