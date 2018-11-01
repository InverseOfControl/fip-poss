$(function () {
    var dg = $('#pay_order_dg');//数据网格
    var searchForm = $('#pay_order_form');//表单
    // 日期初始化
    var startDateEle = searchForm.find(":input[name='tradeDateBegin']");
    var endDateEle = searchForm.find(":input[name='tradeDateEnd']");
    setDaysPeriod(startDateEle,endDateEle,3);
    //数据网格选项
    dg.datagrid({
        url: '/paycore/payOrder/list',
        emptyMsg: "暂无数据",
        idField: "paymentOrderId",
        fit: true,
        rownumbers: true,
        fitColumns: false,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 30,
        queryParams:{
            'tradeDateBegin':startDateEle.datebox('getValue'),
            'tradeDateEnd':endDateEle.datebox('getValue')
        },
        columns: [[
            // {field: 'memberId', title: '会员号', width: 30},
            {field: 'merchantId', title: '商户号', width: 90, align: 'center'},
            {field: 'merchantOrderId', title: '商户订单号', width: 140, align: 'center'},
            {field: 'acquireOrderId', title: '收单订单号', width: 140, align: 'center'},
            {field: 'paymentOrderId', title: '支付订单号', width: 160, align: 'center'},
            {field: 'tradeType', title: '交易类型', width: 70, align: 'center'},
            {field: 'amount', title: '交易金额', width: 70, align: 'right'},
            {field: 'currency', title: '交易币种', width: 70, align: 'center'},
            {field: 'payAmount', title: '支付金额', width: 70, align: 'right'},
            {field: 'payCurrency', title: '支付币种', width: 70, align: 'center'},
            {field: 'payStatus', title: '支付状态', width: 70, align: 'center'},
            {field: 'payMode', title: '支付模式', width: 80, align: 'center'},
            {field: 'payType', title: '支付类型', width: 70, align: 'center'},
            {field: 'gmtCreateTime', title: '创建时间', width: 140, align: 'center'},
            {field: 'gmtCompleteTime', title: '完成时间', width: 140, align: 'center'},
            {field: 'relatePaymentId', title: '原始支付订单号', width: 160, align: 'center'},
            {field: 'settleCurrency', title: '结算币种', width: 70, align: 'center'},
            {field: 'resultCode', title: '对外code', width: 70, align: 'center'},
            {field: 'lastTakenAmount', title: '剩余可退/扣金额', width: 100, align: 'right'}
            // {field: 'gmtModifyTime', title: '更新时间', width: 55},
            // {field: 'accSplitData', title: '分账串信息', width: 30},
            // {field: 'version', title: '版本号', width: 30},
            // {field: 'pipeLogId', title: '记账ID', width: 30},
            // {field: 'failurePipeLogId', title: '失败记账ID', width: 30},
            // {field: 'successPipeLogId', title: '成功记账ID', width: 30},
            // {field: 'lastTakenPayAmount', title: '剩余可退款/扣款支付金额', width: 30}
        ]],
        onLoadSuccess:function(data){
            if(data.errorMsg != null){
                $.messager.alert("系统提示", data.errorMsg);
            }
        },
        toolbar: authToolBar({
            "paycore_pay_order_download": {
                iconCls: 'fa fa-download',
                text: "下载",
                handler: function () {
                    window.location.href="/paycore/payOrder/download?"+$("#pay_order_form").serialize();
                }
            }
        })
    });


    // 交易类型
    selectData("transType");
    // 支付状态
    selectData("bizStatus");
    // // 支付方式
    // selectData("payMethod");
    // 币种类型
    selectData("currencyType");

    // 搜索和重置事件
    searchForm.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchForm.formToJson());
    }).on('click', 'a.reset', function () {
        searchForm.form('reset');
        setDaysPeriod(startDateEle,endDateEle,3);
        dg.datagrid('load', searchForm.formToJson());
    });

    // 下载
    searchForm.on('click', 'a.download', function () {
        var tempDiv = $('#tempDiv');
        var rows = dg.datagrid("getRows");
        var url = "/paycore/payOrder/download";
        createTempForm(url, tempDiv, rows, searchForm);
    });

    function selectData(type){
        searchForm.find("input[id='"+type+"']").combobox({
            url:'/select/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }
});


