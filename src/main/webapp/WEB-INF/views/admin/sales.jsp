<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="page-header">
    <div>
        <p class="eyebrow">SALES</p>
        <h1>매출 통계</h1>
        <p class="lede">Mock 결제 성공 건을 기준으로 집계합니다.</p>
    </div>
    <a class="button secondary" href="${ctx}/admin/dashboard.do">대시보드</a>
</section>

<div class="grid grid-2">
    <section class="panel">
        <h2>일자별 매출</h2>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>일자</th>
                    <th>건수</th>
                    <th>금액</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="stat" items="${dailySales}">
                    <tr>
                        <td>${stat.label}</td>
                        <td>${stat.reservationCount}</td>
                        <td><fmt:formatNumber value="${stat.amount}" />원</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty dailySales}">
                    <tr><td colspan="3">매출 데이터가 없습니다.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
    <section class="panel">
        <h2>자원별 매출</h2>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>자원</th>
                    <th>건수</th>
                    <th>금액</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="stat" items="${resourceSales}">
                    <tr>
                        <td>${stat.label}</td>
                        <td>${stat.reservationCount}</td>
                        <td><fmt:formatNumber value="${stat.amount}" />원</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty resourceSales}">
                    <tr><td colspan="3">매출 데이터가 없습니다.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
</div>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
