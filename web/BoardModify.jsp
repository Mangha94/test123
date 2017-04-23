<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/BoardTop.jsp"></jsp:include>

<form method="post" action ="/board?cmd=modify&no=${board.no}" class="form-inline">
    <h1 align="center">modify</h1>
    title <input type ="text" value="${board.title}" name="title" class="form-control" ><br>
    writer<input type ="text" value="${board.writer}" name="writer" class="form-control"><br>
    boardId<select name="boardId" class="form-control" size="1">
    <option value="free board"> freeBoard</option>
    <option value="notice board">noticeBoard</option>
    <option value="reference board">referenceBoard</option>
</select><br>
    content<textarea name="content" class="form-control" rows="3">${board.content}</textarea>
    <input type="submit" value="전송" class="btn btn-primary"><br>
</form>

<a href="/board">return</a>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>
