package kr.co.ttinfo.bookManager.book.model.board;

import kr.co.ttinfo.bookManager.book.BookManageDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ttinfo on 2017-03-21.
 */
public class BoardCenter {

    public BoardCenter() {
    }

    BookManageDB bookManageDB = new BookManageDB();


    /**
     * 게시글 목록 보여주는 메소드
     */

    public List<Board> getBoards() {
        List<Board> list = new ArrayList<>();
        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "SELECT * from board";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                list.add(new Board(rs));
            }
            rs.close();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * 게시물을 불러온다
     */
    public Board getBoard(int no){
        Board board=null;
        try{
            Connection conn=bookManageDB.makeConnect();

            String sql="SELECT * FROM board WHERE no=?";

            PreparedStatement pStmt=conn.prepareStatement(sql);
            pStmt.setInt(1,no);

            ResultSet rs=pStmt.executeQuery();

            if(rs.next()){
                board=new Board(rs);
            }

            rs.close();

            conn.close();

        }catch(Exception ex){

        }
        return board;
    }

    /**
     * 게시글 추가 메소드
     */

    public void addContent(Board board) {
        try {
            Connection conn = bookManageDB.makeConnect();

            //글 추가 된 날짜 저장
            board.setRegDate(new Date());
            String sql = "INSERT INTO board (title,writer,content,boardId,regDate,hit) VALUES (? ,? ,? ,? ,? ,?)";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getWriter());
            pstmt.setString(3, board.getContent());
            pstmt.setString(4, board.getBoardId());
            pstmt.setDate(5, new java.sql.Date(board.getRegDate().getTime()));
            pstmt.setInt(6, board.getHit());

            pstmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 찾기 메소드
     */
    private List<Board> search(String element, String keyWord) {
        List<Board> findList = new ArrayList<>();

        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "select * from board where " + element + " LIKE ?";// sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement(sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setString(1, "%" + keyWord + "%");

            ResultSet rs = pStmt.executeQuery();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next()) {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                findList.add(new Board(rs));                       //복수일땐 while,하나 만 리턴할 경우 if
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return findList;
    }

    //제목으로 찾기
    public List<Board> findBytitle(String title) {
        return search("title", title);
    }

    //작성자로 찾기
    public List<Board> findBywriter(String writer) {
        return search("writer", writer);
    }

    //출판사로 찾기
    public List<Board> findByBoardId(String BoardId) {
        return search("BoardId", BoardId);
    }

    //날짜로 찾기
    public List<Board> findByDate(String regDate) {
        return search("regDate", regDate);
    }

    /**
     * 게시글 삭제 메소드
     */
    public boolean remove(int no) {
        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "DELETE FROM board WHERE no=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);

            pstmt.executeUpdate();

            conn.close();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * 게시글 수정 메소드
     */

    public void edit(Board board) {
        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "UPDATE board SET title=? ,writer=? ,content=? where no=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, board.getTitle());
            pStmt.setString(2, board.getWriter());
            pStmt.setString(3, board.getContent());
            pStmt.setInt(4, board.getNo());
            System.out.println(sql);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Board> boardList(BoardSearchData bsd) {

        System.out.println(bsd);
        List<Board> boardList = new ArrayList<>();
        try {
            Connection conn = bookManageDB.makeConnect();

            List<String> whereList = new ArrayList<>();

            if (bsd.getBoardId() != null && !bsd.getBoardId().equals("")) {
                whereList.add("boardId='" + bsd.getBoardId() + "'");
            }
            if (bsd.getTitle() != null && !bsd.getTitle().equals("")) {
                whereList.add("title LIKE '%" + bsd.getTitle() + "%'");
            }
            if (bsd.getWriter() != null && !bsd.getWriter().equals("")) {
                whereList.add("writer LIKE '%" + bsd.getWriter() + "%'");
            }

            String result = "";

            if (whereList.size() > 0) {
                result = "WHERE ";

                String[] arrays = whereList.toArray(new String[whereList.size()]);

                result += String.join(" AND ", arrays);
            }

            String sql = "select * from board " + result;

            System.out.println(sql);
            PreparedStatement pStmt = conn.prepareStatement(sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.

            ResultSet rs = pStmt.executeQuery();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next()) {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                boardList.add(new Board(rs));                       //복수일땐 while,하나 만 리턴할 경우 if
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return boardList;

    }

    //제목으로 찾기
    public List<Board> findtitle(BoardSearchData bsd) {
        return boardList(bsd);
    }

    //작성자로 찾기
    public List<Board> findwriter(BoardSearchData bsd) {
        return boardList(bsd);
    }

    //출판사로 찾기
    public List<Board> findBoardId(BoardSearchData bsd) {
        return boardList(bsd);
    }

    /**
     * 게시물 내용을 보여준다
     *
     * @param no
     * @return
     */
    //todo 조회수 올라가게 하기 board.setHit(board.getHit()+1);
    public Board showContent(int no) {
        Board board = null;
        try {
            Connection conn = new BookManageDB().makeConnect();
            String sql = "SELECT * FROM board where no=?";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, no);

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                board = new Board(rs);                       //복수일땐 while,하나 만 리턴할 경우 if
            }

            rs.close();

            conn.close();

            return board;
        } catch (Exception ex)

        {
            ex.printStackTrace();
        }
        return null;
    }

    public void addComment(BoardComment boardComment) {
        boardComment.setCommentRegDate(new Date());
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "INSERT INTO boardComment (comment,commentWriter,commentRegDate,no) VALUES (? ,? ,?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, boardComment.getComment());
            pStmt.setString(2, boardComment.getCommentWriter());
            pStmt.setDate(3, new java.sql.Date(boardComment.getCommentRegDate().getTime()));
            pStmt.setInt(4, boardComment.getNo());

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<BoardComment> getComments(int no) {
        List<BoardComment> commentList = new ArrayList<>();

        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "SELECT * from boardComment WHERE no=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, no);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                commentList.add(new BoardComment(rs));
            }
            rs.close();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commentList;
    }

    /**
     * 댓글 하나를 불러온다
     */
    public BoardComment getComment(int commnetNo){
        BoardComment comment=null;
        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "SELECT * from boardComment WHERE commentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, commnetNo);

            ResultSet rs = pStmt.executeQuery();

            if(rs.next()) {
                comment=new BoardComment(rs);
            }
            rs.close();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comment;
    }

    /**
     * 댓글을 삭제 한다
     * @param commentNo-댓글no
     */
    public void deleteComment(int commentNo) {
        try{
            Connection conn=bookManageDB.makeConnect();
            String sql="DELETE FROM boardComment WHERE commentNo=?";
            PreparedStatement pStmt=conn.prepareStatement(sql);
            pStmt.setInt(1,commentNo);

            pStmt.executeUpdate();

            conn.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 해당 게시물이 삭제되었을때 다 삭제한다
     * @param no-게시물 no
     */
    public void deleteAllComment(int no){
        try{
            Connection conn=bookManageDB.makeConnect();
            String sql="DELETE FROM boardComment where no=?";
            PreparedStatement pStmt=conn.prepareStatement(sql);
            pStmt.setInt(1,no);

            pStmt.executeUpdate();

            conn.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 댓글을 수정한다
     */
    public void editComment(BoardComment boardComment){
        try{
            Connection conn=bookManageDB.makeConnect();
            String sql="UPDATE boardComment SET comment=? WHERE commentNo=?";
            PreparedStatement pStmt=conn.prepareStatement(sql);
            pStmt.setString(1,boardComment.getComment());
            pStmt.setInt(2,boardComment.getCommentNo());

            pStmt.executeUpdate();

            conn.close();

        }catch(Exception ex){

        }
    }
}