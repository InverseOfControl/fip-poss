<form class="app-form" id="addchannelfile-form" method="post" enctype="multipart/form-data" action="/recon/channelfile/addChannelFileDetails">
    <div class="field" style="margin-top:25px;margin-left: 50px;">
        <input class="easyui-combobox" name="orgCode" style="width:260px;height: 26px;"
               data-options="label:'渠道：',labelWidth:'110',valueField:'code',panelWidth:'auto', panelHeight:'auto',textField:'name',url:'/recon/channelfile/channels',editable:false,multiple:false"/>
    </div>
    <div class="field" style="margin-top:40px;margin-left: 50px;">
        <input class="easyui-combobox" name="uploadType" style="width:260px;height: 26px;"
               data-options="label:'文件类型：',labelWidth:'110',valueField:'code',panelWidth:'auto', panelHeight:'auto',textField:'message',url:'/recon/channelfile/fileType',editable:false,multiple:false"/>
    </div>
    <div class="field" style="margin-top:40px;margin-left: 50px;">
        <input id="file" name="file"
               class="easyui-filebox" style="width: 260px; height: 26px;"
               data-options="label:'请选择对账文件：',labelWidth:'110',prompt:'请选择文件'">
    </div>

</form>
