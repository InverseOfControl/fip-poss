

function  initData(id) {

    if($.trim(id) == ''){
        $('#effectiveDate').datebox('setValue', formatterDate(new Date()));
        $('#expiryDate').datebox('setValue', '2099-01-01');

        //国家
        $.get('/charge/selectEnum/getData?type=countryCode', function (data) {
            multipleSelAppendOptions($('#countryCode'), data , true);
        })

        //卡组织
        $.get('/charge/selectEnum/getData?type=cardOrg', function (data) {
            multipleSelAppendOptions($('#cardOrg'), data , true);
        })

    }

    //币种下下拉、避免查询多次
    $.get('/charge/selectEnum/getData?type=currency', function (data) {
        comboboxLoadData($('#fixedCurrency'), data , false);
        comboboxLoadData($('#dealCurrency'), data , false);
        //costSettlementCurrency
        comboboxLoadData($('#costSettlementCurrency'), data , false);
        if($.trim(id) == ''){
            //多选框生成下拉列表
            multipleSelAppendOptions($('#channelCurrencyCode'), data , true);
        }
    });

    /********计费规则类型********/

    //交易类型
    getDropDownList('chargeScene', false);
    //单笔/批量
    getDropDownList('chargeMode', false);
    //规则类型
    getDropDownList('chargeRule', false);



    /********计费维度********/

    //地区
    getDropDownList('region', true);

    //卡类型
    getDropDownList('cardType', true);

    //账户类型
    getDropDownList('accountType', true);

    //支付方式
    getDropDownList('payMode', true);

    //交易方式
    getDropDownList('transactionMode', true);

    //交易模型
    getDropDownList('tradeModel', true);

    //到账时效
    getDropDownList('remitTimeliness', true);


    //子渠道暂时全选
    multipleSelAppendOptions($('#subChannelCode'), new Array(), true);

    //交易状态
    getDropDownList('tradeStatus', true);


    //渠道号
    $.get('/charge/selectEnum/getChannelCodeList', function (data) {
        //商户计费维度渠道号
        var $merchChannelCode = $('#merchChannelCode');
        comboboxLoadData($merchChannelCode, data , true);
        $merchChannelCode.combobox('setValue', "*");

        //渠道配置渠道号
        comboboxLoadData($('#channelCode'), data , false);
    })


    /********计费规则状态********/
    //状态
    getDropDownList('chargeStatus', false);
    $('#chargeStatus').combobox('setValue', '1');

    /********计费属性********/
    //计费周期
    getDropDownList('settlementCycle', false);
    //收费方式
    getDropDownList('chargeWay', false);
}


/**
 * 获取下拉框数据
 * @param type
 * @param selAll 是否全部
 */
function getDropDownList(type, selAll){
    var append = '';
    if(selAll){
        append = '&selAll=true';
    }
    $('#'+type).combobox({
        url:'/charge/selectEnum/getData?type='+type + append,
        valueField:'id',
        textField:'text'
    });
}



/**
 * 页面根据数据动态显示规则内容
 */
function initRuleContent(id) {
    $("#ruleBtnDiv").hide();

    //删除按钮绑定事件
    $('#delBtn').bind('click', function(){
        $('#ruleTable tr:last').remove();
    });

    $('#chargeScene').combobox({
        onChange:function (newValue, oldValue) {
            var isUpdateInit = false;//是否是初始化调用
            if($.trim(newValue) == ''){
                return;
            }
            if($.trim(oldValue) == '' && $.trim($('#id').val()) !=''){
                isUpdateInit = true;
            }
            ruleDivTableTr(newValue, $("#chargeRule").combobox('getValue'), isUpdateInit);
        }
    });

    $('#chargeRule').combobox({
        onChange:function (newValue, oldValue) {
            var isUpdateInit = false;//是否是初始化调用
            if($.trim(newValue) == ''){
                return;
            }
            if($.trim(oldValue) == '' && $.trim($('#id').val()) !=''){
                isUpdateInit = true;
            }
            ruleDivTableTr($("#chargeScene").combobox('getValue'), newValue, isUpdateInit);
        }
    });

    //二级商户号：联动
    if(id ==null || $.trim(id)==''){
        $('#channelCode').combobox({
            onChange:function (newValue, oldValue) {
                //根据新值,查询二级商户号
                $.get('/charge/selectEnum/secondaryAccountListByCode?channelCode='+ newValue, function (data) {
                    multipleSelAppendOptions($('#secondaryAccount'), data, true);
                })
            }
        });
    }

    //绑定change事件，* 包含其他的
    $('.chosen-select').chosen().change(function () {
        //获取当前值
        var dataArr = $(this).val();
        if(dataArr !=null && dataArr.length > 1){
            for(var i=0; i<dataArr.length; i++){
                if(dataArr[i] == '*'){
                    $(this).val(['*']);
                    break;
                }
            }
        }
        $(this).trigger("chosen:updated");
    });
}


/**
 * 规则明细
 * @param chargeScene
 * @param chargeRule
 * @param isInit
 */
function ruleDivTableTr(chargeScene, chargeRule, isUpdateInit) {
    var defaultTrHtml= "";
    var $ruleTable= $('#ruleTable');
    if(chargeScene =='5' || chargeScene =='6' ){//退款、退票
        $('#addBtn').unbind('click');//先解绑
        $("#ruleBtnDiv").hide();
        defaultTrHtml = template($('#refundTpl').html(), chargeStrgDtlDeal(new Object()));
    }else{
        if(chargeScene == '2' && '2' == chargeRule){//提现、阶梯价格
            defaultTrHtml = template($('#withDrawLadderPriceTpl').html(), chargeStrgDtlDeal(new Object()));
        }else if(chargeScene == '4' && '2' == chargeRule){//拒付、阶梯价格
            defaultTrHtml = template($('#protestLadderPriceTpl').html(), chargeStrgDtlDeal(new Object()));
        }else if('1'== chargeRule){//价格
            defaultTrHtml = template($('#priceTpl').html(), chargeStrgDtlDeal(new Object()));
        }else if('2' == chargeRule){//通用阶梯价格
            defaultTrHtml = template($('#ladderPriceTpl').html(), chargeStrgDtlDeal(new Object()));
        }
        $("#ruleBtnDiv").show();
        $('#addBtn').unbind('click');//先解绑
        $('#addBtn').bind('click', function(){
            $ruleTable.append(defaultTrHtml);
            //table的子元素input、select 验证器初始化
            fromValidateInit($ruleTable);
        });
    }
    //非修改初始化，规则明细页面清空后添加
    if(!isUpdateInit){
        $('#ruleTable tr').remove();
        $ruleTable.append(defaultTrHtml);
        //table的子元素input、select 验证器初始化
        fromValidateInit($ruleTable);
    }
}

/**
 * 匹配维度默认 若没选默认 *
 * @param data
 */
function matchParamDefault(data) {
    var param = arrToString(data, ',');
   return param == ""? "*":param;
}



function merchRuleDtlDisplay(chargeStrgDTO, form) {
    ruleDtlDisplay(chargeStrgDTO, form);
}


function channelRuleDtlDisplay(chargeStrgDTO, form) {
    //币种下下拉、避免查询多次
    $.get('/charge/selectEnum/getData?type=currency', function (data) {
        //多选框生成下拉列表
        multipleSelAppendOptions($('#channelCurrencyCode'), data , true);
        multipleSelValDispaly($("#channelCurrencyCode"), strToArr(chargeStrgDTO.channelCurrencyCode, ','));
    });
    //查询二级商户号
    $.get('/charge/selectEnum/secondaryAccountListByCode?channelCode='+ chargeStrgDTO.channelCode, function (data) {
        multipleSelAppendOptions($('#secondaryAccount'), data, true);
        multipleSelValDispaly($("#secondaryAccount"), strToArr(chargeStrgDTO.secondaryAccount, ','));
    });
    multipleSelValDispaly($("#subChannelCode"), strToArr(chargeStrgDTO.subChannelCode, ','));

    ruleDtlDisplay(chargeStrgDTO, form);
}

/**
 * 展示明细包含多选框回显、不同类型对应的规则模板
 */
function ruleDtlDisplay(chargeStrgDTO, form) {

    //国家
    $.get('/charge/selectEnum/getData?type=countryCode', function (data) {
        multipleSelAppendOptions($('#countryCode'), data , true);
        multipleSelValDispaly($("#countryCode"), strToArr(chargeStrgDTO.countryCode, ','));
    })
    //卡组织
    $.get('/charge/selectEnum/getData?type=cardOrg', function (data) {
        multipleSelAppendOptions($('#cardOrg'), data , true);
        multipleSelValDispaly($("#cardOrg"), strToArr(chargeStrgDTO.cardOrg, ','));
    })

    var list = chargeStrgDTO.strgDtlDTOList;
    var chargeScene = chargeStrgDTO.chargeScene;
    var chargeRule = chargeStrgDTO.chargeRule;

    var $ruleTable = $('#ruleTable');
    if($.trim(chargeStrgDTO.id) != ''){
        var $merchantId = $('#merchantId');
        if($merchantId.length >0){
            $merchantId.textbox('textbox').attr("readonly", true);
        }
        $('#channelCode').combobox('disable', true);
    }

    var trHtml = null;
    if (chargeScene == '5' || chargeScene == '6') {//退款、退票
        //refundTrHtml
        trHtml = $('#refundTpl').html();
    } else {
        if(chargeScene == '2' && '2' == chargeRule){//提现、阶梯价格
            trHtml = $('#withDrawLadderPriceTpl').html();
        }else if(chargeScene == '4' && '2' == chargeRule){//拒付、阶梯价格
            trHtml =  $('#protestLadderPriceTpl').html();
        }else if ('1' == chargeRule) {//价格
            trHtml = $('#priceTpl').html();
        }else if ('2' == chargeRule) {//阶梯价格
            trHtml = $('#ladderPriceTpl').html();
        }
    }
    $('#ruleTable tr').remove();
    $.each(list, function (i, chargeStrgDtl) {
        //使用模板渲染数据
        $ruleTable.append(template(trHtml, chargeStrgDtlDeal(chargeStrgDtl)));
    });
    //table的子元素input、select 验证器初始化
    fromValidateInit($ruleTable);

    //若是查看，不可编辑
    if(Boolean(chargeStrgDTO.isDetail)){
        form.find('input,textarea,select').attr('readonly',true);
        $('#ruleBtnDiv').hide();
    }

}




/**
 * 获取tr中规则属性的值
 * @param tdArr
 * @returns {Object}
 */
function getRuleDtlObj(tdArr) {
    var chargeStrgDtl = new Object();
    //第一个td内容
    chargeStrgDtl.sMinAmount = tdArr.eq(0).find('input[name=sMinAmount]').val();
    chargeStrgDtl.sMaxAmount = tdArr.eq(0).find('input[name=sMaxAmount]').val();
    if(compareVal(chargeStrgDtl.sMinAmount, chargeStrgDtl.sMaxAmount)){
        throw Error("单笔金额最大值不能小于最小值");
    }
    chargeStrgDtl.tMinCount = tdArr.eq(0).find('input[name=tMinCount]').val();
    chargeStrgDtl.tMaxCount = tdArr.eq(0).find('input[name=tMaxCount]').val();
    if(compareVal(chargeStrgDtl.tMinCount, chargeStrgDtl.tMaxCount)){
        throw Error("总笔数最大值不能小于最小值");
    }
    chargeStrgDtl.tMinAmount = tdArr.eq(0).find('input[name=tMinAmount]').val();
    chargeStrgDtl.tMaxAmount = tdArr.eq(0).find('input[name=tMaxAmount]').val();
    if(compareVal(chargeStrgDtl.tMinAmount, chargeStrgDtl.tMaxAmount)){
        throw Error("总金额最大值不能小于最小值");
    }
    chargeStrgDtl.conditionalRelation = tdArr.eq(0).find('input[name=conditionalRelation]').val();
    chargeStrgDtl.percentFeeRefundType = tdArr.eq(0).find('input[name=percentFeeRefundType]').val();
    chargeStrgDtl.fixedFeeRefundType = tdArr.eq(0).find('input[name=fixedFeeRefundType]').val();

    //拒付率信息
    chargeStrgDtl.protestMinRate = tdArr.eq(0).find('input[name=protestMinRate]').val();
    chargeStrgDtl.protestMaxRate = tdArr.eq(0).find('input[name=protestMaxRate]').val();
    if(compareVal(chargeStrgDtl.protestMinRate, chargeStrgDtl.protestMaxRate)){
        throw Error("拒付率最大值不能小于最小值");
    }
    //第二个td内容
    chargeStrgDtl.percent = tdArr.eq(1).find('input[name=percent]').val();
    chargeStrgDtl.percentMinFee = tdArr.eq(1).find('input[name=percentMinFee]').val();
    chargeStrgDtl.percentMaxFee = tdArr.eq(1).find('input[name=percentMaxFee]').val();
    if(compareVal(chargeStrgDtl.percentMinFee, chargeStrgDtl.percentMaxFee)){
        throw Error("比例费封顶金额不能小于保底金额");
    }
    var fixedFee = tdArr.eq(1).find('input[name=fixedFee]').val();
    var dealFee = tdArr.eq(1).find('input[name=dealFee]').val();
    var fixedCurrency = $('#fixedCurrency').val();
    if((fixedCurrency == null || fixedCurrency =='') && (fixedFee != null && fixedFee != '')){
        //固定费存在 币种不能为空
        throw Error("固定费币种不能为空");
    }
    var dealCurrency = $('#dealCurrency').val();
    if((dealCurrency == null || dealCurrency == '') && (dealFee != null && dealFee != '')){
        //处理费存在 币种不能为空
        throw Error("处理费币种不能为空");
    }
    chargeStrgDtl.fixedFee = fixedFee;
    chargeStrgDtl.dealFee = dealFee;
    return chargeStrgDtl;
}



function chargeStrgDtlDeal(chargeStrgDtl) {
    if(chargeStrgDtl.sMinAmount == null){
        chargeStrgDtl.sMinAmount = "";
    }
    if(chargeStrgDtl.sMaxAmount == null){
        chargeStrgDtl.sMaxAmount = "";
    }

    if(chargeStrgDtl.tMinCount == null){
        chargeStrgDtl.tMinCount="";
    }
    if(chargeStrgDtl.tMaxCount == null){
        chargeStrgDtl.tMaxCount="";
    }

    if(chargeStrgDtl.tMinAmount == null){
        chargeStrgDtl.tMinAmount="";
    }

    if(chargeStrgDtl.tMaxAmount == null){
        chargeStrgDtl.tMaxAmount="";
    }

    if(chargeStrgDtl.conditionalRelation == null){
        chargeStrgDtl.conditionalRelation="";
    }
    if(chargeStrgDtl.percentFeeRefundType == null){
        chargeStrgDtl.percentFeeRefundType="";
    }
    if(chargeStrgDtl.fixedFeeRefundType == null){
        chargeStrgDtl.fixedFeeRefundType="";
    }
    if(chargeStrgDtl.percent == null){
        chargeStrgDtl.percent="";
    }
    if(chargeStrgDtl.percentMinFee == null){
        chargeStrgDtl.percentMinFee="";
    }
    if(chargeStrgDtl.percentMaxFee == null){
        chargeStrgDtl.percentMaxFee="";
    }
    if(chargeStrgDtl.fixedFee == null){
        chargeStrgDtl.fixedFee="";
    }
    if(chargeStrgDtl.dealFee == null){
        chargeStrgDtl.dealFee="";
    }

    if(chargeStrgDtl.protestMinRate == null){
        chargeStrgDtl.protestMinRate="";
    }

    if(chargeStrgDtl.protestMaxRate == null){
        chargeStrgDtl.protestMaxRate="";
    }
    return chargeStrgDtl;
}


/**
 *
 * @param val1
 * @param val2
 * @returns {boolean}
 */
function compareVal(val1,val2){
    if(isRealNum(val1) && isRealNum(val2) ){
        return Number(val2) < Number(val1);
    }
    return false;
}
