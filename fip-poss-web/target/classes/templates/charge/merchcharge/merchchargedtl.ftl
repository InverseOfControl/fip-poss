<form class="app-form" id="merchchargedtl-form">
    <div class="commonBord">
        <table>
            <tr>
                <th><div class="field">商户信息:</div></th>
                <td><div style="display:none"><input id="id" class="easyui-textbox" name="id" type="hidden"></div></td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="merchantId" class="easyui-textbox" name="merchantId" validType="maxlength[32]"   label="*商户号:" labelWidth="80" style="width:200px;" data-options="required:true">
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="chargeRuleName" class="easyui-textbox" name="chargeRuleName" validType="maxlength[60]"  label="*规则名称:" labelWidth="80" style="width:300px;" data-options="required:true">
                    </div>
                </td>
            </tr>
        </table>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <th>
                    <div class="field">计费规则类型:</div>
                </th>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="chargeScene" class="easyui-combobox" name="chargeScene" validType="comboxRequired['#chargeScene']" label="*交易类型:" labelWidth="80" style="width:200px;"
                               data-options="panelHeight:'auto',required:true" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="chargeMode" class="easyui-combobox"  name="chargeMode" validType="comboxRequired['#chargeMode']" label="*单笔/批量:" labelWidth="80"  style="width:200px;"
                               data-options="panelHeight:'auto',required:true" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="chargeRule" class="easyui-combobox" name="chargeRule" validType="comboxRequired['#chargeRule']" label="*规则类型:" labelWidth="80"  style="width:200px;"
                               data-options="panelHeight:'auto',required:true" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <th><div class="field">计费维度: </div></th>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="region" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="地区:" name="region"  labelWidth="80" style="width:300px;"/>
                    </div>
                </td>
                <td>
                    <div class="field">
                        <div class="field">
                            <label for="countryCode" style="display:inline-block;width: 80px">国家:</label>
                            <select id="countryCode" name="countryCode"  multiple class="chosen-select" style="width:220px;">
                            </select>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <div>
                            <label for="cardOrg" style="display:inline-block;width: 80px">卡组织:</label>
                            <select id="cardOrg" name="cardOrg"  multiple class="chosen-select" style="width:220px;">
                            </select>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="cardType" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="卡类型:" name="cardType" labelWidth="80" style="width:300px;" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="accountType" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="账户类型:" name="accountType" labelWidth="80" style="width:300px;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="payMode" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="支付方式:" name="payMode" labelWidth="80" style="width:300px;"/>
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="transactionMode" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="交易方式:" name="transactionMode" labelWidth="80" style="width:300px;" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="tradeModel" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="交易模型:" name="tradeModel" labelWidth="80" style="width:300px;"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="merchChannelCode" class="easyui-combobox" name="channelCode" label="渠道:" labelWidth="80" style="width:300px;"
                               data-options="valueField:'id',textField:'text',panelHeight:'auto'"  />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="remitTimeliness" class="easyui-combobox" editable="false" data-options="panelHeight:'auto'" label="到账时效:" name="remitTimeliness" labelWidth="80" style="width:300px;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <th><div class="field">计费规则状态:</div></th>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="chargeStatus" class="easyui-combobox" name="status"  label="*状态:"  labelWidth="80" style="width:200px;" validType="comboxRequired['#chargeStatus']"
                               data-options="required:true, panelHeight:'auto'"/>
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="effectiveDate" class="easyui-datebox" name="effectiveDate" labelWidth="80" style="width:200px;" data-options="label:'*生效日期:',editable:false, required:true" >
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="expiryDate" class="easyui-datebox" name="expiryDate" labelWidth="80" style="width:200px;" data-options="label:'*失效日期:',editable:false, required:true">
                    </div>
                </td>
                <td>
                    <div class="field">
                        <label for="priority" style="display:inline-block;width: 80px">*优先级:</label>
                        <input id="priority" class="easyui-textbox" name="priority" style="width:120px;"  prompt="值越大优先级越高" validType="rangeDigits[1,999]" data-options="required:true">
                    </div>
                </td>
            </tr>
        </table>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <th><div class="field">计费规则: </div></th>
            </tr>
        </table>
        <div class="field">
            <div id="ruleBtnDiv" class="field" align="right">
                <a href="#" id="delBtn" class="easyui-linkbutton" plain="true" iconCls="fa fa-trash" ></a>
                <a href="#" id="addBtn" class="easyui-linkbutton" plain="true" iconCls="fa fa-plus-square" ></a>
            </div>
            <div class="field">
                <table id="ruleTable">
                </table>
            </div>
        </div>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <td></br></td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="fixedCurrency" class="easyui-combobox" name="fixedCurrency" label="固定费: 币种" labelWidth="80" style="width:200px;"  validType="comboxRequired['#fixedCurrency']"
                               data-options="valueField:'id',textField:'text'" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="dealCurrency" class="easyui-combobox" name="dealCurrency"  label="处理费: 币种" labelWidth="80" style="width:200px;"   validType="comboxRequired['#dealCurrency']"
                               data-options="valueField:'id',textField:'text'" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    </br>
    <div class="commonBord">
        <table>
            <tr>
                <th><div class="field">计费属性: </div></th>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="settlementCycle" class="easyui-combobox"  validType="comboxRequired['#settlementCycle']"  label="*费用结算周期:" labelWidth="100" name="settlementCycle" style="width:200px;"
                            data-options="panelHeight:'auto',required:true" />
                    </div>
                </td>
                <td>
                    <div class="field">
                        <input id="settlementDate" class="easyui-textbox" name="settlementDate"   label="*结算日:" labelWidth="50" style="width:150px;" prompt="0-31整数" data-options="required:true" validType="rangeDigits[0,31]">
                    </div>
                </td>
                <td>
                    <div class="field">
                        <select id="settlementDateType" class="easyui-combobox" validType="comboxRequired['#settlementDateType']" data-options="panelHeight:'auto', required:true" label="*结算日期类型:" labelWidth="100"  name="settlementDateType" style="width:200px;">
                            <option value="D">自然日</option>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="field">
                        <input id="chargeWay" class="easyui-combobox"  validType="comboxRequired['#chargeWay']" label="*收费方式:" labelWidth="100" name="chargeWay" style="width:200px;"
                            data-options="panelHeight:'auto',required:true" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
</form>


<#--引入模板-->
<#include "../template/price_tpl.ftl"/>
<#include "../template/ladderprice_tpl.ftl"/>
<#include "../template/refund_tpl.ftl"/>
<#include "../template/protest_ladderprice_tpl.ftl"/>
<#include "../template/withdraw_ladderprice_tpl.ftl"/>


 <script>
     <#if merchChargeStrgDTO??>
         $(function () {
             var obj = $('#merch_dlg').dialog('options');
             var queryParams = obj["queryParams"];//获取父页面参数
             //需要延迟一点执行，等待页面所有组件都初始化好，再执行数据初始化
             setTimeout(function () {
                 var chargeStrgDTO = ${merchChargeStrgDTO};
                 var form = $("#merchchargedtl-form");
                 form.form("load", chargeStrgDTO);
                 chargeStrgDTO.isDetail = queryParams.isDetail;
                 merchRuleDtlDisplay(chargeStrgDTO, form);
             }, 200);
         });
     </#if>

    $(function() {
        $('.chosen-select').chosen();
        $('.chosen-select-deselect').chosen({ allow_single_deselect: true });
    });
 </script>

<style type="text/css">
    .commonBord{
        border:1px solid #095157
    }
</style>

