$(function () {
    var dg = $("#merchcharge_dg");
    var searchFrom = $("#merchcharge_search_from");
    var dlg = $('#merch_dlg');

    dg.datagrid({
        url: '/charge/merchcharge/list',
        emptyMsg: "查询无记录",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: true,
        ignore: ['roles'],
        pageSize: 30,
        queryParams: searchFrom.formToJson(),
        sortName:"id",
        sortOrder:"desc",
        columns:[[
            {field: 'merchantId', title: '商户号', width: 15, sortable:true},
            {field: 'chargeScene', title: '交易类型', width: 10, sortable:true, formatter: function (val) {
                    return  selDisPlayTran(val, $('#merchChargeScene').combobox('getData'));
                }
            },
            {field: 'id', title: '规则ID', width: 10, sortable:true},
            {field: 'chargeRuleName', title: '规则名称', width: 30, sortable:true},
            {field: 'status', title: '状态', width: 10, sortable:true, formatter: function (val) {
                    return  selDisPlayTran(val, $('#merchStatus').combobox('getData'));
                }
            },
            {field: 'effectiveDate', title: '生效时间', width: 30, sortable:true, formatter: function (value, row) {
                    return row.effectiveDate + '-' + row.expiryDate;
                }
            },
            {field: 'createTime', title: '创建时间', width: 20, sortable:true, formatter:function(value,row,index){
                    if(value == null || value == ''){
                        return '';
                    }
                    return formatTime(value) ;
                }
            },
            {field: 'updateTime', title: '最后修改时间', width: 20, sortable:true, formatter:function(value,row,index){
                    if(value == null || value == ''){
                        return '';
                    }
                    return formatTime(value) ;
                }
            },
            {field: 'test', title: '操作', width: 30, align: 'center', formatter: function (value, row, index) {
                    return authToolBar({
                        "charge-merchcharge-detail": '<a data-id="' + row.id + '"class="ctr ctr-detail">详情</a>'
                    }).join("");
                }
            }
        ]],
        toolbar: authToolBar(
            {
                "charge-merchcharge-add": {
                    iconCls: 'fa fa-plus-square',
                    text: "新增",
                    handler: function () {
                        addOrUpdate("")
                    }
                },
                "charge-merchcharge-update": {
                    iconCls: 'ctr ctr-edit',
                    text: "修改",
                    handler: function () {
                        update();
                    }
                }
            }
        )
    });


    function querylist() {
        dg.datagrid('load', searchFrom.formToJson());
    }


    /**
     * 搜索区域事件
     */
    searchFrom.on('click', 'a.searcher', function () {//查询
        dg.datagrid('load', searchFrom.formToJson());
    }).on('click', 'a.reset', function () {//重置
        searchFrom.form('reset');
        dg.datagrid('load', {});
    });

    /**
     * 新增、编辑
     * @param id
     */
    function addOrUpdate(id) {
        var dialog = dlg.dialog({
            title: id ?  "修改":"新增",
            height: 600,
            width: 900,
            href: '/charge/merchcharge/merchchargedtl',
            queryParams: {
                id: id
            },
            resizable: true,
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#merchchargedtl-form");

                //初始化数据
                initData(id);

                //页面根据数据动态显示规则内容
                initRuleContent(id);
            },
            buttons: [
                {
                    iconCls: 'fa fa-save',
                    text: '确认',
                    handler: function () {
                        submit(form, id, dialog);
                    }
                },{
                    iconCls: 'icon-redo',
                    text: '返回',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }
            ]
        });
    }


    dg.datagrid("getPanel").on('click', "a.ctr-detail", function () {//详情信息
        detail.call(this, this.dataset.id)
    })

    /**
     * 确认提交
     * @param form
     */
    function submit(form, id, dialog) {
        if (form.form('validate')) {
            var requestData = formToJsonObject(form.serializeArray());
            requestData.id = id;
            requestData.countryCode = matchParamDefault(requestData.countryCode);
            requestData.cardOrg= matchParamDefault(requestData.cardOrg);
            var strgDtlDTOList = null;
            try {
                if(!dateCompare(requestData.effectiveDate, requestData.expiryDate)){
                    throw Error("失效日期不能小于生效日期");
                }
                strgDtlDTOList = form.chargeStrgDtlList();
            }catch (err) {
                layer.msg(err.message);
                return;
            }
            requestData.strgDtlDTOList = strgDtlDTOList;
            $.ajax({
                type:"post",
                url:"/charge/merchcharge/addOrUpdate",
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                data:JSON.stringify(requestData),
                success: function (res) {
                    if(res.success){
                        layer.msg("<div style='font-size:16px'><img src='/static/easyui/themes/icons/ok.png' height='16' width='16'/> <span>操作成功！</span></div>");
                        dg.datagrid('reload');
                        dialog.dialog('close');
                    }else {
                        layerMsg('操作失败，'+ res.message);
                    }
                }
            });
        }
    }


    function detail(id) {
        var dialog = dlg.dialog({
            title: "详情",
            height: 600,
            width: 900,
            href: '/charge/merchcharge/merchchargedtl',
            queryParams: {
                id: id,
                isDetail:true
            },
            resizable: true,
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#merchchargedtl-form");
                initData(id);
                //页面根据数据动态显示规则内容
                initRuleContent(id);
                form.find('input,textarea,select,commbobox').attr('readonly',true);

            },
            buttons: [
                {
                    iconCls: 'icon-redo',
                    text: '返回',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }
            ]
        });
    }

    function update() {
        var row = dg.datagrid('getSelected');
        if(row == null || row.id == null){
            layerMsg("请选中需要修改的记录");
            return;
        }
        addOrUpdate(row.id);
    }


    $.fn.chargeStrgDtlList = function chargeStrgDtlListData(){
        var chargeStrgDtlList = new Array();
        $('#ruleTable').find("tr").each(function () {
            var tdArr = $(this).children();
            chargeStrgDtlList.push(getRuleDtlObj(tdArr));
        })
        return chargeStrgDtlList;
    }
});