/**
 * 日期框是否可编辑
 * @param $dom1
 * @param $dom2
 */
function initDatebox($dom1,$dom2,bool){
    $dom1.datebox({editable: bool});
    $dom2.datebox({editable: bool});
}

/**
 * 计算初始化日期
 * @param $dom1 开始日期
 * @param $dom2 结束日期
 * @param month 开始日期的月份推前几个月
 */
function initDate($dom1,$dom2,month){
    $.when(doCalDate(month)).done(function(data){
        $dom1.datebox("setValue", data.split("@")[0]);
        $dom2.datebox("setValue", data.split("@")[1]);
    })
}

function doCalDate(month){
    var defer = $.Deferred();
    $.get("/select/calDate",{"month":month},function(data){
        defer.resolve(data);
    });
    return defer.promise();
}
