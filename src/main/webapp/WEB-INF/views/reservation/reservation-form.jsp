<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">${resource.typeLabel}</p>
        <h1>${resource.name}</h1>
        <p class="lede">${resource.location} · <fmt:formatNumber value="${resource.pricePerSlot}" />원/시간</p>
    </div>
    <c:choose>
        <c:when test="${resource.resourceType == 'LOCKER'}">
            <a class="button secondary" href="${ctx}/resources/lockers.do">목록으로</a>
        </c:when>
        <c:otherwise>
            <a class="button secondary" href="${ctx}/resources/study-rooms.do">목록으로</a>
        </c:otherwise>
    </c:choose>
</section>

<form class="toolbar" method="get" action="${ctx}/reservation/form.do">
    <input type="hidden" name="resourceId" value="${resource.resourceId}">
    <div class="field">
        <label for="date">예약일</label>
        <input id="date" name="date" type="date" value="${selectedDate}" min="${today}">
    </div>
    <button class="button secondary" type="submit">시간 조회</button>
</form>

<section class="panel">
    <h2>예약 가능 시간</h2>
    <div class="slots">
        <c:forEach var="slot" items="${slots}">
            <c:choose>
                <c:when test="${slot.status == 'AVAILABLE'}">
                    <form class="slot" method="post" action="${ctx}/reservation/create.do">
                        <input type="hidden" name="_csrf" value="${csrfToken}">
                        <input type="hidden" name="slotId" value="${slot.slotId}">
                        <input type="hidden" name="paymentMethod" value="MOCK_CARD">
                        <strong>${slot.timeLabel}</strong>
                        <p class="muted">Mock 카드 결제</p>
                        <button class="button" type="submit">예약하기</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="slot disabled">
                        <strong>${slot.timeLabel}</strong>
                        <p class="muted">예약 완료</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${empty slots}">
            <p class="muted">선택한 날짜에 생성된 시간대가 없습니다.</p>
        </c:if>
    </div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
