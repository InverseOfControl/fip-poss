$(function () {
    var dg = $("#settleTransDetail_dg");
    var searchFrom = $("#settleTransDetail_search_from");
    var form;

// 使用edatagrid，需要而外导入edatagrid扩展
    var options = {
        url: '/traderecon/settleTransDetail/settleTransDetailList',
        emptyMsg: "没有找到记录",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: false,
        selectOnCheck: true,
        checkbox: true,
        pageSize: 30,
        queryParams: searchFrom.formToJson(),
        columns: [[
            {
                field: 'id',
                title: 'ID',
                checkbox: true,
                width: 30,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
                    }
                },
                formatter: function (val) {
                    return filterXSS(val);
                }
            }, {
                field: 'orgSubCode',
                title: '子渠道号',
                width: 33,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'merchantId',
                title: '商户号',
                width: 30,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'channelOrderId',
                title: '渠道订单号',
                width: 45,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'validatebox'
                    }
                },
                formatter: function (val) {
                    return filterXSS(val);
                }
            }, {
                field: 'transType',
                title: '交易类型',
                width: 22,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'payCurrency',
                title: '系统交易币种',
                width: 27,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'validatebox'
                    }
                },
                formatter: function (val) {
                    return filterXSS(val);
                }
            }, {
                field: 'payAmount',
                title: '系统交易金额',
                width: 25,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
                    }
                }
            }, {
                field: 'channelCompleteTime', title: '系统交易时间', width: 40,
                formatter: function (val) {
                    return formatDate(val);
                }
            }, {
                field: 'reconResult',
                title: '对账状态',
                width: 22,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'merchantOrderId',
                title: '商户订单号',
                width: 33,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }
        ]]
    };

    dg.datagrid(options);

    /**
     * 搜索区域事件
     */
    searchFrom.on('click', 'a.searcher', function () {//检索
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {//重置
        searchFrom.form('reset');
        $("#channelOrderId").textbox("setValue", "");
        $("#reconResult").textbox("setValue", "");
        dg.datagrid('load', {});
    });
});
