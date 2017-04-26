<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:forEach items="${commentList}" var="cml">
    <li class="list-group-item">${cml.commentWriter} ${cml.comment} ${cml.commentRegDate}
        <a href="javascript: modifyCommentv('${cml.cardCategoryNo}','${cml.contentNo}','${cml.commentNo}')"
           class="btn btn-warning">edit</a>
        <a href="javascript: deleteCommentv('${cml.cardCategoryNo}','${cml.contentNo}','${cml.commentNo}')"
           class="btn btn-danger">delete</a>
    </li>
</c:forEach>
