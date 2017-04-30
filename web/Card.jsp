<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/WEB-INF/top.jsp"></jsp:include>
<script>
    function deleteCategoryv(cardCategoryNo) {
        if (confirm("삭제하시겠습니까?")) {
            location.href = "/card?cmd=delete&cardCategoryNo=" + cardCategoryNo;
        }
    }

    function deleteContentv(cardCategoryNo, contentNo) {
        if (confirm("삭제하시겠습니까?")) {
            location.href = "/card?cmd=delete&cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo;
        }
    }

    function deleteCommentv(cardCategoryNo, contentNo, commentNo) {
        if (confirm("삭제하시겠습니까?")) {
            //location.href = "/card?cmd=delete&cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo + "&commentNo=" + commentNo;
            $.ajax({
                type:"GET",
                url:"/card?cmd=delete&cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo + "&commentNo=" + commentNo,
                dataType:"html",
                success:function(data,textStatus)
                {
                    result=data;
                    console.log(data);
                    //alert("등록되었습니다");
                    //location.href="/card";
                    //result = eval(data.trim());
                    console.log(result);
                    if(result.cmd=="OK"){
                        alert("삭제되었습니다");
                    }else
                        alert("실패하였습니다");


                    reloadCommentList(contentNo);
                }

            })
        }
    }

    function modifyCategoryv(cardCategoryNo) {
        location.href = "/card?cmd=getNo&cardCategoryNo=" + cardCategoryNo;
    }

    function modifyCommentv(cardCategoryNo, contentNo, commentNo) {
        location.href = "/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo + "&commentNo=" + commentNo;
    }

    function showLayer() {
        $("#testLayer").load("/hello.jsp", function () {
            $("#testLayer").show();
        });
    }

    /*function submitCategory ()
     {
     var flag = true;
     var myForm = document.categoryForm;

     if (myForm.cardCategoryTitle.value.trim() == "")
     {
     alert ("카테고리명을 입력해주세요");
     myForm.cardCategoryTitle.focus ();

     return;
     }

     myForm.submit();
     }*/

    function showAddCategory(categoryNo) {
        $("#registForm_" + categoryNo).toggle(500);
    }

    function onSubmitCategory(myForm) {
        var flag = true;

        if (myForm.cardCategoryTitle.value.trim() == "") {
            $("#errorAlert").text("카테고리명을 입력해주세요").show(300).delay(800).hide(400);


            flag = false;

            myForm.cardCategoryTitle.focus();
        }

        // 체크 실패! Submit 이 일어나면 안된다.
        if (flag == false) {
            if (event.preventDefault)
                event.preventDefault();
            else
                event.returnValue = false; // for IE as dont support preventDefault;
        }

        return flag;
    }

    function showContentTitle(categoryNo) {
        $("#contentForm_" + categoryNo).toggle(500);
    }
    function hi() {
        $("#testLayer").append("<div class='well'>광고 광고 광고</div>");
    }
    function onSubmitComment(myForm) {
        var flag = true;

        if (myForm.commentWriter.value.trim() == ""&&myForm.comment.value.trim() == "")
        {
            $("#errorAlert").text ("아무것도 쓰지 않았습니다").show (300).delay (800).hide (400);


            flag = false;

            myForm.commentWriter.focus ();
        }else if (myForm.commentWriter.value.trim() == "") {
            $("#errorAlert").text("작성자를 입력해주세요").show(300).delay(800).hide(400);


            flag = false;

            myForm.commentWriter.focus();
        }else if (myForm.comment.value.trim() == "") {
            $("#errorAlert").text("댓글을 입력해주세요").show(300).delay(800).hide(400);


            flag = false;

            myForm.comment.focus();
        }

        $.ajax({
            type:"POST",
            url:myForm.action,
            dataType:"html",
           // data:{cmd:"test",contentNo:123},
            data:$(myForm).serialize(),
            success:function(data,textStatus)
            {
                //alert("등록되었습니다");

                var contentNo=$(myForm).children("[name='contentNo']").val();
                reloadCommentList(contentNo);
            }

        });

        // 체크 실패! Submit 이 일어나면 안된다.
        //if (flag == false) {
        if(true){
            if (event.preventDefault)
                event.preventDefault();
            else
                event.returnValue = false; // for IE as dont support preventDefault;
        }

        return flag;
    }
    function reloadCommentList(contentNo) {
        $("#commentForm_" + contentNo).load("/card?cmd=commentList&contentNo=" + contentNo);
    }
</script>

<%--<div id = "testLayer" style="display:none; position: absolute; top:10px; left:10px;width:500px;">--%>

<%--</div>--%>

<div class="alert alert-danger" role="alert" id="errorAlert"
     style="display:none; position: absolute; top:10px; left:10px;width:500px;"></div>

<div id="testLayer">
    안녕하세요
</div>

<a href="javascript:hi()" class="btn btn-default">hi</a>

<c:forEach items="${categoryList}" var="category">

    <div class="row">

        <div class="col-xs-12 col-md-12">

            <div class="panel panel-success">
                <div class="panel-heading">

                    <a href="javascript:showContentTitle('${category.cardCategoryNo}')">${category.cardCategoryTitle}</a>

                    <a href="javascript:showAddCategory('${category.cardCategoryNo}')"
                       class="btn btn-primary"><span class="glyphicon glyphicon-plus"
                                                     aria-hidden="true"></span> 추가</a>

                    <a href="javascript:modifyCategoryv(${category.cardCategoryNo})"
                       class="btn btn-warning"><span class="glyphicon glyphicon-edit"
                                                     aria-hidden="true"></span> 수정</a>

                    <a href="javascript:deleteCategoryv(${category.cardCategoryNo})"
                       class="btn btn-danger"><span class="glyphicon glyphicon-trash"
                                                    aria-hidden="true"></span> 삭제</a>
                </div>

                <div class="panel-body" id="contentForm_${category.cardCategoryNo}" style="display: none;">
                    <div class="list-group">
                        <c:forEach items="${contentMap[category.cardCategoryNo]}" var="content">

                            <div class="list-group-item active">${content.title}</div>

                            <form method="post"
                                  action="/card?cardCategoryNo=${content.cardCategoryNo}&contentNo=${content.contentNo}">
										<textarea name="content"
                                                  class="form-control">${content.content}</textarea><br>
                                <input type="hidden" name="contentNo"
                                       value="${content.contentNo}">
                                <input type="hidden" name="cmd" value="modify">
                                <input type="submit" class="btn btn-default" value="수정하기">
                                <a href="javascript:deleteContentv('${category.cardCategoryNo}','${content.contentNo}')"
                                   class="btn btn-danger">삭제</a>
                            </form>
                            <form method="post"
                                  action="/card?cardCategoryNo=${content.cardCategoryNo}"
                                  class="form-inline" onsubmit="onSubmitComment(this)" >
                                <input type="hidden" name="contentNo" value="${content.contentNo}">

                                <div class="form-group">
                                    <label for="commentWriter">writer</label>
                                    <input type="text" class="form-control" id="commentWriter" name="commentWriter"
                                           value="${getComment.commentWriter}" placeholder="writer">
                                </div>
                                <div class="form-group">
                                    <label for="comment">comment</label>
                                    <input type="text" class="form-control" id="comment" name="comment"
                                           value="${getComment.comment}" placeholder="comment">
                                </div>
                                <c:choose>
                                    <c:when test="${getComment eq null}">
                                        <input type="hidden" name="contentNo"
                                               value="${content.contentNo}">
                                        <input type="hidden" name="cmd" value="addComment">
                                        <input type="submit" class="btn btn-default"
                                               value="댓글쓰기">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="commentNo"
                                               value="${getComment.commentNo}">
                                        <input type="hidden" name="cmd" value="modify">
                                        <input type="submit" class="btn btn-default"
                                               value="댓글수정">
                                    </c:otherwise>
                                </c:choose>
                            </form>

                            <uI class="list-group" id="commentForm_${content.contentNo}">
                                <c:forEach items="${commentMap[content.contentNo]}" var="cml">
                                    <li class="list-group-item">${cml.commentWriter} ${cml.comment} ${cml.commentRegDate}
                                        <a href="javascript: modifyCommentv('${category.cardCategoryNo}','${content.contentNo}','${cml.commentNo}')"
                                           class="btn btn-warning">edit</a>
                                        <a href="javascript: deleteCommentv('${category.cardCategoryNo}','${content.contentNo}','${cml.commentNo}')"
                                           class="btn btn-danger">delete</a>
                                    </li>
                                </c:forEach>
                            </uI>


                        </c:forEach>

                    </div>
                </div>
                <div class="panel-body" id="registForm_${category.cardCategoryNo}" style="display: none;">
                    <div class="list-group">
                        <form method="post"
                              action="/card?cardCategoryNo=${category.cardCategoryNo}">
                            <h2>글 새로 추가하기</h2>
                            <input type="text" class="form-control" name="title" placeholder="TITLE"/><br>
                            <textarea name="content" class="form-control" placeholder="내용을 입력하세요"></textarea><br>
                            <input type="hidden" name="cmd" value="addContent">
                            <input type="submit" value="등록하기">
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</c:forEach>


<form method="post" action="/card" class="form-inline" name="categoryForm" onsubmit="onSubmitCategory(this)">
    <input type="text" class="form-control" name="cardCategoryTitle"
           placeholder="cardCategoryTitle" value="${getCardCategory.cardCategoryTitle}">
    <c:choose>
        <c:when test="${getCardCategory eq null}">
            <input type="hidden" name="cmd" value="addCardCategory">
            <input type="submit" value="등록하기">
        </c:when>
        <c:otherwise>
            <input type="hidden" name="cardCategoryNo" value="${getCardCategory.cardCategoryNo}">
            <input type="hidden" name="cmd" value="modify">
            <input type="submit" value="수정하기">

            <%--<a href = "javascript:submitCategory()" class = "btn btn-primary">등록하기</a>--%>
            <a href="javascript:showLayer()" class="btn btn-primary">테스트</a>
        </c:otherwise>
    </c:choose>
</form>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>