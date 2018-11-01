$(function () {
    var dg = $("#mert_fee_dg");
    var searchFrom = $("#mert_fee_search_form");

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/mert-fee/list',
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
            {field: 'id', width: '5%', hidden: true},
            {field: 'merchantId', title: '商户号', width: 90},
            {field: 'merchantName', title: '商户名称', width: 90},
            {field: 'merchantOrderId', title: '商户订单号', width: 150},
            {field: 'orderId', title: '收单订单号', width: 150},
            {field: 'transType', title: '订单类型', width: 80},
            {field: 'amountType', title: '费用类型', width: 70},
            {field: 'transCurrency', title: '订单币种', width: 70},
            {field: 'transAmount', title: '订单金额', width: 70},
            {field: 'feeCurrency', title: '费用币种', width: 70},
            {field: 'feeAmount', title: '费用金额', width: 70},
            {field: 'feeSettleCurrency', title: '费用结算币种', width: 90},
            {field: 'feeSettleAmount', title: '费用结算金额', width: 90},
            {field: 'orderDate', title: '订单日期', width: 80},
            {field: 'settleDate', title: '结算日期', width: 80},
            {field: 'settleRate', title: '结算汇率', width: 70},
            {field: 'feeSettleMethod', title: '收费方式', width: 70},
            {field: 'settleStatus', title: '结算状态', width: 70},
            {
                field: 'operate', title: '操作', width: 70, formatter: function (value, row, index) {
                    if (row.feeSettleMethod == "外收" && row.settleStatus == "未结算") {
                        return authToolBar({
                            "mert-fee-query-charge": '<a data-id="' + row.id + '" class="ctr ctr-edit">已收费</a>'
                        }).join("");
                    }
                }
            },
        ]],
    });

    // 交易币种
    selectData("transType");
    // 费用类型
    selectData("financeType");
    // 币种类型
    selectData("currencyType");
    // 收费方式
    selectData("chargeMethod");
    // 结算状态
    selectData("settleStatus");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        dg.datagrid("load", {});
    });

    // 已收费事件
    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {
        var id = this.dataset.id;
        $.post("/liquidation/mert-fee/charge", {'id': id}, function (data) {
            layer.open({
                title: '提示'
                , content: '收费成功'
            });
            dg.datagrid('reload');
        })
    })

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/mert-fee/download";
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
