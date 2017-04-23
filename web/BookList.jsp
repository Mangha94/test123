<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/top.jsp"></jsp:include>
<script>
    function deletev(id) {
        if(confirm("삭제하겠습니까?")){
            location.href="/book?cmd=delete&id="+id;
        }
    }
    function borrowv(id){
        if(confirm("빌리시겠습니까??")){
            location.href="/book?cmd=borrow&id="+id;
        }
    }
    function returnv(id){
        if(confirm("반납하시겠습니다???")){
            location.href="/book?cmd=returnBook&id="+id;
        }
    }
    function searchByTitle(){
        title=document.searchForm.keyword.value;
            location.href="/book?cmd=searchByTitle&title="+title;
    }
</script>
<br>
<table align = "center" class="table table-bordered table-striped">
    <thead>
    <tr>
        <td>id</td>
        <td>title</td>
        <td>writer</td>
        <td>publisher</td>
        <td>price</td>
        <td>rented</td>
        <td>관리</td>
    </tr>
    </thead>

    <tbody>

        <c:forEach items="${books}" var="b">

        <tr>
            <td>${b.id}</td>
            <td>${b.title}</td>
            <td>${b.writer}</td>
            <td>${b.publisher}</td>
            <td><fmt:formatNumber value = "${b.price}" /></td>
            <td>${b.rented}</td>
            <td><a href="javascript:deletev('${b.id}')" class="btn btn-danger">delete</a></td>
            <td><a href="javascript:borrowv('${b.id}')" class="btn btn-primary">borrow</a></td>
            <td><a href="javascript:returnv('${b.id}')" class="btn btn-success">return</a></td>
        </tr>
        </c:forEach>
    </tbody>

</table>
    <a href="index.jsp" class="btn btn-info">main</a>
    <a href="BookAdd.jsp" class="btn btn-primary">add Book</a><br>

<form name="searchForm" method="get" action="/book">

<select name="classification" class="form-control" size="1">
<option value="id" <c:if test="${classification eq 'id'}">selected</c:if>> id</option>
<option value="title" <c:if test="${classification eq 'title'}">selected</c:if>> title</option>
<option value="writer" <c:if test="${classification eq 'writer'}">selected</c:if>>writer</option>
<option value="publisher" <c:if test="${classification eq 'publisher'}">selected</c:if>>publisher</option>
</select>
<input type="text" class="form-control" placeholder="Text input" name="keyword" value="<c:out value="${keyword}"/>">
    <button class="btn btn-success">search</button>
</form>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>