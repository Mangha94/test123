<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/top.jsp"></jsp:include>

        <form method="post" action ="/book" class="form-inline">
            <h1 align="center">Input Books</h1>
                title <input type ="text" name="title" class="form-control" ><br>
                writer <input type ="text" name="writer" class="form-control"><br>
                publisher <input type ="text" name="publisher" class="form-control"><br>
                price <input type ="text" name="price" class="form-control"><br>
                classification<select name="classification" class="form-control" size="1">
                    <option value="fairytale"> fairytale</option>
                    <option value="novel"> novel</option>
                    <option value="professional books"> professional books</option>
                </select><br>

            <input type="submit" value="전송" class="btn btn-primary"><br>

        </form>
    <a href="/book">return</a>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>
