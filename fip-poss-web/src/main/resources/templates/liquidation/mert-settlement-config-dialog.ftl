<#if flag=="add" || flag=="edit">
    <form method="post" id="mert_settlement_config_dialog" style="margin: 15px 30px;" contenteditable="false">
        <input name="id" type="hidden" value="${id}">
        <table style="width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">商户信息</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input name="merchantId" required="true" class="easyui-textbox" label="商户号" labelWidth="100" style="width:250px;"/></td>
                <td><input name="merchantName" required="true" class="easyui-textbox" label="商户名称" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">结算维度</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input id="payMethod" name="paymentMethods" class="easyui-combobox" label="支付方式" labelWidth="100" style="width:250px;"/></td>
                <td><input id="countryType" name="countryCodes" class="easyui-combobox" label="国家" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="channelType" name="channelCodes" class="easyui-combobox" label="渠道" labelWidth="100" style="width:250px;"/></td>
                <td><#--<input name="subChannelCodes" class="easyui-textbox" label="子渠道" labelWidth="100" style="width:250px;"/>--></td>
            </tr>
            <tr>
                <td><input id="currencyType1" required="true" name="transCurrency" class="easyui-combobox" label="交易币种" labelWidth="100" style="width:250px;"/></td>
                <td><input id="transMethod" required="true" name="transMethod" class="easyui-combobox" label="交易方式" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="priority" name="priority" required="true" class="easyui-textbox" label="优先级" labelWidth="100" style="width:250px;"/></td>
                <td></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">结算属性</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input id="currencyType" name="settlementCurrencys" required="true" class="easyui-combobox" label="币种" labelWidth="100" style="width:250px;"/></td>
                <td><input name="startSettlementMoney" required="true" class="easyui-textbox" label="起始结算金额" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="settlementCycle0" name="settlementCycle" required="true" class="easyui-combobox" label="结算周期" labelWidth="100" style="width:250px;"/></td>
                <td><input name="settlementDay" required="true" class="easyui-textbox" label="结算日" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="settlementDayType0" name="settlementDayType" required="true" class="easyui-combobox" label="结算日类型" labelWidth="100" style="width:250px;"/></td>
                <td><input id="autoWithdrawal" name="isWithdrawal" required="true" class="easyui-combobox" label="自动提现" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">保证金</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input name="marginRatio" required="true" class="easyui-textbox" label="比例(%)" labelWidth="100" style="width:250px;"/></td>
                <td><input id="settlementCycle1" name="marginSettlementCycle" required="true" class="easyui-combobox" label="归还周期" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input name="marginSettlementDay" required="true" class="easyui-textbox" label="归还日" labelWidth="100" style="width:250px;"/></td>
                <td><input id="settlementDayType1" name="marginSettlementDayType" required="true" class="easyui-combobox" label="归还日类型" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
    </form>
</#if>


<#if flag=="detail">
    <form method="post" id="mert_settlement_config_dialog" style="margin: 15px 30px;" contenteditable="false">
        <input name="id" type="hidden" value="${id}">
        <table style="width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">商户信息</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input name="merchantId" readonly="readonly" class="easyui-textbox" label="商户号" labelWidth="100" style="width:250px;"/></td>
                <td><input name="merchantName" readonly="readonly" class="easyui-textbox" label="商户名称" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">结算维度</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input id="payMethod" readonly="readonly" name="paymentMethods" label="支付方式" labelWidth="100" style="width:250px;"/></td>
                <td><input id="countryType" readonly="readonly" name="countryCodes" class="easyui-textbox" label="国家" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="channelType" readonly="readonly" name="channelCodes" class="easyui-textbox" label="渠道" labelWidth="100" style="width:250px;"/></td>
                <td></td>
            </tr>
            <tr>
                <td><input id="currencyType1" readonly="readonly" name="transCurrency" class="easyui-combobox" label="交易币种" labelWidth="100" style="width:250px;"/></td>
                <td><input id="transMethod" readonly="readonly" name="transMethod" class="easyui-combobox" label="交易方式" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="priority" readonly="readonly" name="priority" class="easyui-textbox" label="优先级" labelWidth="100" style="width:250px;"/></td>
                <td></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">结算属性</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input id="currencyType" readonly="readonly" name="settlementCurrencys" class="easyui-combobox" label="币种" labelWidth="100" style="width:250px;"/></td>
                <td><input name="startSettlementMoney" readonly="readonly" class="easyui-textbox" label="起始结算金额" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="settlementCycle0" readonly="readonly" name="settlementCycle" required="true" class="easyui-combobox" label="结算周期" labelWidth="100" style="width:250px;"/></td>
                <td><input name="settlementDay" readonly="readonly" class="easyui-textbox" label="结算日" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input id="settlementDayType0" readonly="readonly" name="settlementDayType" class="easyui-combobox" label="结算日类型" labelWidth="100" style="width:250px;"/></td>
                <td><input id="autoWithdrawal" readonly="readonly" name="isWithdrawal" class="easyui-combobox" label="自动提现" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
        <table style="margin-top: 10px;width: 100%;">
            <tr><td colspan="2" style="font-size: 14px;">保证金</td></tr>
            <tr><td colspan="2"><hr style="height:1px;border:none;border-top:1px solid #D3D3D3;margin-right: 30px;"/><td></tr>
            <tr>
                <td><input name="marginRatio" readonly="readonly" class="easyui-textbox" label="比例(%)" labelWidth="100" style="width:250px;"/></td>
                <td><input id="settlementCycle1" readonly="readonly" name="marginSettlementCycle" class="easyui-combobox" label="归还周期" labelWidth="100" style="width:250px;"/></td>
            </tr>
            <tr>
                <td><input name="marginSettlementDay" readonly="readonly" class="easyui-textbox" label="归还日" labelWidth="100" style="width:250px;"/></td>
                <td><input id="settlementDayType1" readonly="readonly" name="marginSettlementDayType" class="easyui-combobox" label="归还日类型" labelWidth="100" style="width:250px;"/></td>
            </tr>
        </table>
    </form>
</#if>
