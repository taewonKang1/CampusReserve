<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">MY PAGE</p>
        <h1>내 예약 현황</h1>
        <p class="lede">예약 완료 건은 관리자 승인 절차를 통해 취소할 수 있습니다.</p>
    </div>
    <a class="button secondary" href="${ctx}/resources/study-rooms.do">새 예약</a>
</section>

<div class="table-wrap">
    <table>
        <thead>
        <tr>
            <th>예약번호</th>
            <th>자원</th>
            <th>시간</th>
            <th>금액</th>
            <th>상태</th>
            <th>요청</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="reservation" items="${reservations}">
            <tr>
                <td>${reservation.reservationId}</td>
                <td>${reservation.resourceName}</td>
                <td>${reservation.periodLabel}</td>
                <td><fmt:formatNumber value="${reservation.totalPrice}" />원</td>
                <td>
                    <span class="status ${reservation.cancelRequested ? 'pending' : ''} ${reservation.status == 'CANCELLED' ? 'cancelled' : ''}">
                        ${reservation.statusLabel}
                    </span>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${reservation.cancelable}">
                            <form method="post" action="${ctx}/mypage/cancel-request.do">
                                <input type="hidden" name="_csrf" value="${csrfToken}">
                                <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                                <button class="button warn" type="submit">취소 요청</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <span class="muted">처리 불가</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty reservations}">
            <tr>
                <td colspan="6">예약 내역이 없습니다.</td>
            </tr>
        </c:if>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
