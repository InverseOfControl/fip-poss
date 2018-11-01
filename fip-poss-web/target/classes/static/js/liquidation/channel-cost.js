$(function () {
    var dg = $("#channel_cost_dg");
    var searchFrom = $("#channel_cost_search_form");

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/channel-cost/list',
        emptyMsg: "未查询到数据",
        idField: "id",
        rownumbers: true,
        fit: true,
        fitColumns: false,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 10,
        columns: [[
            {field: 'orgCode', title: '渠道', width: 70},
            {field: 'orgSubCode', title: '子渠道', width: 70},
            {field: 'channelOrderId', title: '渠道流水号', width: 150},
            {field: 'payMethod', title: '支付方式', width: 70},
            {field: 'transType', title: '交易类型', width: 70},
            {field: 'transStatus', title: '交易状态', width: 70},
            {field: 'payCurrency', title: '支付币种', width: 70},
            {field: 'payAmount', title: '支付金额', width: 70},
            {field: 'percentFeeAmount', title: '比例费金额', width: 70},
            {field: 'fixedFeeCurrency', title: '固定费币种', width: 70},
            {field: 'fixedFeeAmount', title: '固定费金额', width: 70},
            {field: 'feeCurrency', title: '处理费币种', width: 70},
            {field: 'feeAmount', title: '处理费金额', width: 70},
            {field: 'chargeCostMethod', title: '收取方式', width: 60},
            {field: 'costSettleCurrency', title: '结算币种', width: 60},
            {field: 'costSettleDate', title: '结算日期', width: 70},
            {
                field: 'channelCompleteTime', title: '交易完成时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            }
        ]]
    });

    // 支付方式
    selectData("payMethod");
    // 交易类型
    selectData("transType");
    // 交易状态
    selectData("transStatus");
    // 交易币种
    selectData("currencyType");
    // 收费方式
    selectData("chargeMethod");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/channel-cost/download";
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