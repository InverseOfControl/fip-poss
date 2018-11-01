$(function () {
    var dg = $('#pay_blacklist_dg');//数据网格
    var searchForm = $('#pay_blacklist_form');//表单
    //数据网格选项
    dg.datagrid({
        url: '/paycore/blacklist/list',
        emptyMsg: "暂无数据",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 30,
        columns: [[
            {field: 'id', title: '主键', width: 30, align: 'center'},
            {field: 'merchantId', title: '商户号', width: 60, align: 'center'},
            {field: 'currency', title: '币种', width: 30, align: 'center'},
            {field: 'tradeType', title: '交易类型', width: 30, align: 'center'},
            {field: 'status', title: '状态', width: 30, align: 'center' ,formatter: function(value, options){
                    return value == 1 ? '<span style="color: blue">启用</span>' : '禁用';
            }},
            {field: 'createTime', title: '创建时间', width: 30, align: 'center'},
            {field: 'updateTime', title: '更新时间', width: 30, align: 'center'},
            {field: 'test',title: '操作',width: 50,align: 'center',formatter: function (value, row, index) {
                    return authToolBar({
                        "blacklist-edit": '<a data-id="' + row.id + '" class="ctr ctr-edit">编辑</a>',
                        "blacklist-delete": '<a data-id="' + row.id + '" class="ctr ctr-delete">删除</a>'
                    }).join("");
            }}
        ]],
        onLoadSuccess:function(data){
            if(data.errorMsg != null){
                $.messager.alert("系统提示", data.errorMsg);
            }
        },
        toolbar: authToolBar({
            "blacklist-create": {
                iconCls: 'fa fa-plus-square',
                text: "创建",
                handler: function () {
                    createForm()
                }
            }
        })
    });

    // 交易类型
    selectData("transType");
    // 币种类型
    selectData("currencyType");

    // 搜索和重置事件
    searchForm.on('click', 'a.searcher', function () {
        dg.datagrid('load', searchForm.formToJson());
    }).on('click', 'a.reset', function () {
        searchForm.form('reset');
        dg.datagrid('load', searchForm.formToJson());
    });

    /**
     * 操作按钮绑定事件
     */
    dg.datagrid("getPanel").on('click', "a.ctr-edit", function () {// 编辑按钮事件
        createForm.call(this, this.dataset.id)
    }).on('click', "a.ctr-delete", function () {// 删除按钮事件
        var id = this.dataset.id;
        $.messager.confirm("删除提醒", "确认删除此配置?", function (r) {
            if (r) {
                $.get("/paycore/blacklist/delete", {id: id}, function () {
                    // 数据操作成功后，对列表数据，进行刷新
                    dg.datagrid("reload");
                });
            }
        });
    });

    function selectData(type){
        searchForm.find("input[id='"+type+"']").combobox({
            url:'/paycore/blacklist/getData?type='+type,
            valueField:'id',
            textField:'text'
        });
    }

    /**
     * 创建表单窗口
     *
     * @returns
     */
    function createForm(id) {
        var dialog = $("<div/>", {class: 'noflow'}).dialog({
            title: (id ? "编辑" : "创建") + "黑名单",
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            height: id ? 380 : 420,
            width: 420,
            href: '/paycore/blacklist/form',
            queryParams: {
                id: id
            },
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#blacklist-form");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post("/paycore/blacklist/save", form.serialize(), function (res) {
                            //调用app.js的ajax结果处理函数ajaxJsonResult()，弹信息框
                            ajaxJsonResult(res);
                            dg.datagrid('reload');
                            dialog.dialog('close');
                        });
                    }
                }
            }]
        });
    }
});


