/**
 * select下拉框生成options
 * @param $select
 * @param arrData
 * @param allSelected
 */
function selectAppendOptions($select, arrData, allSelected) {
    if($select.length > 0){
        if(allSelected){
            $select.prepend("<option value='*' selected>全选</option>");
        }
        $.each(arrData, function(index, value){
            $select.append("<option value='"+value.id+"'>"+value.text+"</option>");
        });
    }
}

/**
 * 组合框加载本地列表数据
 * @param $select
 * @param arrData
 * @param allSelected
 */
function comboboxLoadData($select, arrData, allSelected) {
    if($select.length > 0) {
        if(allSelected){
            arrData.unshift({"id":"*", "text":"全选"})
        }
        $select.combobox('loadData', arrData);
    }
}




/**
 *  Multiple Select 生成下拉框
 * @param $select 下拉控件对象
 * @param arrData 下拉列表数据
 * @param allSelected 是否全选
 */
function multipleSelAppendOptions($select, arrData, allSelected) {
    if($select.length > 0){
        //先清除原数据
        $select.empty();
        //Destroying Chosen
        //$select.chosen("destroy");
        if(allSelected){
            $select.prepend("<option value='*' selected>全选</option>");
        }
        $.each(arrData, function(index, value){
            $select.append("<option value='"+value.id+"'>"+value.text+"</option>");
        });
        $select.trigger("chosen:updated");
    }
}

/**
 * multipleSel值显示
 * @param $select 下拉控件对象
 * @param arrData 下拉控件值
 */
function multipleSelValDispaly($select, arrData) {
    if($select.length > 0){
        $select.val(arrData);
        $select.trigger("chosen:updated");
    }
}
