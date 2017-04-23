package kr.co.ttinfo.bookManager.book.model.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Lsh on 2017-04-10.
 */
public class BoardComment {
    private int commentNo;
    private int no;
    private String comment;
    private Date commentRegDate;
    private String commentWriter;

    public BoardComment(){
    }
    public BoardComment(ResultSet rs) throws SQLException {
        no=rs.getInt("no");
        comment=rs.getString("comment");
        commentWriter=rs.getString("commentWriter");
        commentRegDate=rs.getDate("commentRegDate");
        commentNo=rs.getInt("commentNo");
    }


    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

    public Date getCommentRegDate() {return commentRegDate;}

    public void setCommentRegDate(Date commentRegDate) {this.commentRegDate = commentRegDate;}

    public String getCommentWriter() {return commentWriter;}

    public void setCommentWriter(String commentWriter) {this.commentWriter = commentWriter;}

    public int getCommentNo() {return commentNo;}

    public void setCommentNo(int commentNo) {this.commentNo = commentNo;}

    public int getNo() {return no;}

    public void setNo(int no) {this.no = no;}
}
