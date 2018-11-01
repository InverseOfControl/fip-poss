/**
 * 转日期格式yyyy-MM-dd hh:mm:ss
 * @param time
 * @returns {string}
 */
function formatTime(time) {
    var date=new Date(time);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var hour = date.getHours().toString();
    var minutes = date.getMinutes().toString();
    var seconds = date.getSeconds().toString();
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    return  y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d) + " " + hour + ":" + minutes + ":" + seconds;
}




/**
 * 自动将form表单封装成json对象
 */
function formToJsonObject(data) {
    var o = {};
    $.each(data, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


/**
 * 数组按指定符号转字符串
 * @param arrData
 * @returns symbol
 */
function arrToString(arrData, separator) {
    var str = "";
    if(arrData == null){
        return str;
    }
    if(Array.isArray(arrData)){
        str = arrData.join(separator);
    }else {
        str = arrData;
    }
    return str;
}


/**
 * 字符串拆分成数组
 * @param str
 * @param separator
 * @returns {*}
 */
function strToArr(str, separator) {
    var arr = null;
    if(str !=null ){
        arr = str.split(separator);
    }
    return arr;
}


/**
 * yyyy-MM-dd日期比较
 * @param beginDate
 * @param endDate
 * @returns {boolean}
 */
function dateCompare(beginDate, endDate) {
    var d1 = new Date(beginDate.replace(/\-/g, "\/"));
    var d2 = new Date(endDate.replace(/\-/g, "\/"));
    if (d1 > d2) {
        return false;
   }
   return true;
}


/**
 * 当前日期转yyyy-MM-dd字符串
 * @param date
 * @returns {string}
 */
function formatterDate(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    m = m < 10 ? '0' + m : m;
    d = d < 10 ? '0' + d : d;
    return y+'-'+m+'-'+d;
}




/**
 * 下拉框显示转换
 * @param val
 * @param arr
 * @returns {string}
 */
function selDisPlayTran(val, arr) {
    var playText="";
    if(arr !=null && arr.length > 0){
        for(var i=0; i < arr.length; i++){
            if(val == arr[i].id){
                playText = arr[i].text;
                break;
            }
        }
    }
    return playText;
}



/**
 * 判断是否为数字
 * @param val
 * @returns {boolean}
 */
function isRealNum(val){
    if(val ==null || val == ""){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
}

/**
 * 提示语
 * @param msg 提示语
 * @param icon 5-失败；6-成功；
 * @author hongxu.gao
 */
function msg(msg, icon) {
    layer.msg(msg, {
        time: 2000,
        icon: icon
    })
}

/**
 * 创建临时form表单，在下载的时候通过表单提单，隐藏参数信息
 * @param url 下载请求路径
 * @param tempDiv 临时div
 * @param rows 记录数
 * @param queryForm 查询表单，用于把查询参数复制到新表单中
 * @author hongxu.gao
 */
function createTempForm(url, tempDiv, rows, queryForm ){
    if(rows.length == 0){
        msg("数据为空，无需下载", 5);
        return;
    }
    var $tmpForm = $('<form/>');
    $tmpForm.attr('action', url);
    $tmpForm.attr('method', 'post');
    $tmpForm.attr('style', 'display:none');
    $tmpForm.html(queryForm.html());
    tempDiv.append($tmpForm);
    $tmpForm.submit();
    tempDiv.empty();
}
/**
 * append 元素后， 页面执行验证器初始化
 * @param $form
 */
function fromValidateInit($dom) {
    $.parser.parse($dom);//重新解析EasyUI组件，$dom为父级节点
    // $('input[type!="hidden"],select,textarea', $dom).each(function(){
    //     $(this).validatebox();
    // });
}

/**
 * 设置开始日期，结束日期N天间隔
 * @param startEle
 * @param endEle
 */
function setDaysPeriod(startEle,endEle,period){
	var date = new Date();
	var endTime = date.getTime();
	var startTime = endTime - period * 86400000;
	endTime = new Date(endTime);
	startTime = new Date(startTime);
	endTime = endTime.getFullYear()+'-'+(endTime.getMonth() + 1)+'-'+endTime.getDate();
	startTime = startTime.getFullYear()+'-'+(startTime.getMonth() + 1)+'-'+startTime.getDate();
	startEle.datebox({editable: false});
	endEle.datebox({editable: false});
	startEle.datebox("setValue", startTime);
	endEle.datebox("setValue", endTime);
}
