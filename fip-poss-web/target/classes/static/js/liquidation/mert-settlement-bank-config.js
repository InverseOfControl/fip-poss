$(function () {
    var urlPre = "/liquidation/mert-settlement-bank-config/";
    var dg = $("#mert_settlement_bank_config_dg");
    var searchFrom = $("#mert_settlement_bank_config_search_from");
    var addOrEditForm;

    // 查询结算币种类型
    selectData("currencyType",searchFrom);

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
            {field: 'merchantId', title: '商户号', width: 80},
            {field: 'merchantName', title: '商户名称', width: 80},
            {field: 'settlementCurrency', title: '结算币种', width: 80},
            {field: 'account', title: '账号', width: 100},
            {field: 'accountName', title: '账户名称', width: 100},
            {field: 'swiftCode', title: 'SWIFT CODE', width: 100},
            {field: 'electronicLinkNumber', title: '电子联行号', width: 100},
            {field: 'ibanCode', title: 'IBAN号', width: 100},
            {field: 'openBankName', title: '开户行名称', width: 100},
            {field: 'openBankAddress', title: '开户行地址', width: 100},
            {field: 'accountSwiftCode', title: '账户行SWIFT CODE', width: 130},
            {field: 'status', title: '状态', width: 80},
            {
                field: 'gmtCreateTime', title: '创建时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            },
            {
                field: 'gmtModifyTime', title: '最后更新时间', width: 150, formatter: function (value, row, index) {
                    return new Date(value).toLocaleString();
                }
            },
            {
                field: 'operate', title: '操作', width: 100, formatter: function (value, row, index) {
                    return authToolBar({
                        "mert-settlement-bank-config-edit": '<a data-id="' + row.id + '" class="ctr ctr-edit">编辑</a>',
                        "mert-settlement-bank-config-delete": '<a data-id="' + row.id + '" class="ctr ctr-delete">删除</a>'
                    }).join("");
                }
            }
        ]],
        toolbar: authToolBar({
            "mert-settlement-bank-config-add": {
                iconCls: 'fa fa-plus-square',
                text: "创建",
                handler: function () {
                    createDialog();
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
        createDialog.call(this, this.dataset.id);
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
    function createDialog(id) {
        var url = (id ? urlPre + "edit" : urlPre + "add");
        var $dialog = $("<div/>", {class: 'noflow'}).dialog({
            title: (id ? "编辑" : "新增"),
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            width: 650,
            height: 350,
            closed: false,
            cache: false,
            modal: true,
            href: urlPre + "toDialogPage",
            queryParams: {"id": id},
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                addOrEditForm = $("#mert_settlement_bank_config_dialog");
                if (id) {
                    addOrEditForm.form("load", urlPre + "one?id=" + id);
                }
                selectData("currencyType0",addOrEditForm);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (addOrEditForm.form('validate')) {
                        $.post(url, addOrEditForm.serialize(), function (data) {
                            dg.datagrid('reload');
                            $dialog.dialog('close');
                            msg("操作成功", 6);
                        })
                    }
                }
            }]
        });
    }

    function selectData(type,searchFrom){
        searchFrom.find("input[id='"+type+"']").combobox({
            url:'/select/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }

});