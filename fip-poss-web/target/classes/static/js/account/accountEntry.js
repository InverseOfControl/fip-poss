$(function () {
        var dg = $("#entry_dg");
        var searchFrom = $("#entry_search_from");
    var startDateEle = searchFrom.find(":input[name='startDay']");
    var endDateEle = searchFrom.find(":input[name='endDay']");
    setDaysPeriod(startDateEle,endDateEle,3);
        // 使用edatagrid，需要而外导入edatagrid扩展
        dg.datagrid({
            url: '/account/accountEntry/list',
            emptyMsg: "还未生成分录",
            idField: "id",
            fit: true,
            rownumbers: true,
            fitColumns: false,
            border: false,
            pagination: true,
            singleSelect: true,
            pageSize: 30,
            pageList: [10,20,30,40,50],
            columns: [[
                {field: 'accountingDay', title: '会计日期', width: 130},
                {field: 'voucharNo', title: '会计凭证号', align: 'left', width: 130},
                {field: 'txnOrderNo', title: '订单号', align: 'left', width: 130},
                {field: 'titleNo', title: '科目号', align: 'left', width: 130},
                {field: 'titleName', title: '科目名称', align: 'left', width: 160},
                {field: 'accountNoS', title: '账户号', align: 'left', width: 130},
                {field: 'drCr', title: '借贷标志', width: 100},
                {field: 'currency', title: '币种', width: 100},
                {field: 'amount', title: '金额', width: 130},
                {field: 'acctDrCr', title: '账户资金方向', width: 100},
                {field: 'psCode', title: '支付服务码', width: 100},
                {field: 'orderType', title: '交易类型', width: 100},
                {field: 'financeType', title: '金额类型', width: 100},
                {field: 'orgCode', title: '渠道号', width: 130},
                {field: 'screateTime', title: '记账时间', width: 160},
                {field: 'scompletionTime', title: '订单完成时间', width: 160}
            ]]
        });


        /**
         * 搜索区域事件
         */
        searchFrom.on('click', 'a.searcher', function () {//检索
            dg.datagrid('load', searchFrom.formToJson());
        }).on('click', 'a.reset', function () {//重置
            searchFrom.form('reset');
            dg.datagrid('load', {});
        }).on('click', 'a.download', function (){
            window.location.href = "/account/accountEntry/download?" + searchFrom.serialize();
            layerMsg("下载成功");
        });
});
