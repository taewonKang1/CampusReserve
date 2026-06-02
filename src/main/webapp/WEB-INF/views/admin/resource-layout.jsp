<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">LAYOUT</p>
        <h1>자원 배치 현황</h1>
        <p class="lede">선택한 날짜의 시간대별 예약 사용률입니다.</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/dashboard.do">대시보드</a>
</section>

<form class="toolbar" method="get" action="${ctx}/admin/layout.do">
    <div class="field">
        <label for="date">조회일</label>
        <input id="date" name="date" type="date" value="${selectedDate}" min="${today}">
    </div>
    <button class="button secondary" type="submit">조회</button>
</form>

<div class="grid grid-2">
    <c:forEach var="status" items="${statuses}">
        <article class="panel">
            <div class="toolbar">
                <div>
                    <h2>${status.resourceName}</h2>
                    <p class="muted">${status.typeLabel} · ${status.location}</p>
                </div>
                <span class="pill">${status.usageRate}% 사용</span>
            </div>
            <div class="progress" aria-hidden="true"><span style="width:${status.usageRate}%"></span></div>
            <div class="meta-row">
                <span class="pill">가능 ${status.availableCount}</span>
                <span class="pill">예약 ${status.reservedCount}</span>
                <span class="pill">전체 ${status.totalCount}</span>
            </div>
        </article>
    </c:forEach>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
