$(function () {
    var dg = $('#channelFile_dg');//数据网格
    var searchForm = $('#channel-file-form');//表单
//数据网格选项
    dg.datagrid({
        url: '/recon/channelfile/list',
        emptyMsg: "暂无文件上传记录",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 30,
        columns: [[

            {field: 'orgName', title: '渠道名称', width: 30},
            {field: 'batchNo', title: '批次号', width: 30},
            {field: 'fileType', title: '文件类型', width: 40},
            {field: 'fileExtensionName', title: '文件格式', width: 30},
            {field: 'fileName', title: '文件名称', width: 40},
            {field: 'handleStatus', title: '文件解析状态', width: 30},
            {field: 'reconStatus', title: '对账状态', width: 30},
            {
                field: 'gmtCreateTime', title: '导入时间', width: 40,
                formatter: function (val) {
                    return formatDate(val);
                }
            }

        ]],
        toolbar: authToolBar({
            "channelfile-add": {
                iconCls: 'fa fa-plus-square',
                text: "导入",
                handler: function () {
                    addChannelFile()
                }
            },
        })
    });


    /**
     * 搜索区域事件
     */
    searchForm.on('click', 'a.searcher', function () {//检索
        dg.datagrid('load', searchForm.formToJson());
    }).on('click', 'a.reset', function () {//重置
        searchForm.form('reset');
        dg.datagrid('load', {});
    }).on('click', 'a.download', function () {
        window.location.href = "/recon/channelfile/download?" + searchForm.serialize();
        layerMsg("下载成功");
        dg.datagrid('load', searchForm.formToJson());
    });

    /**
     * 渠道文件导入
     */
    function addChannelFile() {
        var dialog = $("<div/>", {class: 'noflow'}).dialog({
            title: "渠道文件导入",
            iconCls: "fa fa-plus-square",
            height: 380,
            width: 420,
            href: '/recon/channelfile/addChannelFileView',
            modal: true,
            onClose: function () {
                $(this).dialog("destroy");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    $('#addchannelfile-form').form('submit', {
                        type: "post",
                        url: "/recon/channelfile/addChannelFileDetails",
                        dateType: "json",
                        success: function (res) {
                            ajaxResult(res);
                        }
                    });
                    dg.datagrid('reload');
                    dialog.dialog('close');
                }
            }],
        });
    }
});



