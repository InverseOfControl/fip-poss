$(function () {
        var dg = $("#balanceAcctDaily_dg");
        var searchFrom = $("#balanceAcctDaily_search_from");
    var startDateEle = searchFrom.find(":input[name='startDay']");
    var endDateEle = searchFrom.find(":input[name='endDay']");
    setDaysPeriod(startDateEle,endDateEle,3);
        // 使用edatagrid，需要而外导入edatagrid扩展
        dg.datagrid({
            url: '/account/balanceAcctDaily/list',
            emptyMsg: "还未生成会计账户余额表",
            idField: "id",
            fit: true,
            rownumbers: true,
            fitColumns: false,
            border: false,
            pagination: true,
            singleSelect: true,
            ignore: ['roles'],
            pageSize: 30,
            pageList: [10,20,30,40,50],
            columns: [[
                {field: 'accountingDay', title: '会计日期', width: 130},
                {field: 'accountNoS', title: '账户号', align: 'left', width: 130},
                {field: 'currency', title: '币种', align: 'left',width: 100},
                {field: 'openingAmount', title: '期初余额', width: 130},
                {field: 'incomeAmount', title: '收入金额', width: 130},
                {field: 'costAmount', title: '支出金额', width: 130},
                {field: 'closingAmount', title: '期末余额', width: 130},
                {field: 'screateTime', title: '创建时间', width: 160},
                {field: 'supdateTime', title: '最后更新时间', width: 160}
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
            window.location.href = "/account/balanceAcctDaily/download?" + searchFrom.serialize();
            layerMsg("下载成功");
        });
});
