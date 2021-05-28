<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/plugins/zTreeStyle/zTreeStyle.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js?v=2.1.4"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/ztree/jquery.ztree.core.js"></script>
    <script src="${pageContext.request.contextPath}/js/plugins/ztree/jquery.ztree.excheck.js"></script>
    <script>
        var setting = {
            view: {
                showLine: false
            },
            async: {
                enable:true,
                url:"${pageContext.request.contextPath}/data.json",
            },
            check: {
                enable: true
            }
        };
        var znodes=[
            {
                "name" :"OA办公系统",
                "icon":"${pageContext.request.contextPath}/img/001.jpg",
                "open":false,
                "children":[
                    {"name":"客户管理"},
                    {
                        "name":"项目管理",
                        "children":[
                            {"name":"添加项目"},
                            {"name":"需求分析"},
                            {"name":"模块管理"},
                            {"name":"附件管理"}
                        ]
                    },
                    {
                        "name":"日常办公",
                        "children":[
                            {"name":"分配任务"},
                            {"name":"我的任务"},
                            {"name":"通知公告"}
                        ]
                    }
                ]
            }
        ];
        $(document).ready(function(){
            $.fn.zTree.init($("#treeDemo"),setting);



        });
    </script>
</head>
<body>
      <ul id="treeDemo" class="ztree"></ul>
</body>
</html>
