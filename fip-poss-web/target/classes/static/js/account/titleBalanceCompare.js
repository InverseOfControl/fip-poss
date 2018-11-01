$(function () {
        var dg = $("#titleBalanceCompare_dg");
        var searchFrom = $("#titleBalanceCompare_search_from");
    var startDateEle = searchFrom.find(":input[name='startDay']");
    var endDateEle = searchFrom.find(":input[name='endDay']");
    setDaysPeriod(startDateEle,endDateEle,3);

        // 使用edatagrid，需要而外导入edatagrid扩展
        dg.datagrid({
            url: '/account/titleBalanceCompare/list',
            emptyMsg: "还未生成会计账户核对结果表",
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
                {field: 'openingAmountBalance', title: '账户期初余额', width: 130},
                {field: 'openingAmountTitle', title: '会计期初余额', width: 130},
                {field: 'incomeAmountBalance', title: '账户收入金额', width: 130},
                {field: 'incomeAmountTitle', title: '会计收入金额', width: 130},
                {field: 'costAmountBalance', title: '账户支出金额', width: 130},
                {field: 'costAmountTitle', title: '会计支出金额', width: 130},
                {field: 'closingAmountBalance', title: '账户期末余额', width: 130},
                {field: 'closingAmountTitle', title: '会计期末余额', width: 130},
                {field: 'checkEntryResult', title: '核对结果', width: 100},
                {field: 'checkEntryMsg', title: '核对描述', width: 130},
                {field: 'screateTime', title: '创建时间', width: 160},
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
            window.location.href = "/account/titleBalanceCompare/download?" + searchFrom.serialize();
            layerMsg("下载成功");
        });


});
