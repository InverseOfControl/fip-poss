<script id="priceTpl" type="text/html">
    <#--价格模板-->
    <tr>
        <td>
            <div class="field">
                <label for="sMinAmount" style="display:inline-block;width:10px">*</label>
                <input id="sMinAmount" class="easyui-textbox" name="sMinAmount"  style="width:100px" data-options="required:true" value="<%=sMinAmount%>" validType="number">
                <label for="sMaxAmount" style="display:inline-block;width:80px"><=单笔金额<</label>
                <input id="sMaxAmount" class="easyui-textbox" name="sMaxAmount"   style="width:100px" data-options="required:true" value="<%=sMaxAmount%>" validType="number">
            </div>
        </td>
        <td>
            <div class="field">
                <div class="field">
                    <label for="percent" style="display:inline-block;width: 100px">比例费：*费率</label>
                    <input id="percent" class="easyui-textbox" name="percent" style="width: 50px" data-options="required:true"  value="<%=percent%>" validType="number">
                    <label>%</label>
                </div>
                <div class="field">
                    <label for="percentMinFee"  style="display:inline-block;width: 100px">*比例费保底金额</label>
                    <input id="percentMinFee" class="easyui-textbox" name="percentMinFee" style="width: 65px" data-options="required:true" value="<%=percentMinFee%>" validType="number">
                    <label for="percentMaxFee"  style="display:inline-block;width: 100px">*比例费封顶金额</label>
                    <input id="percentMaxFee" class="easyui-textbox" name="percentMaxFee" style="width: 65px" data-options="required:true" value="<%=percentMaxFee%>" validType="number">
                </div>
                <div class="field">
                    <label for="fixedFee" style="display:inline-block;width: 100px">固定费：*金额</label>
                    <input id="fixedFee" class="easyui-textbox" name="fixedFee" style="width: 65px" data-options="required:true" value="<%=fixedFee%>" validType="number">
                    <label for="dealFee" style="display:inline-block;width: 100px">处理费：*金额</label>
                    <input id="dealFee" class="easyui-textbox" name="dealFee" style="width: 65px" data-options="required:true" value="<%=dealFee%>" validType="number">
                </div>
            </div>
        </td>
    </tr>
</script>