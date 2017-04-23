package kr.co.ttinfo.bookManager.book.model.timeTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CardComment {
    private int cardCategoryNo;
    private int contentNo;
    private int commentNo;
    private String comment;
    private String commentWriter;
    private Date commentRegDate;

    public CardComment(){}

    public CardComment(ResultSet rs) throws SQLException {
        cardCategoryNo =rs.getInt("cardCategoryNo");
        contentNo=rs.getInt("contentNo");
        commentNo=rs.getInt("commentNo");
        comment=rs.getString("comment");
        commentWriter=rs.getString("commentWriter");
        commentRegDate=rs.getDate("commentRegDate");
    }

    public int getCardCategoryNo() {
        return cardCategoryNo;
    }

    public void setCardCategoryNo(int cardCategoryNo) {
        this.cardCategoryNo = cardCategoryNo;
    }

    public int getContentNo() {
        return contentNo;
    }

    public void setContentNo(int contentsNo) {
        this.contentNo = contentsNo;
    }

    public int getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(int commentNo) {
        this.commentNo = commentNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }

    public Date getCommentRegDate() {
        return commentRegDate;
    }

    public void setCommentRegDate(Date commentRegDate) {
        this.commentRegDate = commentRegDate;
    }
}
