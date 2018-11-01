$(function () {
    var dg = $("#mert_settlement_config_dg");
    var searchFrom = $("#mert_settlement_config_search_from");
    var addOrEditForm;
    var urlPre = "/liquidation/mert-settlement-config/";
    var urlAllSelectPre = "/liquidation/mert-settlement-config/getAllSelectData?type=";

    // 加载表格数据
    dg.datagrid({
        url: urlPre + 'list',
        emptyMsg: "暂无数据",
        fit: true,
        rownumbers: true,
        fitColumns: false,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 10,
        columns: [[
            {field: 'id', hidden: true},
            {field: 'merchantId', title: '商户号', width: 90},
            {field: 'merchantName', title: '商户名称', width: 90},
            {field: 'settlementCurrencys', title: '结算币种', width: 90},
            {field: 'startSettlementMoney', title: '起始金额', width: 90},
            {field: 'settlementCycle', title: '结算周期', width: 90},
            {field: 'settlementDay', title: '结算日', width: 90},
            {field: 'settlementDayType', title: '结算日类型', width: 90},
            {field: 'paymentMethods', title: '支付方式', width: 90},
            {field: 'countryCodes', title: '国家', width: 90},
            {field: 'channelCodes', title: '渠道', width: 90},
            {
                field: 'gmtCreateTime', title: '创建时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            },
            {
                field: 'gmtModifyTime', title: '最后更新时间', width: 150, formatter: function (value, row, index) {
                    if (value) {
                        return new Date(value).toLocaleString();
                    }
                }
            },
            {
                field: 'operate', title: '操作', width: 140, formatter: function (value, row, index) {
                    return authToolBar({
                        "mert-settlement-config-edit": '<a data-id="' + row.id + '" class="ctr ctr-edit">编辑</a>',
                        "mert-settlement-config-delete": '<a data-id="' + row.id + '" class="ctr ctr-delete">删除</a>',
                        "mert-settlement-config-detail": '<a data-id="' + row.id + '" class="ctr ctr-detail">详情</a>'
                    }).join("");
                }
            },
        ]],
        toolbar: authToolBar({
            "mert-settlement-config-add": {
                iconCls: 'fa fa-plus-square',
                text: "创建",
                handler: function () {
                    createDialog("add");
                }
            }
        })
    });

    // 搜索和重置事件
    searchFrom.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {
        searchFrom.form('reset');
        dg.datagrid('load', {});
    });

    // 编辑
    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {
        createDialog.call(this, "edit", this.dataset.id);
    })

    // 详情
    dg.datagrid("getPanel").on('click', "a.ctr-detail", function () {
        createDialog.call(this, 'detail', this.dataset.id);
    })

    // 删除
    dg.datagrid("getPanel").on('click', "a.ctr-delete", function () {
        var id = this.dataset.id;
        $.messager.confirm('系统提示', '确定删除?', function (r) {
            if (r) {
                $.post(urlPre + "delete", {'id': id}, function (data) {
                    dg.datagrid('reload');
                    msg("操作成功", 6);
                })
            }
        })
    })

    // 打开弹框
    function createDialog(flag, id) {
        var title;
        if (flag == 'add') title = '新增';
        if (flag == 'edit') title = '编辑';
        if (flag == 'detail') title = '详情';

        var $dialog = $("<div/>", {class: 'noflow'}).dialog({
            title: title,
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            width: 650,
            height: 600,
            closed: false,
            cache: false,
            modal: true,
            href: urlPre + "toDialogPage",
            queryParams: {"id": id, "flag": flag},
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                if (flag == "detail") {
                    $('.dialog-button a span').hide();
                }

                addOrEditForm = $("#mert_settlement_config_dialog");

                getAllSelectData("payMethod", urlAllSelectPre); // 支付方式
                getAllSelectData("countryType", urlAllSelectPre); // 国家
                getAllSelectData("channelType", urlAllSelectPre); // 渠道
                getAllSelectData("transMethod", urlAllSelectPre); // 交易方式
                getAllSelectData("currencyType1", urlAllSelectPre); // 交易币种

                selectData("settlementCycle0"); // 结算周期
                selectData("settlementDayType0"); // 结算日类型
                selectData("autoWithdrawal"); // 自动提现
                selectData("settlementCycle1"); // 保证金结算周期
                selectData("settlementDayType1"); // 保证金结算日类型
                selectData("currencyType"); // 结算币种
                selectData("priority"); // 优先级

                if (id) {
                    addOrEditForm.form("load", urlPre + "one?id=" + id);
                }
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (addOrEditForm.form('validate')) {
                        $.post(urlPre + flag, addOrEditForm.serialize(), function (data) {
                            dg.datagrid('reload');
                            $dialog.dialog('close');
                            msg("操作成功", 6);
                        })
                    }
                }
            }]
        });
    }

    /**
     * 获取有"全部"选项的下拉框数据
     */
    function getAllSelectData(type, url) {
        addOrEditForm.find("input[id='"+type+"']").combobox({
            url: url + type,
            valueField: 'id',
            textField: 'text',
            multiple: type=='currencyType1' ? true : false
        });
    }

    function selectData(type){
        addOrEditForm.find("input[id='"+type+"']").combobox({
            url:'/select/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }
});