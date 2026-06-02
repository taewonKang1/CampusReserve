<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">ADMIN</p>
        <h1>관리자 대시보드</h1>
        <p class="lede">예약 현황, 취소 요청, 매출을 확인합니다.</p>
    </div>
</section>

<div class="grid grid-4">
    <article class="stat">
        <span class="muted">전체 예약</span>
        <strong>${dashboard.totalReservations}</strong>
    </article>
    <article class="stat">
        <span class="muted">취소 요청</span>
        <strong>${dashboard.pendingCancelCount}</strong>
    </article>
    <article class="stat">
        <span class="muted">운영 자원</span>
        <strong>${dashboard.activeResourceCount}</strong>
    </article>
    <article class="stat">
        <span class="muted">결제 합계</span>
        <strong><fmt:formatNumber value="${dashboard.totalSales}" />원</strong>
    </article>
</div>

<div class="grid grid-2" style="margin-top:16px">
    <a class="panel" href="${ctx}/admin/layout.do">
        <h2>자원 배치 현황</h2>
        <p class="muted">날짜별 예약 가능 시간과 사용률을 확인합니다.</p>
    </a>
    <a class="panel" href="${ctx}/admin/cancel-requests.do">
        <h2>취소 요청 관리</h2>
        <p class="muted">회원의 취소 요청을 승인하거나 거절합니다.</p>
    </a>
    <a class="panel" href="${ctx}/admin/reservations.do">
        <h2>전체 예약</h2>
        <p class="muted">모든 회원의 예약과 결제 상태를 조회합니다.</p>
    </a>
    <a class="panel" href="${ctx}/admin/sales.do">
        <h2>매출 통계</h2>
        <p class="muted">일자별, 자원별 결제 금액을 집계합니다.</p>
    </a>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
