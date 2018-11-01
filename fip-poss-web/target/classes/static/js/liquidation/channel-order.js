$(function () {
    var dg = $("#channel_dg");
    var searchFrom = $("#channel_search_from");

    // 日期初始化
    var $beginDate = $('#beginChannelCompleteTime');
    var $endDate = $('#endChannelCompleteTime');
    initDatebox($beginDate, $endDate, false);
    initDate($beginDate, $endDate, -1);

    // 加载表格数据
    dg.datagrid({
        url: '/liquidation/channel-order/list',
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
            'beginChannelCompleteTime': $beginDate.val(),
            'endChannelCompleteTime': $endDate.val(),
        },
        columns: [[
            {field: 'merchantId', title: '商户号', width: 100},
            {field: 'merchantName', title: '商户名称', width: 100},
            {field: 'transType', title: '交易类型', width: 100},
            {field: 'payMethod', title: '支付方式', width: 100},
            {field: 'transStatus', title: '交易状态', width: 100},
            {field: 'orgCode', title: '渠道', width: 100},
            {field: 'merchantOrderId', title: '商户订单号', width: 150},
            {field: 'channelOrderId', title: '渠道流水号', width: 150},
            {field: 'channelReturnId', title: '渠道返回流水号', width: 150},
            {field: 'payCurrency', title: '币种', width: 100},
            {field: 'payAmount', title: '金额', width: 100},
            {field: 'oriChannelReturnId', title: '原渠道流水号', width: 150},
            {
                field: 'channelCompleteTime', title: '交易完成时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            },
            {field: 'waterPushFlag', title: '流水是否推送', width: 80, formatter: function (value, row, index) {
                    var chargeCostFlag = row.chargeCostFlag;
                    var waterPushFlag = row.waterPushFlag;
                    // 渠道清分成功但流水推送失败
                    if(chargeCostFlag == '01' && waterPushFlag == '0'){
                        return authToolBar({
                            "channel-order-water-push": '<a data-id="' + row.channelOrderId + '" class="ctr ctr-repeat">重新推送</a>'
                        }).join("");
                    }else{
                       return "已推送";
                    }
                }
            },
        ]],
    });

    // 交易类型
    selectData("transType");
    // 交易状态
    selectData("bizStatus");
    // 支付方式
    selectData("payMethod");
    // 币种类型
    selectData("currencyType");

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        initDate($beginDate, $endDate, -1);
        dg.datagrid('load', searchFrom.formToJson());
    });

    // 渠道流水补处理
    dg.datagrid("getPanel").on('click', "a.ctr-repeat", function () {
        $.messager.confirm('系统提示', '确定要重新推送渠道流水么?', function (r) {
            if (r) {
                $.ajax({
                    url: '/liquidation/channel-order/waterPush',
                    data: {'channelOrderId': this.dataset.id},
                    dataType: 'json',
                    success: function (data) {
                        if (data.responseCode == '00000000') {
                            dg.datagrid('reload');
                            msg("渠道流水补处理成功", 6);
                        } else {
                            msg("渠道流水补处理失败", 5);
                        }
                    }
                });
            }
        })
    })

    // 下载
    searchFrom.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/liquidation/channel-order/download";
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