
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Board</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<script>
    function goBoard(boardId) {
        location.href="/board?boardId="+boardId;
    }
</script>
<body>

    <table>
        <tr>
            <td><a href="/board" class="btn btn-primary">All board</a><br></td>
            <td><a href="javascript:goBoard('freeBoard')" class="btn btn-primary">Free board</a><br></td>
            <td><a href="javascript:goBoard('noticeBoard')" class="btn btn-primary">Notice board</a><br></td>
            <td><a href="javascript:goBoard('referenceBoard')" class="btn btn-primary">Reference board</a><br></td>
        </tr>
    </table>

<h1>Board</h1>
