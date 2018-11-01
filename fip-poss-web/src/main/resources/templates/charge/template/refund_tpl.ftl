<script id="refundTpl" type="text/html">
    <#--退款模板-->
    <tr>
        <td>
            <div class="field">
                <div class="field">
                    <select id="percentFeeRefundType" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', required:true"  name="percentFeeRefundType" label="比例费:" labelWidth="80" style="width:200px;">
                        <option value="">请选择</option>
                        <option value="0" <% if (percentFeeRefundType == '0') { %> selected="selected" <% } %> >不退</option>
                        <option value="1" <% if (percentFeeRefundType == '1') { %> selected="selected" <% } %> >等比例退</option>
                        <option value="2" <% if (percentFeeRefundType == '2') { %> selected="selected" <% } %> >全额退才退</option>
                    </select>
                </div>
                <div class="field">
                    <select id="fixedFeeRefundType" class="easyui-combobox" editable="false" data-options="panelHeight:'auto', required:true"  name="fixedFeeRefundType" label="固定费:" labelWidth="80"  style="width:200px;">
                        <option value="">请选择</option>
                        <option value="0" <% if (fixedFeeRefundType == '0') { %> selected="selected" <% } %> >不退</option>
                        <option value="1" <% if (fixedFeeRefundType == '1') { %> selected="selected" <% } %> >等比例退</option>
                        <option value="2" <% if (fixedFeeRefundType == '2') { %> selected="selected" <% } %> >全额退才退</option>
                    </select>
                </div>
            </div>
        </td>
        <td align="left" valign="bottom">
            <div class="field">
                <div class="field">
                    <input id="dealFee" class="easyui-textbox" name="dealFee" label="处理费:*金额" labelWidth="80" style="width: 145px;" data-options="required:true" value="<%=dealFee%>" validType="number">
                </div>
            </div>
        </td>
    </tr>
</script>