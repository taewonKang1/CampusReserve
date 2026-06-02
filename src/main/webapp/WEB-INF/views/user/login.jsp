<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="auth-wrap">
    <div class="auth-panel">
        <p class="eyebrow">LOGIN</p>
        <h1>로그인</h1>
        <c:if test="${param.registered == '1'}">
            <div class="notice">회원가입이 완료되었습니다. 로그인하세요.</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <form class="form-grid" method="post" action="${ctx}/user/login.do">
            <input type="hidden" name="_csrf" value="${csrfToken}">
            <div class="field">
                <label for="loginId">아이디</label>
                <input id="loginId" name="loginId" value="${loginId}" required autocomplete="username">
            </div>
            <div class="field">
                <label for="password">비밀번호</label>
                <input id="password" name="password" type="password" required autocomplete="current-password">
            </div>
            <button class="button full" type="submit">로그인</button>
            <a class="button secondary full" href="${ctx}/user/register.do">회원가입</a>
        </form>
    </div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
