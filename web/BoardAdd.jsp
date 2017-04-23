<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/BoardTop.jsp"></jsp:include>

<form method="post" action="/board" class="form-inline">
    <h1 align="center">write board</h1>
    <p>
        title
    </p>
    <input type="text" name="title" class="form-control" value="${board.title}">
    <p>
        writer
    </p>
    <input type="text" name="writer" class="form-control" value="${board.writer}">
    <p>
        boardId
    </p>
    <select name="boardId" class="form-control" size="1" value="${board.boardId}">
        <option value="freeBoard"> freeBoard</option>
        <option value="noticeBoard">noticeBoard</option>
        <option value="referenceBoard">referenceBoard</option>
    </select>
    <p>
        content
    </p>
    <textarea name="content" class="form-control" rows="3">${board.content}</textarea>

    <input type="hidden" name="cmd" value="save">
    <input type="hidden" name="no" value="${board.no}">

    <c:choose>
        <c:when test="${board ne null}">
            <input type="submit" value="수정하기">
        </c:when>
        <c:otherwise>
            <input type="submit" value="글쓰기">
        </c:otherwise>
    </c:choose>
</form>

<a href="/board">return</a>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>