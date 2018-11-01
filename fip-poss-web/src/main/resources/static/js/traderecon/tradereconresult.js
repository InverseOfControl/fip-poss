$(function () {
    var dg = $("#tradereconresult_dg");
    var searchFrom = $("#tradereconresult_search_from");
    var form;

// 使用edatagrid，需要而外导入edatagrid扩展
    var options = {
        url: '/traderecon/tradereconresult/list',
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
        //ignore: ['roles'],
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
                field: 'batchNo',
                title: '批次号',
                width: 33,
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
                field: 'channelCurrency',
                title: '渠道交易币种',
                width: 25,
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
                field: 'channelTransAmt',
                title: '渠道交易金额',
                width: 25,
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
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
                field: 'reconResult',
                title: '处理状态',
                width: 22,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'accountingType',
                title: '对账记账类型',
                width: 30,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'correctionType',
                title: '差错处理方式',
                width: 26,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'reconRequestId',
                title: '对账记账请求ID',
                width: 35,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'corrRequestId',
                title: '差错处理记账请求ID',
                width: 35,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'auditStatus',
                title: '审核状态',
                width: 20,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'initialAuditor',
                title: '初审人',
                width: 25,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }, {
                field: 'repeatAuditor',
                title: '复审人',
                width: 25,
                editor: {
                    type: 'datebox',
                    options: {
                        editable: false
                    }
                }
            }

        ]],
        toolbar: authToolBar({
            "initialAudit": {
                iconCls: 'fa fa-repeat',
                text: "初审",
                handler: function () {
                    var rows = dg.datagrid('getSelections');
                    var chk_value = '';
                    for (var i = 0; i < rows.length; i++) {
                        chk_value += rows[i].id + ',';
                    }
                    if (chk_value.length <= 0) {
                        $.messager.alert("系统提示", "请选择订单！");
                        return;
                    }
                    chk_value = chk_value.substring(0, chk_value.length - 1);
                    var correctionType = $("#correctionType").val();
                    var params = {
                        ids: chk_value, correctionType: correctionType, userName: MEMBER.userName
                    };
                    if (rows) {
                        $.messager.confirm('系统提示', '确定将订单进行初审!', function (r) {
                            if (r) {
                                $.get("/traderecon/tradereconresult/initialAudit", params, function (rsp) {
                                    if (rsp.success) {
                                        $.messager.alert("系统提示", "初审成功！");
                                        dg.datagrid('reload');
                                    } else {
                                        $.messager.alert("系统提示", rsp.message);
                                    }
                                })
                            }
                        })
                    }
                }
            }, "repeatAudit": {
                iconCls: 'fa fa-repeat',
                text: "复审",
                handler: function () {
                    var rows = dg.datagrid('getSelections');
                    var chk_value = '';
                    var correctionType = ''
                    for (var i = 0; i < rows.length; i++) {
                        chk_value += rows[i].id + ',';
                    }
                    if (chk_value.length <= 0) {
                        $.messager.alert("系统提示", "请选择订单！");
                        return;
                    }
                    for (var i = 0; i < rows.length; i++) {
                        correctionType = rows[i].correctionType;
                        if (correctionType != '') {
                            break;
                        }
                    }
                    chk_value = chk_value.substring(0, chk_value.length - 1);
                    var params = {
                        ids: chk_value, correctionType: correctionType, userName: MEMBER.userName
                    };
                    if (rows) {
                        $.messager.confirm('系统提示', '确定将订单进行复审', function (r) {
                            if (r) {
                                $.get("/traderecon/tradereconresult/repeatAudit", params, function (rsp) {
                                    if (rsp.success) {
                                        $.messager.alert("系统提示", "复审成功！");
                                        dg.datagrid('reload');
                                    } else {
                                        $.messager.alert("系统提示", rsp.message);
                                    }
                                })
                            }
                        })
                    }
                }
            }
        })
    };

    dg.datagrid(options);

    /**
     * 操作按钮绑定事件
     */
    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {// 编辑按钮事件
        createForm.call(this, this.dataset.id)
    }).on('click', "a.ctr-delete", function () {// 删除按钮事件
        var id = this.dataset.id;
        var v_orgCode = orgCode.$.messager.confirm("删除提醒", "确认删除此用户?", function (r) {
            if (r) {
                $.get("/system/member/delete", {id: id}, function () {
                    // 数据操作成功后，对列表数据，进行刷新
                    dg.datagrid("reload");
                });
            }
        });
    });

    /**
     * 搜索区域事件
     */
    searchFrom.on('click', 'a.searcher', function () {//检索
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {//重置
        searchFrom.form('reset');
        $("#batchNo").textbox("setValue", "");
        $("#reconResult").textbox("setValue", "");
        dg.datagrid('load', {});
    });


    /**
     * 创建表单窗口
     *
     * @returns
     */
    function createForm(id) {
        var dialog = $("<div/>", {class: 'noflow'}).dialog({
            title: (id ? "编辑" : "创建") + "用户",
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            height: id ? 380 : 420,
            width: 420,
            href: '/system/member/form',
            queryParams: {
                id: id
            },
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#member-form");

                //这个字段比较特殊，有比较多的校验，所以单独拿出来实例化
                $("#member_userName").textbox({
                    label: '账号：',
                    required: true,
                    validType: ['userName', 'length[6, 10]', "remote['/system/member/check','userName']"]
                })
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post("/system/member/save", form.serialize(), function (res) {
                            dg.datagrid('reload');
                            dialog.dialog('close');
                        });
                    }
                }
            }]
        });
    }
});
