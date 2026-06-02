<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">COMPLETE</p>
        <h1>예약이 완료되었습니다</h1>
        <p class="lede">예약 번호와 결제 정보를 확인하세요.</p>
    </div>
    <a class="button" href="${ctx}/mypage/reservations.do">내 예약</a>
</section>

<section class="panel">
    <div class="grid grid-2">
        <div>
            <h2>${reservation.resourceName}</h2>
            <p class="muted">${reservation.periodLabel}</p>
            <div class="meta-row">
                <span class="pill">예약 번호 ${reservation.reservationId}</span>
                <span class="pill">${reservation.statusLabel}</span>
            </div>
        </div>
        <div>
            <h2><fmt:formatNumber value="${reservation.totalPrice}" />원</h2>
            <p class="muted">${reservation.paymentMethod} · ${reservation.paymentStatus}</p>
        </div>
    </div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
