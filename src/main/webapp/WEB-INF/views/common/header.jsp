<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CampusReserve</title>
    <link rel="stylesheet" href="${ctx}/assets/css/app.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar">
        <a class="brand" href="${ctx}/index.do">
            <span class="brand-mark">CR</span>
            <span>CampusReserve</span>
        </a>
        <nav class="nav">
            <a class="${currentPath == '/resources/study-rooms.do' ? 'active' : ''}" href="${ctx}/resources/study-rooms.do">스터디룸</a>
            <a class="${currentPath == '/resources/lockers.do' ? 'active' : ''}" href="${ctx}/resources/lockers.do">사물함</a>
            <c:choose>
                <c:when test="${not empty sessionScope.userId}">
                    <a class="${currentPath == '/mypage/reservations.do' ? 'active' : ''}" href="${ctx}/mypage/reservations.do">내 예약</a>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <a class="${fn:startsWith(currentPath, '/admin/') ? 'active' : ''}" href="${ctx}/admin/dashboard.do">관리자</a>
                    </c:if>
                    <span class="user-chip">${sessionScope.name}</span>
                    <a href="${ctx}/user/logout.do">로그아웃</a>
                </c:when>
                <c:otherwise>
                    <a class="${currentPath == '/user/login.do' ? 'active' : ''}" href="${ctx}/user/login.do">로그인</a>
                    <a class="${currentPath == '/user/register.do' ? 'active' : ''}" href="${ctx}/user/register.do">회원가입</a>
                </c:otherwise>
            </c:choose>
        </nav>
    </header>
    <main class="content">
