<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setBundle basename = "messages"/>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link media="all" href="/library/skin/tool_base.css" rel="stylesheet" type="text/css" />
    <link media="all" href="/library/skin/default/tool.css" rel="stylesheet" type="text/css" />
    <link media="all" href="css/style.css" rel="stylesheet" type="text/css" />

    <script src="/library/js/headscripts.js" language="JavaScript" type="text/javascript"></script>

    <title><fmt:message key="title" /></title>
</head>
<body onload="<%=request.getAttribute("sakai.html.body.onload")%>">
    <div class="portletBody">
        <c:choose>
            <c:when test="${ empty events }">
                <p class="empty-timetable"><fmt:message key="emptyTimetable" /></p>
            </c:when>
            <c:otherwise>
                <table class="eveoh-upcoming-activities" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                            <th class="header-activity"><fmt:message key="header_activity" /></th>
                            <th class="header-date"><fmt:message key="header_date" /></th>
                            <th class="header-time"><fmt:message key="header_time" /></th>
                            <th class="header-location"><fmt:message key="header_location" /></th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ events }" var="event">
                        <tr>
                            <td class="activity">
                                <c:out value="${ event.activityDescription }" />
                            </td>
                            <td class="date">
                                <fmt:formatDate pattern="dd-MM-yyyy" value="${ event.startDate }" />
                            </td>
                            <td class="time">
                                <fmt:formatDate pattern="HH:mm" value="${ event.startDate }" /> -
                                <fmt:formatDate pattern="HH:mm" value="${ event.endDate }" />
                            </td>
                            <td class="location">
                                <c:choose>
                                    <c:when test="${ empty event.locations }">
                                        <fmt:message key="unknownLocation" />
                                    </c:when>
                                    <c:when test="${ fn:length(event.locations) == 1}">
                                        <c:forEach items="${ event.locations }" var="location">
                                            <c:out value="${ location.name }" />
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <ul>
                                            <c:forEach items="${ event.locations }" var="location">
                                                <li><c:out value="${ location.name }" /></li>
                                            </c:forEach>
                                        </ul>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty applicationUri and not empty applicationTarget}">
            <div id="full-schedule">
                <a href="<c:url value="${applicationUri}" />" target="${applicationTarget}">
                    <fmt:message key="fullSchedule_link_title" />
                </a>
            </div>
        </c:if>
    </div>
</body>
</html>
