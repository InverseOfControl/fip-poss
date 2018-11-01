$(function () {
    var dg = $("#verifyCollectResult_dg");
    var searchForm = $("#verifyCollectResult_search_from");

    // 使用edatagrid，需要而外导入edatagrid扩展
    var dgOption = {
        url: '/traderecon/verifyCollectResult/totalList',
        emptyMsg: "没有找到记录",
        idField: "id",
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 30,
        columns: [[
            {
                field: 'id',
                title: '主键',
                width: 30,
                hidden: true
            }, {
                field: 'batchNo',
                title: '批次号',
                width: 30
            }, {
                field: 'fileName',
                title: '对账文件名',
                width: 30
            }, {
                field: 'orgName',
                title: '渠道名',
                width: 30,
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
                field: 'moreSysNums',
                title: '系统多笔数',
                width: 50, formatter: function (value, rowData) {
                    var type1 = "02" + "";
                    return "<a href='#' onclick='openReconDetail(" + rowData.batchNo + ',' + type1 + ")'> " + value + "</a>";
                }
            }, {
                field: 'reconSuccessNums',
                title: '对账成功笔数',
                width: 50, formatter: function (value, rowData) {
                    var type1 = "00" + "";
                    return "<a href='#' onclick='openReconDetail(" + rowData.batchNo + ',' + type1 + ")'> " + value + "</a>";
                }
            }, {
                field: 'moreChannelNums',
                title: '渠道多笔数',
                width: 50, formatter: function (value, rowData) {
                    var type1 = "01" + "";
                    return "<a href='#' onclick='openReconDetail(" + rowData.batchNo + ',' + type1 + ")'> " + value + "</a>";
                }
            }, {
                field: 'conflictNums',
                title: '币种金额不一致笔数',
                width: 50, formatter: function (value, rowData) {
                    var type1 = "03" + "";
                    return "<a href='#' onclick='openReconDetail(" + rowData.batchNo + ',' + type1 + ")'> " + value + "</a>";
                }
            }, {
                field: 'notSysNums',
                title: '系统无',
                width: 50, formatter: function (value, rowData) {
                    var type1 = "05" + "";
                    return "<a href='#' onclick='openReconDetail(" + rowData.batchNo + ',' + type1 + ")'> " + value + "</a>";
                }
            }, {
                field: 'gmtCreateTime', title: '创建时间', width: 40,
                formatter: function (val) {
                    return formatDate(val);
                }
            }
        ]]
    };

    /**
     * 搜索区域事件
     */
    searchForm.on('click', 'a.searcher', function () {//检索
        dg.datagrid('load', searchForm.formToJson());
    }).on('click', 'a.reset', function () {//重置
        searchForm.form('reset');
        dg.datagrid('load', {});
    }).on('click', 'a.download', function () {
        $.ajax({
            type: "post",
            url: "/traderecon/verifyCollectResult/download",
            data: searchForm.formToJson()
        });
    });

    $(dg).datagrid(dgOption);

});

function openReconDetail(batchNo, type1) {
    // center.panel("refresh", "/traderecon/tradereconresult" );
    console.log("=========id:", type1);
    addEasyuiTab({
        id: getRandomNumByDef(), title: "对账结果管理",
        href: "/traderecon/tradereconresult?batchNo=" + batchNo + '&type=' + type1, closable: true
    }, true);
}


