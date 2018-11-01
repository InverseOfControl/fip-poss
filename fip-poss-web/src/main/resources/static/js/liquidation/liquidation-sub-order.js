$(function () {
    var dg = $("#liquidation_sub_dg");
    var searchFrom = $("#liquidation_sub_search_from");

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/liquidation-sub-order/list',
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
            {field: 'liquidationOrderId', title: '清算主订单号', width: 200},
            {field: 'id', title: '清算子订单号', width: 200},
            {field: 'paySubOrderId', title: '支付子订单号', width: 200},
            {field: 'channelOrderNo', title: '渠道流水单号', width: 200},
            {field: 'payMethod', title: '支付方式', width: 100},
            {field: 'payStatus', title: '支付状态', width: 100},
            {field: 'transCurrency', title: '交易币种', width: 100},
            {field: 'payOrgCode', title: '支付渠道', width: 100},
            {field: 'payCurrency', title: '支付币种', width: 100},
            {field: 'payAmount', title: '支付金额', width: 100},
            {field: 'accountingStatus', title: '记账状态', width: 100},
            {
                field: 'payCompleteTime', title: '支付完成时间', width: 150, formatter: function (value, row, index) {
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
        dg.datagrid('load', {});
    });
});