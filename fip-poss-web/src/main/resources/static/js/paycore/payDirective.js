$(function () {
    var dg = $('#pay_directive_dg');//数据网格
    var searchForm = $('#pay_directive_form');//表单
    // 日期初始化
    var startDateEle = searchForm.find(":input[name='tradeDateBegin']");
    var endDateEle = searchForm.find(":input[name='tradeDateEnd']");
    setDaysPeriod(startDateEle,endDateEle,3);
    //数据网格选项
    dg.datagrid({
        url: '/paycore/directive/list',
        emptyMsg: "暂无数据",
        idField: "directiveId",
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
            {field: 'directiveId', title: '支付指令号', width: 160, align: 'center'},
            {field: 'paymentOrderId', title: '支付订单号', width: 160, align: 'center'},
            {field: 'tradeType', title: '交易类型', width: 70, align: 'center'},
            {field: 'amount', title: '交易金额', width: 70, align: 'right'},
            {field: 'currency', title: '交易币种', width: 70, align: 'center'},
            {field: 'payAmount', title: '支付金额', width: 70, align: 'right'},
            {field: 'payCurrency', title: '支付币种', width: 70, align: 'center'},
            {field: 'payStatus', title: '支付状态', width: 70, align: 'center'},
            {field: 'payKind', title: '卡类型', width: 60, align: 'center'},
            {field: 'payType', title: '支付类型', width: 70, align: 'center'},
            {field: 'rate', title: '汇率', width: 50, align: 'right'},
            {field: 'cardId', title: '卡ID', width: 50, align: 'center'},
            {field: 'countryCode', title: '国家信息', width: 70, align: 'center'},
            {field: 'orgCode', title: '支付渠道号', width: 70, align: 'center'},
            {field: 'gmtCreateTime', title: '创建时间', width: 140, align: 'center'},
            {field: 'gmtCompleteTime', title: '完成时间', width: 140, align: 'center'},
            {field: 'relateDirectiveId', title: '原交易指令号', width: 160, align: 'center'},
            {field: 'channelItemId', title: '通道ID', width: 60, align: 'center'},
            {field: 'directiveType', title: '指令类型', width: 70, align: 'center'}
            // {field: 'extCol', title: '额外字段', width: 50},
            // {field: 'extCol1', title: '额外字段1', width: 50},
            // {field: 'rateExtInfo', title: '汇率信息扩展信息', width: 50},
            // {field: 'paySeq', title: '支付顺序', width: 50},
            // {field: 'gmtModifyTime', title: '更新时间', width: 55},
        ]],
        onLoadSuccess:function(data){
            if(data.errorMsg != null){
                $.messager.alert("系统提示", data.errorMsg);
            }
        },
        toolbar: authToolBar({
            "paycore_pay_directive_download": {
                iconCls: 'fa fa-download',
                text: "下载",
                handler: function () {
                    window.location.href="/paycore/directive/download?"+$("#pay_directive_form").serialize();
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
        var url = "/paycore/directive/download";
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


