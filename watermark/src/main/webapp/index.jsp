<%@ page language="java" pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
    <title>首页</title>
</head>
<body>
  <h2>hello world</h2>
</body>
<script src="static/js/watermark.js"></script>
<script type="text/javascript">
    window.onload=function(){
        watermark({ watermark_txt: "测试水印" });
    };
</script>
</html>
