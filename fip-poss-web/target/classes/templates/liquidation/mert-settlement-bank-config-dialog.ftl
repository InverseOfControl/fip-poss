<style>
    .input-width{ width: 250px;}
</style>
<form method="post" id="mert_settlement_bank_config_dialog" style="margin: 15px 30px;">
    <input name="id" type="hidden" value="${id}">
    <table style="width: 100%;">
        <tr>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="merchantId" required="true" label="商户号"/></td>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="merchantName" required="true" label="商户名称"/></td>
        </tr>
        <tr>
            <td><input labelWidth="100" id="currencyType0" class="input-width" name="settlementCurrency" required="true" label="结算币种"/></td>
            <td></td>
        </tr>
        <tr style="height: 30px;"><td colspan="2"><td></tr>
        <tr>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="account" label="账号"/></td>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="accountName" label="账户名称"/></td>
        </tr>
        <tr>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="swiftCode" label="Swift Code"/></td>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="electronicLinkNumber" label="电子联行号"/></td>
        </tr>
        <tr>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="ibanCode" label="IBAN号"/></td>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="accountSwiftCode" label="账户号SwiftCode"/></td>
        </tr>
        <tr>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="openBankName" label="开户行名称"/></td>
            <td><input labelWidth="100" class="easyui-textbox input-width" name="openBankAddress" label="开户行地址"/></td>
        </tr>
    </table>
</form>
