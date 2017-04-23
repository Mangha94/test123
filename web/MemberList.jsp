<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/top.jsp"></jsp:include>
<script>
    function deletev(memberId) {
        if (confirm("삭제하겠습니까?")) {
            location.href = "/member?cmd=delete&memberId=" + memberId;
        }
    }
    function editv(memberNum) {
        location.href = "/member?memberNum=" + memberNum;
    }
</script>
<br>
<table align="center" class="table table-bordered table-striped">
    <thead>
    <tr>
        <td>num</td>
        <td>name</td>
        <td>memberId</td>
        <td>phonnumber</td>
        <td>birthday</td>
        <td>regDate</td>
        <td>관리</td>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${members}" var="m">

        <tr>
            <td>${m.memberNum}</td>
            <td>${m.name}</td>
            <td>${m.memberId}</td>
            <td>${m.phonnumber}</td>
            <td>${m.birthday}</td>
            <td>${m.regDate}</td>
            <td><a href="javascript:editv('${m.memberNum}')" class="btn btn-warning">edit</a>
                <a href="javascript:deletev('${m.memberId}')" class="btn btn-danger">delete</a></td>
        </tr>
    </c:forEach>

    </tbody>

</table>
<a href="index.jsp" class="btn btn-info">main</a>
<a href="MemberAdd.jsp" class="btn btn-primary">add member</a>
<input type="hidden" name="memberNum" value="${member.memberNum}">
<c:choose>
    <c:when test="${member ne null}">
        <form method="post" action="/member?memberNum=${member.memberNum}">
            <b>Name</b>
            <input type="text" class="form-control" placeholder="Text input" name="name"
                   value="${member.name}">
            <b>MemberId</b>
            <input type="text" class="form-control" placeholder="Text input" name="memberId"
                   value="${member.memberId}">
            <b>pw</b>
            <input type="text" class="form-control" placeholder="Text input" name="pw"
                   value="${member.pw}">
            <b>PhonNumber</b>
            <input type="text" class="form-control" placeholder="Text input" name="phonnumber"
                   value="${member.phonnumber}">
            <b>Birthday</b>
            <input type="text" class="form-control" placeholder="Text input" name="birthday"
                   value="${member.birthday}">
            <button class="btn btn-success">edit</button>
        </form>
    </c:when>
    <c:otherwise>
        <form name="searchForm" method="get" action="/member">
            <b>Name</b>
            <input type="text" class="form-control" placeholder="Text input" name="findName"
                   value="<c:out value="${findName}"/>">
            <b>MemberId</b>
            <input type="text" class="form-control" placeholder="Text input" name="findMemberId"
                   value="<c:out value="${findMemberId}"/>">
            <b>PhonNumber</b>
            <input type="text" class="form-control" placeholder="Text input" name="findPhonnumber"
                   value="<c:out value="${findPhonnumber}"/>">
            <b>Birthday</b>
            <input type="text" class="form-control" placeholder="Text input" name="findBirthday"
                   value="<c:out value="${findBirthday}"/>">
            <button class="btn btn-success">search</button>
        </form>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>