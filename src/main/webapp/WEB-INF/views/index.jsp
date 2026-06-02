<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">RESERVE</p>
        <h1>스터디룸과 사물함을 바로 예약하세요</h1>
        <p class="lede">날짜와 시간대를 고르면 예약과 Mock 결제가 한 번에 처리됩니다.</p>
    </div>
    <c:choose>
        <c:when test="${empty sessionScope.userId}">
            <a class="button" href="${ctx}/user/login.do">로그인</a>
        </c:when>
        <c:otherwise>
            <a class="button" href="${ctx}/mypage/reservations.do">내 예약 보기</a>
        </c:otherwise>
    </c:choose>
</section>

<div class="grid grid-3">
    <article class="panel">
        <h2>스터디룸</h2>
        <p class="muted">팀 프로젝트와 세미나에 맞는 공간을 시간 단위로 예약합니다.</p>
        <a class="button secondary" href="${ctx}/resources/study-rooms.do">목록 보기</a>
    </article>
    <article class="panel">
        <h2>사물함</h2>
        <p class="muted">도서관과 공학관 사물함의 이용 가능 시간을 확인합니다.</p>
        <a class="button secondary" href="${ctx}/resources/lockers.do">목록 보기</a>
    </article>
    <article class="panel">
        <h2>관리자</h2>
        <p class="muted">예약 현황, 취소 요청, 매출 통계를 관리합니다.</p>
        <a class="button secondary" href="${ctx}/admin/dashboard.do">관리 화면</a>
    </article>
</div>

<c:if test="${empty sessionScope.userId}">
    <div class="panel" style="margin-top:16px">
        <p class="muted">시연 계정: 일반 회원 student / student1234!, 관리자 admin / admin1234!</p>
    </div>
</c:if>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
