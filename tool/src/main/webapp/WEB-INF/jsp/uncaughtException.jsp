<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>">
    <div class="portletBody">
        <c:out value="${exception.message}" />
    </div>
</body>
</html>
