<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename="messages" />

<jsp:include page="header.jsp" />
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>">
    <div class="portletBody">
        <c:choose>
            <c:when test="${not empty exception.resourceBundleKey}">
                <fmt:message key="${exception.resourceBundleKey}" />
            </c:when>
            <c:otherwise>
                <c:out value="${exception.message}" />
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
