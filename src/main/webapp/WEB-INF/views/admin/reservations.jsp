<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">RESERVATIONS</p>
        <h1>전체 예약 조회</h1>
        <p class="lede">회원, 자원, 결제 상태를 통합 조회합니다.</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/dashboard.do">대시보드</a>
</section>

<div class="table-wrap">
    <table>
        <thead>
        <tr>
            <th>번호</th>
            <th>회원</th>
            <th>자원</th>
            <th>시간</th>
            <th>금액</th>
            <th>예약 상태</th>
            <th>결제</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td>${reservation.reservationId}</td>
                <td>${reservation.userName} (${reservation.loginId})</td>
                <td>${reservation.resourceName}</td>
                <td>${reservation.periodLabel}</td>
                <td><fmt:formatNumber value="${reservation.totalPrice}" />원</td>
                <td>
                    <span class="status ${reservation.cancelRequested ? 'pending' : ''} ${reservation.status == 'CANCELLED' ? 'cancelled' : ''}">
                        ${reservation.statusLabel}
                    </span>
                </td>
                <td>${reservation.paymentMethod} · ${reservation.paymentStatus}</td>
            </tr>
        </c:forEach>
        <c:if test="${empty reservations}">
            <tr>
                <td colspan="7">예약 내역이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
