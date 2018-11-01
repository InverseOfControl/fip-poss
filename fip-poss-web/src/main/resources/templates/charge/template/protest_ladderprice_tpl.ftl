<script id="protestLadderPriceTpl" type="text/html">
    <#--拒付，阶梯价格模板-->
    <tr>
        <td>
            <div class="field">
                <div class="field">
                    <label for="protestMinRate" style="display:inline-block;width:10px">*</label>
                    <input id="protestMinRate" class="easyui-textbox" name="protestMinRate"  style="width:50px" data-options="required:true" value="<%=protestMinRate%>" validType="number">
                    <label for="protestMaxRate" style="display:inline-block;width:80px"><=拒付率< *</label>
                    <input id="protestMaxRate" class="easyui-textbox" name="protestMaxRate"   style="width:50px" data-options="required:true" value="<%=protestMaxRate%>" validType="number">
                    <label for="conditionalRelation" style="display:inline-block;width:10px">*</label>
                    <select id="conditionalRelation" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', required:true" name="conditionalRelation" style="width:60px">
                        <option value="">请选择</option>
                        <option value="AND" <% if (conditionalRelation == 'AND') { %> selected="selected" <% } %> >AND</option>
                        <option value="OR" <% if (conditionalRelation == 'OR') { %> selected="selected" <% } %>>OR</option>
                    </select>
                </div>
                <div class="field">
                    <label for="tMinAmount" style="display:inline-block;width:10px">*</label>
                    <input id="tMinAmount" class="easyui-textbox" name="tMinAmount"  style="width:100px" data-options="required:true" value="<%=tMinAmount%>" validType="number">
                    <label for="tMaxAmount" style="display:inline-block;width:80px"><=总金额< *</label>
                    <input id="tMaxAmount" class="easyui-textbox" name="tMaxAmount"   style="width:100px" data-options="required:true" value="<%=tMaxAmount%>" validType="number">
                </div>
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