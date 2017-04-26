package kr.co.ttinfo.bookManager.Servlet;

import kr.co.ttinfo.bookManager.book.model.board.Board;
import kr.co.ttinfo.bookManager.book.model.board.BoardCenter;
import kr.co.ttinfo.bookManager.book.model.board.BoardComment;
import kr.co.ttinfo.bookManager.book.model.board.BoardSearchData;
import kr.co.ttinfo.bookManager.book.model.book.BookCenter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/board")
public class BoardListServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        BoardCenter bc=new BoardCenter();

        String cmd=req.getParameter("cmd");
        if(cmd==null){
            cmd="";
        }
        if(cmd.equals("delete")) {
            deleteBoard(bc,req,resp);
            return;
        }else if("view".equals(cmd)){
            viewContent(req, resp, bc);
        }else if("deleteComment".equals(cmd)){
            deleteComment(req, resp, bc);
        }else if("save".equals(cmd)){
            String noStr=req.getParameter("no");
            if(noStr!=null){

                int no=Integer.parseInt(noStr);
                Board board=bc.getBoard(no);

                req.setAttribute("no",no);
                req.setAttribute("board",board);
            }
            RequestDispatcher view = req.getRequestDispatcher("BoardAdd.jsp");

            view.forward(req, resp);
        }
        else {
            boardList(req, resp, bc);
        }

    }

    private void deleteComment(HttpServletRequest req, HttpServletResponse resp, BoardCenter bc) throws IOException {
        String commentNostr=req.getParameter("commentNo");
        String nostr=req.getParameter("no");

        bc.deleteComment(Integer.parseInt(commentNostr));

        resp.sendRedirect("/board?cmd=view&no="+Integer.parseInt(nostr));
    }

    private void viewContent(HttpServletRequest req, HttpServletResponse resp, BoardCenter bc) throws ServletException, IOException {
        BoardComment boardComment=new BoardComment();
        String commentNoStr=req.getParameter("commentNo");

        if(commentNoStr!=null){
            int commentNo=Integer.parseInt(commentNoStr);
            boardComment=bc.getComment(commentNo);
            req.setAttribute("boardComment",boardComment);
        }
        String noStr=req.getParameter("no");
        int no=Integer.parseInt(noStr);

        Board board=bc.showContent(no);

        List<BoardComment> comments=bc.getComments(no);

        req.setAttribute("no",no);
        req.setAttribute("board",board);
        req.setAttribute("comments",comments);

        RequestDispatcher view = req.getRequestDispatcher("BoardView.jsp");

        view.forward(req, resp);
    }

    private void boardList(HttpServletRequest req, HttpServletResponse resp, BoardCenter bc) throws ServletException, IOException {

        BoardSearchData bsd=new BoardSearchData();

        String classification=req.getParameter("classification");
        String keyword=req.getParameter("keyword");
        String boardId=req.getParameter("boardId");

        /**
         * keyWoard에 맞는글을 찾아서 boards리스트에 넣는다
         */
        List<Board> boards = null;

        bsd.setBoardId(boardId);
        if("title".equals(classification)){
            bsd.setTitle(keyword);
        }else if("writer".equals(classification)){
            bsd.setWriter(keyword);
        }
        boards=bc.boardList(bsd);

        req.setAttribute("boards", boards);
        req.setAttribute("classification",classification);
        req.setAttribute("keyword",keyword);
        req.setAttribute("boardId",boardId);

        RequestDispatcher view = req.getRequestDispatcher("BoardMain.jsp");

        view.forward(req, resp);
    }

    /**
     * 게시물과 해당 댓글을 전부 지운다
     * @param bc
     * @param req
     * @param resp
     * @throws IOException
     */
    private void deleteBoard(BoardCenter bc,HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String no = req.getParameter("no");
        bc.remove(Integer.parseInt(no));
        bc.deleteAllComment(Integer.parseInt(no));
        //resp.sendRedirect("/board");

        req.setAttribute("message","삭제되었습니다");
        req.setAttribute("url","/board");

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/move.jsp");

        view.forward(req, resp);
        return;
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("board","boardList");

        BoardCenter bc=new BoardCenter();

        String cmd=req.getParameter("cmd");
        String noStr=req.getParameter("no");

        if("addBoard".equals(cmd)) {
            String title = req.getParameter("title");
            String writer = req.getParameter("writer");
            String boardId = req.getParameter("boardId");
            String content = req.getParameter("content");

            if(noStr==null) {
                Board board=new Board();
                board.setTitle(title);
                board.setWriter(writer);
                board.setBoardId(boardId);
                board.setContent(content);

                bc.addContent(board);
            }else{
                int no = Integer.parseInt(noStr);
                Board board = bc.getBoard(no);
                board.setTitle(title);
                board.setWriter(writer);
                board.setBoardId(boardId);
                board.setContent(content);

                bc.edit(board);
            }
        }

        /**
         * 댓글을 쓰게한다
         */
        else if("writeComment".equals(cmd)){
            String commentWriter=req.getParameter("commentWriter");
            String comment=req.getParameter("comment");
            String commentNoStr=req.getParameter("commentNo");

            int no = Integer.parseInt(noStr);

            if(commentNoStr==null) {
                BoardComment boardComment = new BoardComment();

                boardComment.setNo(no);
                boardComment.setCommentWriter(commentWriter);
                boardComment.setComment(comment);
                bc.addComment(boardComment);

            }else {
                int commentNo=Integer.parseInt(commentNoStr);
                BoardComment boardComment=bc.getComment(commentNo);

                boardComment.setComment(comment);
                bc.editComment(boardComment);
            }
            resp.sendRedirect("/board?cmd=view&no=" + no);
        }
        /**
         * 댓글을 수정하게 한다
         */
        else if("modifyComment".equals(cmd)){
            String comment=req.getParameter("comment");

            int no=Integer.parseInt(noStr);
            BoardComment boardComment=new BoardComment();

            boardComment.setComment(comment);
            boardComment.setNo(no);

            bc.editComment(boardComment);

            resp.sendRedirect("/board?cmd=view&no="+no);
        }
    }
}
