package kr.co.ttinfo.bookManager.book.model.timeTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.ttinfo.bookManager.book.BookManageDB;

public class CardCenter {

    BookManageDB bookManageDB = new BookManageDB();

    /**
     * 카드 목록을 가져온다
     *
     * @return
     */
    public List<CardCategory> getCardCategories() {
        List<CardCategory> categoryList = new ArrayList<>();
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM cardCategory";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                categoryList.add(new CardCategory(rs));
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return categoryList;
    }

    /**
     * 카드 하나를 가져온다
     */
    public CardCategory getCardCategory(int cardCategoryNo) {
        CardCategory category = null;
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM cardCategory WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, cardCategoryNo);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                category = new CardCategory(rs);
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return category;
    }

    /**
     * 콘텐츠 제목에 속한 내용을 가져온다
     * @param categoryNo
     * @return
     */
    public List<CardContent> getContents(int categoryNo){
        List<CardContent> contentList=new ArrayList<>();
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM CardContent WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, categoryNo);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                contentList.add(new CardContent(rs));
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return contentList;
    }

    /**
     * 콘텐츠 내용 하나를 가져온다
     * @param contentNo
     * @return
     */
    public CardContent getContent(int contentNo){
        CardContent content=null;
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM cardContent WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, contentNo);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                content = new CardContent(rs);
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }
    /**
     * 콘텐츠에 속한 댓글들을 가져온다
     *
     * @param contentNo
     * @return
     */
    public List<CardComment> getComments(int contentNo) {
        List<CardComment> commentList = new ArrayList<>();
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM cardComment WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, contentNo);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                commentList.add(new CardComment(rs));
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return commentList;
    }

    /**
     * 댓글하나를 가져온다
     *
     * @param commentNo
     * @return
     */
    public CardComment getComment(int commentNo) {
        CardComment comment = null;
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "SELECT * FROM cardComment WHERE commentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, commentNo);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                comment = new CardComment(rs);
            }

            rs.close();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return comment;
    }

    /**
     * 카드 추가한다
     */
    public void addCategory(CardCategory category) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "INSERT INTO cardCategory(cardCategoryTitle) VALUE(?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, category.getCardCategoryTitle());

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 콘텐츠 내용을 추가한다
     */
    public void addContent(CardContent content){
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "INSERT INTO cardContent (cardCategoryNo,title,content) " +
                    "VALUE( ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, content.getCardCategoryNo());
            pStmt.setString(2, content.getTitle());
            pStmt.setString(3, content.getContent());

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 댓글을 추가한다
     */
    public void addComment(CardComment comment) {
        comment.setCommentRegDate(new Date());
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "INSERT INTO cardComment (cardCategoryNo,contentNo,comment,commentWriter,commentRegDate) " +
                    "VALUE( ?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, comment.getCardCategoryNo());
            pStmt.setInt(2, comment.getContentNo());
            pStmt.setString(3, comment.getComment());
            pStmt.setString(4, comment.getCommentWriter());
            pStmt.setDate(5, new java.sql.Date(comment.getCommentRegDate().getTime()));

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 카드를 수정한다
     *
     * @param cardCategory
     */
    public void modifyCategory(CardCategory cardCategory) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "UPDATE cardCategory SET cardCategoryTitle=? WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, cardCategory.getCardCategoryTitle());
            pStmt.setInt(2, cardCategory.getCardCategoryNo());

            pStmt.executeUpdate();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 콘텐츠 내용을 수정한다
     * @param content
     */
    public void modifyContent(CardContent content){
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "UPDATE cardContent SET content=? WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, content.getContent());
            pStmt.setInt(2, content.getContentNo());

            pStmt.executeUpdate();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 댓글을 수정한다
     *
     * @param comment
     */
    public void modifyComment(CardComment comment) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "UPDATE cardComment SET comment=?, commentWriter=? WHERE commentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, comment.getComment());
            pStmt.setString(2, comment.getCommentWriter());
            pStmt.setInt(3, comment.getCommentNo());

            pStmt.executeUpdate();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 카드를 지운다
     * @param cardCategoryNo
     */
    public void deleteCategory(int cardCategoryNo) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardCategory WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, cardCategoryNo);

            pStmt.executeUpdate();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 콘텐츠 내용을 지운다
     * @param contentNo
     */
    public void deleteContent(int contentNo){
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardContent WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, contentNo);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 댓글을 지운다
     *
     * @param commentNo
     */
    public void deleteComment(int commentNo) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardComment WHERE commentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, commentNo);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 카테고리가 지워졌을때 해당 모든 콘텐츠 내용을 지운다
     * @param cardCategoryNo
     */
    public void deleteAllContents(int cardCategoryNo){
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardContent WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, cardCategoryNo);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 카테고리가 삭제되었을때 속한 댓글들을 전부 지운다
     * @param cardCategoryNo
     */
    public void deleteAllComments(int cardCategoryNo) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardComment WHERE cardCategoryNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, cardCategoryNo);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 콘텐츠가 지워졌을때 해당 댓글을 전부 삭제한다
     * @param contentNo
     */
    public void deleteCommentsByContent(int contentNo){
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "DELETE FROM cardComment WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, contentNo);

            pStmt.executeUpdate();

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 콘텐츠의 완료 여부를 바꾼다
     *
     * @param contentNo
     */
    public void updateComplete(int contentNo, boolean isCompleted) {
        try {
            Connection conn = bookManageDB.makeConnect();
            String sql = "UPDATE cardContent SET completed=? WHERE contentNo=?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setBoolean(1, isCompleted);
            pStmt.setInt(2, contentNo);

            pStmt.executeUpdate();

            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 해당 콘텐츠가 완료되었다고 바꿔준다
     *
     * @param contentNo
     */
    public void complete(int contentNo) {
        updateComplete(contentNo, true);
    }

    /**
     * 해당 콘텐츠를 다시 되돌려준다
     *
     * @param contentNo
     */
    public void returnCompleted(int contentNo) {
        updateComplete(contentNo, false);
    }
}
