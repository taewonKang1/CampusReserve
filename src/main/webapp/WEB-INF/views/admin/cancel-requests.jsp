<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">CANCEL</p>
        <h1>취소 요청 관리</h1>
        <p class="lede">승인 시 결제 상태는 환불 처리되고 시간대는 다시 예약 가능해집니다.</p>
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
            <th>처리</th>
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
                    <div class="inline-actions">
                        <form method="post" action="${ctx}/admin/cancel-approve.do">
                            <input type="hidden" name="_csrf" value="${csrfToken}">
                            <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                            <button class="button" type="submit">승인</button>
                        </form>
                        <form method="post" action="${ctx}/admin/cancel-reject.do">
                            <input type="hidden" name="_csrf" value="${csrfToken}">
                            <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                            <button class="button secondary" type="submit">거절</button>
                        </form>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty reservations}">
            <tr>
                <td colspan="6">대기 중인 취소 요청이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
