$(function () {
        var dg = $("#balance_dg");
        var searchFrom = $("#balance_search_from");
    var startDateEle = searchFrom.find(":input[name='startDay']");
    var endDateEle = searchFrom.find(":input[name='endDay']");
    setDaysPeriod(startDateEle,endDateEle,3);

        // 使用edatagrid，需要而外导入edatagrid扩展
        dg.datagrid({
            url: '/account/balanceDaily/list',
            emptyMsg: "还未生成余额表",
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
                {field: 'titleNo', title: '科目号', align: 'left', width: 130},
                {field: 'titleName', title: '科目名称', align: 'left', width: 160},
                {field: 'drCr', title: '余额方向', width: 100},
                {field: 'currency', title: '币种', width: 100},
                {field: 'drOpenBalance', title: '期初借方余额', width: 130},
                {field: 'drOpenBalanceCny', title: '期初借方人民币余额', width: 130},
                {field: 'crOpenBalance', title: '期初贷方余额', width: 130},
                {field: 'crOpenBalanceCny', title: '期初贷方人民币余额', width: 130},
                {field: 'drOccurrenceAmount', title: '借方发生额', width: 130},
                {field: 'drOccurrenceAmountCny', title: '借方发生额人民币金额', width: 130},
                {field: 'crOccurrenceAmount', title: '贷方发生额', width: 130},
                {field: 'crOccurrenceAmountCny', title: '贷方发生额人民币金额', width: 130},
                {field: 'drCloseBalance', title: '期末借方余额', width: 130},
                {field: 'drCloseBalanceCny', title: '期末借方人民币余额', width: 130},
                {field: 'crCloseBalance', title: '期末贷方余额', width: 130},
                {field: 'crCloseBalanceCny', title: '期末贷方人民币余额', width: 130},
                {field: 'screateTime', title: '创建时间', width: 160},
                {field: 'checkBalance', title: '试算结果', width: 100,formatter: function (val) {
                    return {
                        'Y': '平衡',
                        'N': '不平衡'
                    }[val];
                }},
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
            window.location.href = "/account/balanceDaily/download?" + searchFrom.serialize();
            layerMsg("下载成功");
        });

});
