
/**
 * 请输入不小于 {0} 的整数
 */
$.extend($.fn.validatebox.defaults.rules, {
    minDigits: {
        validator: function(value, param){
            var reg = /^[0-9]*$/;
            if(reg.test(value)){
                return value >= param[0];
            }
            return false;
        },
        message: '请输入不小于{0}的整数'
    }
});



/**
 * 请输入不大于 {0} 的整数
 */
$.extend($.fn.validatebox.defaults.rules, {
    maxDigits: {
        validator: function(value, param){
            var reg = /^[0-9]*$/;
            if(reg.test(value)){
                return value <= param[0];
            }
            return false;
        },
        message: '请输入不大于{0}的整数'
    }
});


/**
 * 请输入范围在{0}到{1}之间的整数
 */
$.extend($.fn.validatebox.defaults.rules, {
    rangeDigits: {
        validator: function(value, param){
            var reg = /^[0-9]*$/;
            if(reg.test(value)){
                return value >= param[0] && value <= param[1];
            }
            return false;
        },
        message: '请输入范围在{0}到{1}之间的整数'
    }
});


/**
 * 请输入不小于 {0} 的数值
 */
$.extend($.fn.validatebox.defaults.rules, {
    min: {
        validator: function(value, param){
            var reg = /^(\-|\+)?\d+(\.\d+)?$/;
            if(reg.test(value)){
                return value >= param[0];
            }
            return false;
        },
        message: '请输入不小于{0}的数值'
    }
});



/**
 * 请输入不大于 {0} 的数值
 */
$.extend($.fn.validatebox.defaults.rules, {
    max: {
        validator: function(value, param){
            var reg = /^(\-|\+)?\d+(\.\d+)?$/;
            if(reg.test(value)){
                return value <= param[0];
            }
            return false;
        },
        message: '请输入不大于{0}的数值'
    }
});



/**
 * 请输入范围在{0}到{1}之间的数值
 */
$.extend($.fn.validatebox.defaults.rules, {
    range: {
        validator: function(value, param){
            var reg = /^(\-|\+)?\d+(\.\d+)?$/;
            if(reg.test(value)){
                return value >= param[0] && value <= param[1];
            }
            return false;
        },
        message: '请输入范围在{0}到{1}之间的数值'
    }
});



/**
 * 必须输入合法的数字（负数，小数）
 */
$.extend($.fn.validatebox.defaults.rules, {
    number: {
        validator: function(value, param){
            var reg = /^(\-|\+)?\d+(\.\d+)?$/;
            return reg.test(value);
        },
        message: '请输入输入合法的数值'
    }
});





/**
 * 最多可以输入{0}个字符
 */
$.extend($.fn.validatebox.defaults.rules, {
    maxlength: {
        validator: function(value, param){
            return value.length <= param[0];
        },
        message: '最多可以输入{0}个字符'
    }
});



/**
 * 最少要输入{0}个字符
 */
$.extend($.fn.validatebox.defaults.rules, {
    minlength: {
        validator: function(value, param){
            return value.length >= param[0];
        },
        message: '最少要输入{0}个字符'
    }
});

/**
 * 请输入长度在{0}到{1}之间的字符串
 */
$.extend($.fn.validatebox.defaults.rules, {
    rangelength: {
        validator: function(value, param){
            return value.length >= param[0] && value.length <= param[1];
        },
        message: '请输入长度在{0}到{1}之间的字符串'
    }
});


/**
 * 针对combobox editable=true, 校验输入项为下拉选项
 */
$.extend($.fn.validatebox.defaults.rules, {
    comboxRequired: {
        validator: function(value, param){
            var dataArr =  $(param[0]).combobox('getData');
            var val = $(param[0]).val();
            var valid = false;
            if(val == null ||  $.trim(val) == ''){
                return valid;
            }
            if(dataArr != null && dataArr.length > 0){
                for(var i=0; i < dataArr.length; i++){
                    var data = dataArr[i];
                    if(data.id == val || data.value == val){
                        valid = true;
                        break;
                    }
                }
            }
            return valid;
        },
        message: '必须选择一个'
    }
});
