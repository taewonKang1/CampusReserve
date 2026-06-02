<%@ include file="/WEB-INF/views/common/header.jsp" %>
<section class="auth-wrap">
    <div class="auth-panel">
        <p class="eyebrow">JOIN</p>
        <h1>회원가입</h1>
        <c:if test="${not empty errorMessage}">
            <div class="error">${errorMessage}</div>
        </c:if>
        <form class="form-grid" method="post" action="${ctx}/user/register.do">
            <input type="hidden" name="_csrf" value="${csrfToken}">
            <div class="field">
                <label for="loginId">아이디</label>
                <input id="loginId" name="loginId" value="${loginId}" required autocomplete="username" minlength="4" maxlength="20">
            </div>
            <div class="field">
                <label for="password">비밀번호</label>
                <input id="password" name="password" type="password" required autocomplete="new-password" minlength="8" maxlength="72">
            </div>
            <div class="field">
                <label for="name">이름</label>
                <input id="name" name="name" value="${name}" required autocomplete="name">
            </div>
            <div class="field">
                <label for="phone">연락처</label>
                <input id="phone" name="phone" value="${phone}" autocomplete="tel">
            </div>
            <button class="button full" type="submit">가입하기</button>
            <a class="button secondary full" href="${ctx}/user/login.do">로그인으로 이동</a>
        </form>
    </div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
