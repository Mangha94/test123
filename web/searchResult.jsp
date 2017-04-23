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
</script>

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

    <c:forEach items="${findByTitle}" var="t">

        <tr>
            <td>${t.id}</td>
            <td>${t.title}</td>
            <td>${t.writer}</td>
            <td>${t.publisher}</td>
            <td><fmt:formatNumber value = "${t.price}" /></td>
            <td>${t.rented}</td>
            <td><a href="javascript:deletev('${t.id}')" class="btn btn-danger">delete</a></td>
            <td><a href="javascript:borrowv('${t.id}')" class="btn btn-primary">borrow</a></td>
            <td><a href="javascript:returnv('${t.id}')" class="btn btn-success">return</a></td>
        </tr>
    </c:forEach>
    </tbody>

</table>
<a href="index.jsp" class="btn btn-info">main</a>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>
