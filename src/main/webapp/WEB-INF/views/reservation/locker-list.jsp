<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">LOCKER</p>
        <h1>${title}</h1>
        <p class="lede">${subtitle}</p>
    </div>
</section>

<div class="grid resource-grid">
    <c:forEach var="resource" items="${resources}">
        <article class="resource-card">
            <div class="resource-visual locker" aria-hidden="true">
                <span></span><span></span><span></span><span></span><span></span><span></span><span></span>
                <span></span><span></span><span></span><span></span><span></span><span></span><span></span>
            </div>
            <div class="resource-body">
                <h2>${resource.name}</h2>
                <div class="resource-meta">
                    <span class="pill">${resource.location}</span>
                    <span class="pill">${resource.capacity}칸</span>
                    <span class="pill"><fmt:formatNumber value="${resource.pricePerSlot}" />원/시간</span>
                </div>
                <a class="button full" href="${ctx}/reservation/form.do?resourceId=${resource.resourceId}&date=${today}">시간 선택</a>
            </div>
        </article>
    </c:forEach>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
