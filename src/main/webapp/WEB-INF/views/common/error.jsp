<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">ERROR</p>
        <h1>요청을 처리하지 못했습니다</h1>
        <p class="lede">${empty errorMessage ? '알 수 없는 오류가 발생했습니다.' : errorMessage}</p>
    </div>
    <a class="button secondary" href="${ctx}/index.do">메인으로</a>
</section>
<c:if test="${not empty errorDetail}">
    <div class="panel">
        <p class="muted">오류 유형: ${errorDetail}</p>
    </div>
</c:if>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
