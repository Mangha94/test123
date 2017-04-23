<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/BoardTop.jsp"></jsp:include>
<script>
    function deleteComment(commentNo,no) {
        if(confirm("삭제하시겠습니까?")){
            location.href="/board?cmd=deleteComment&commentNo="+commentNo+"&no="+no;
        }
    }
    function editv(commentNo,no){
        location.href="/board?cmd=view&no="+no+"&commentNo="+commentNo;
    }
</script>

${board}<br>

<table align = "center" class="table table-bordered table-striped">
    <thead>
    <tr>
        <td>commentRegDate</td>
        <td>writer</td>
        <td>comment</td>
        <td>관리</td>

    </tr>
    </thead>

    <tbody>

    <c:forEach items="${comments}" var="category">

        <tr>
            <td>${category.commentRegDate}</td>
            <td>${category.commentWriter}</td>
            <td>${category.comment}</td>
            <td><a href="javascript:editv('${category.commentNo}','${board.no}')" class="btn btn-warning">edit</a>
                <a href="javascript:deleteComment('${category.commentNo}','${board.no}')" class="btn btn-danger">delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form method="post" action="/board">
    <p>
        작성자
    </p>
    <input type="text" name="commentWriter" value='${boardComment.commentWriter}'>
    <p>
        댓글
    </p>
    <input type="text" name="comment" value='${boardComment.comment}'>

    <input type="hidden" name="cmd" value="writeComment">
    <input type="hidden" name="no" value="${board.no}">
    <input type="hidden" name="commentNo" value="${boardComment.commentNo}">


    <c:choose >
        <c:when test="${boardComment ne null}">
            <input type="submit" value="댓글수정하기">
        </c:when>
        <c:otherwise>
            <input type="submit" value="댓글쓰기">
        </c:otherwise>
    </c:choose>

</form>

<a href="/board">return</a><br>
<a href="/board?cmd=modify&no=${board.no}">modify</a><br>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>