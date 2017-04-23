<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/top.jsp"></jsp:include>

<form method="post" action ="/member" class="form-inline">
    <h1 align="center">Input Members</h1>
    memberId <input type ="text" name="memberId" class="form-control" ><br>
    name <input type ="text" name="name" class="form-control"><br>
    phonnumber <input type ="text" name="phonnumber" class="form-control"><br>
    birthday <input type ="text" name="birthday" class="form-control"><br>
    pw<input type = "text" name="pw" class="form-control"><br>

    <input type="submit" value="전송" class="btn btn-primary"><br>

</form>
<a href="/member">return</a>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>