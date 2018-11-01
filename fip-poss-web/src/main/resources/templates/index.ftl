<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理系统</title>
    <!-- 导入主题样式文件 -->
    <link rel="stylesheet" href="/static/easyui/themes/gray/easyui.css" />
    <!-- 图标 -->
    <link rel="stylesheet" href="/static/easyui/themes/icon.css" />
    <link rel="stylesheet" href="/static/font-awesome/css/font-awesome.min.css" />
    <!-- 项目公共样式 -->
    <link rel="stylesheet" href="/static/css/app.css" />

    <!--layer弹出框样式引用 -->
    <link rel="stylesheet" href="/static/easyui/layer/skin/layer.css" />
    <link rel="stylesheet" href="/static/easyui/layer/skin/layer.ext.css" />
    <!--layer弹出框样式引用 -->

    <!-- chosen多選擇框-->
    <link rel="stylesheet" href="../static/css/chosen/prism.css">
    <link rel="stylesheet" href="../static/css/chosen/chosen.css">


    <!-- 第一脚本：jquery，必须是在第一位 -->
    <script src="/static/easyui/jquery.min.js" charset="utf-8"></script>
    <!-- easyui的核心 -->
    <script src="/static/easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <!-- Easyui的扩展 -->
    <script src="/static/easyui/jquery.edatagrid.js" charset="utf-8"></script>
    <!-- Easyui tabs 的扩展 -->
    <script src="/static/easyui/jquery.easyui.tabs.extension.js" charset="utf-8"></script>
    <!-- Easyui的国际化 -->
    <script src="/static/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
    <!-- Easyui的bug修复包 -->
    <script src="/static/easyui/fixed.js" charset="utf-8"></script>
    <script src="/static/js/lib/xss.js" charset="utf-8"></script>

    <!--layer弹出框样式引用 -->
    <script src="/static/easyui/layer/layer.js" charset="utf-8"></script>
    <script src="/static/easyui/layer/extend/layer.ext.js" charset="utf-8"></script>
    <!--layer弹出框样式引用 -->


    <!--权限资源-->
    <script src="/resource" charset="utf-8"></script>

    <!--webSocket的支持-->
    <script src="/static/js/lib/sockjs.min.js" charset="utf-8"></script>

    <script src="/static/js/lib/vue.js" charset="utf-8"></script>
    <!-- template模板-->
    <script src="/static/js/lib/template.js" charset="utf-8"></script>
    <!-- chosen ，Multiple Select-->
    <script src="/static/js/lib/chosen.jquery.js" charset="utf-8"></script>

    <!--限制弹出层被拖拽到浏览器可视范围外-->
    <script src="/static/js/lib/dialog.rang.js" charset="utf-8"></script>

    <script src="/static/js/liquidation/date.js" charset="utf-8"></script>

    <!--Multiple Select-->
    <script src="/static/js/common/chosen.select.js" charset="utf-8"></script>
    <script src="/static/js/common/commutil.js" charset="utf-8"></script>
    <!--validatebox 验证方法-->
    <script src="/static/js/common/validate.js" charset="utf-8"></script>
    <script src="/static/js/charge/charge.js" charset="utf-8"></script>

    <script>
        var MEMBER = {
            id:${s_member.id?c},
            realName: '${s_member.realName}',
            userName: '${s_member.userName}'
        };
    </script>
    <script src="/static/js/require.js" charset="utf-8" data-main="/static/js/app" defer async="true"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height: 35px;overflow: hidden;padding: 0 10px;">
    <div class="user-info">
        <span class="item" id="public_change_info"><i class="fa fa-user"></i> ${s_member.realName}</span>
        <span class="item" id="public_change_password"><i class="fa fa-user"></i> 修改密码</span>
        <a class="item" href="/logout"><i class="fa fa-sign-out"></i> 注销</a>
    </div>
    <div style="float: left;font-size: 19px;font-weight: bold;font-family: Ravie;line-height: 25px;height: 25px; background-image: -webkit-gradient(linear, 0 0, 0 bottom, from(rgba(241, 166, 86, 1)), to(rgba(227, 204, 169, 1)));-webkit-background-clip: text;-webkit-text-fill-color: transparent;">
        <img style="vertical-align:middle;" src="/static/images/ipl_ico.jpg">&nbsp;POSS后台管理系统</div>
</div>
<div title="菜单" data-options="region:'west',iconCls:'fa fa-list'" style="width: 200px">
    <div class="easyui-accordion" data-options="fit:true,border:false">
  <#list menus as menu>
	  <#if !menu.parent??>
        <div title="${menu.resName}" data-options="iconCls:'fa fa-cogs'">
            <ul class="crm-menu">
			  <#list menus as child>
				  <#if child.parent?? && child.parent.id == menu.id>
                    <li data-url="${child.menuUrl}">${child.resName}</li>
                  </#if>
              </#list>
            </ul>
        </div>
      </#if>
  </#list>
    </div>
</div>
<div id="mm" class="easyui-menu" style="width: 120px;">
    <div name="closeCurrent">关闭</div>
    <div name="closeAll">关闭全部</div>
    <div name="closeOther">关闭其他</div>
    <div class="menu-sep"></div>
    <div name="closeRight">关闭右侧</div>
    <div name="closeLeft">关闭左侧</div>
</div>
<div class="easyui-layout" data-options="region:'center'">
    <div id="index_tabs" fit="true" class="easyui-tabs" data-options="region:'center'">
        <div id="xtsy" title="系统首页" iconCls="fa fa-home" data-options="href:'/desktop'"></div>
    </div>
</div>
<div id="footer" data-options="region:'south'" style="height:20px;text-align: center;line-height: 20px;overflow: hidden;">
    <div id="online" class="online">
        当前在线人数：<span v-text="online"></span>
    </div>
    copyright © 2015-2018 管理系统</a>

    <div id="online_list" class="online-list">
        <div class="online-list-header">
            <i class="fa fa-close"></i>
            <span>系统在线用户</span>
        </div>
        <div class="online-list-users">
            <ul>
                <li v-for="(user,i) in onlineUser" :id="'user'+user.uid" :key="user" :index="i" @click="sendMsg(user,${s_member.id})">
          <span class="online-user-avator">
            <i class="fa fa-user"></i>
          </span>
                    <span v-text="user.realName"></span>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
