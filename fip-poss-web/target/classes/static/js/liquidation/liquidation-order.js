$(function () {
    var dg = $("#liquidation_dg");
    var searchFrom = $("#liquidation_search_from");

    // 日期初始化
    var $beginDate = $('#beginPayCompleteTime');
    var $endDate = $('#endPayCompleteTime');
    initDatebox($beginDate, $endDate, false);
    initDate($beginDate, $endDate, -1);

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/liquidation-order/list',
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
            {field: 'id', title: '清算主订单号', width: 180},
            {field: 'merchantId', title: '商户号', width: 90},
            {field: 'merchantName', title: '商户名称', width: 90},
            {field: 'transType', title: '交易类型', width: 90},
            {field: 'transStatus', title: '订单状态', width: 90},
            {field: 'payMethod', title: '支付方式', width: 90},
            {field: 'merchantOrderId', title: '商户订单号', width: 150},
            {field: 'orderId', title: '收单订单号', width: 150},
            {field: 'payOrderId', title: '支付订单号', width: 180},
            {field: 'transCurrency', title: '订单币种', width: 90},
            {field: 'transAmount', title: '订单金额', width: 90},
            {field: 'oriPayOrderId', title: '原支付订单号', width: 180},
            {
                field: 'payCompleteTime', title: '订单完成时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            }
        ]],
    });

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        initDate($beginDate, $endDate, -1);
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 交易类型下拉框数据
    selectData("transType");
    // 订单状态
    selectData("transStatus");
    // 支付方式
    selectData("payMethod");
    // 币种类型
    selectData("currencyType");

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/liquidation-order/download";
        createTempForm(url, tempDiv, rows, searchFrom);
    })

    function selectData(type){
        searchFrom.find("input[id='"+type+"']").combobox({
            url:'/select/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }

})