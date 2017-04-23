<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/BoardTop.jsp"></jsp:include>
<script>
    function deletev(no){
        if(confirm("삭제하겠습니까?")){
        location.href="/board?cmd=delete&no="+no;
        }
    }
</script>

<table align = "center" class="table table-bordered table-striped">
    <thead>
    <tr>
        <td>no</td>
        <td>title</td>
        <td>writer</td>
        <td>regDate</td>
        <td>boardId</td>
        <td>hit</td>
        <td>delete</td>
    </tr>
    </thead>

    <tbody>

    <c:forEach items="${boards}" var="b">

        <tr>
            <td>${b.no}</td>
            <td><a href="/board?cmd=view&no=${b.no}">${b.title}</a></td>
            <td>${b.writer}</td>
            <td>${b.regDate}</td>
            <td>${b.boardId}</td>
            <td>${b.hit}</td>
            <td><a href="/board?cmd=save&no=${b.no}" class="btn btn-warning">edit</a>
                <a href="javascript:deletev('${b.no}')" class="btn btn-danger">delete</a></td>

        </tr>
    </c:forEach>
    </tbody>
</table>
    <a href="/board?cmd=save" class="btn btn-primary">boardAdd</a><br>

    <form name="searchForm" method="get" action="/board">

        <input type="hidden" name="boardId" value="${boardId}">
        <select name="classification" class="form-control" size="1">
            <option value="title" <c:if test="${classification eq 'title'}">selected</c:if>> title</option>
            <option value="writer" <c:if test="${classification eq 'writer'}">selected</c:if>>writer</option>
        </select>
        <input type="text" class="form-control" placeholder="Text input" name="keyword" value="<c:out value="${keyword}"/>">
        <button class="btn btn-success">search</button>
    </form>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>