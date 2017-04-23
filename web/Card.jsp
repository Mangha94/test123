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
			location.href = "/card?cmd=delete&cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo + "&commentNo=" + commentNo;
		}
	}

	function modifyCategoryv(cardCategoryNo) {
		location.href = "/card?cmd=getNo&cardCategoryNo=" + cardCategoryNo;
	}

	function modifyCommentv(cardCategoryNo, contentNo, commentNo) {
		location.href = "/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo + "&commentNo=" + commentNo;
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

	function showAddCategory (categoryNo)
	{
		$("#registForm_" + categoryNo).toggle(500);
	}

	function onSubmitCategory (myForm)
	{
		var flag = true;

		if (myForm.cardCategoryTitle.value.trim() == "")
		{
			$("#errorAlert").text ("카테고리명을 입력해주세요").show (300).delay (800).hide (400);


			flag = false;

			myForm.cardCategoryTitle.focus ();
		}

		// 체크 실패! Submit 이 일어나면 안된다.
		if (flag == false)
		{
			if (event.preventDefault)
				event.preventDefault();
			else
				event.returnValue = false; // for IE as dont support preventDefault;
		}

		return flag;
	}
</script>


<div class="alert alert-danger" role="alert" id = "errorAlert" style="display:none; position: absolute; top:10px; left:10px;width:500px;"></div>


<c:forEach items="${categoryList}" var="category">

	<div class="row">

		<div class="col-xs-12 col-md-12">

			<div class="panel panel-success">
				<div class="panel-heading">

					<a href="/card?cardCategoryNo=${category.cardCategoryNo}">${category.cardCategoryTitle}</a>

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
				<div class="panel-body">
					<div class="list-group">

						<c:forEach items="${contentList}" var="content">

							<c:if test="${category.cardCategoryNo eq content.cardCategoryNo}">

								<a href="/card?cardCategoryNo=${category.cardCategoryNo}&contentNo=${content.contentNo}"
								   class="list-group-item">${content.title}</a>
								<c:if test="${contentNo eq content.contentNo}">
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
									      action="/card?cardCategoryNo=${content.cardCategoryNo}&contentNo=${content.contentNo}"
									      class="form-inline">
										<p>Writer</p>
										<input type="text" name="commentWriter" class="form-control"
										       value="${getComment.commentWriter}">
										<p>comment</p>
										<input type="text" name="comment" class="form-control"
										       value="${getComment.comment}">
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

									<uI class="list-group">
										<c:forEach items="${commentList}" var="cml">
											<li class="list-group-item">${cml.commentWriter} ${cml.comment} ${cml.commentRegDate}
												<a href="javascript: modifyCommentv('${category.cardCategoryNo}','${content.contentNo}','${cml.commentNo}')"
												   class="btn btn-warning">edit</a>
												<a href="javascript: deleteCommentv('${category.cardCategoryNo}','${content.contentNo}','${cml.commentNo}')"
												   class="btn btn-danger">delete</a>
											</li>
										</c:forEach>
									</uI>

								</c:if>


							</c:if>
						</c:forEach>

					</div>
				</div>
				<div class="panel-body" id = "registForm_${category.cardCategoryNo}" style="display: none;">
					<form method="post"
					      action="/card?cardCategoryNo=${category.cardCategoryNo}"
					      class="form-inline">
						<input type="text" class="form-control" name="title" placeholder="TITLE"/>
						<textarea name="content" class="form-control">내용을 입력하세요</textarea>
						<input type="hidden" name="cmd" value="addContent">
						<input type="submit" value="등록하기">
					</form>
				</div>
			</div>

		</div>
	</div>
</c:forEach>


<form method="post" action="/card" class="form-inline" name="categoryForm" onsubmit="onSubmitCategory(this)">
	<c:choose>
		<c:when test="${getCardCategory ne null}">
			<input type="text" class="form-control" name="cardCategoryTitle"
			       placeholder="cardCategoryTitle" value="${getCardCategory.cardCategoryTitle}">
			<input type="hidden" name="cardCategoryNo" value="${getCardCategory.cardCategoryNo}">
			<input type="hidden" name="cmd" value="modify">
			<input type="submit" value="수정하기">
		</c:when>
		<c:otherwise>
			<input type="text" class="form-control" name="cardCategoryTitle"
			       placeholder="카테고리명" >
			<input type="hidden" name="cmd" value="addCardCategory">
			<input type="submit" value="등록하기">
			<%--<a href = "javascript:submitCategory()" class = "btn btn-primary">등록하기</a>--%>
		</c:otherwise>
	</c:choose>
</form>

<jsp:include page="/WEB-INF/bottom.jsp"></jsp:include>