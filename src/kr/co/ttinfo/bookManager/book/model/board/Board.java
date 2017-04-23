package kr.co.ttinfo.bookManager.book.model.board;

import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;

import static javax.swing.UIManager.getString;

public class Board {
    private int no;
    private String title;
    private String writer;
    private String content;
    private Date regDate;
    private int hit;
    private String boardId;

    public Board(){
        hit=0;
    }

    public Board(ResultSet rs) throws SQLException{
        no=rs.getInt("no");
        title=rs.getString("title");
        writer=rs.getString("writer");
        content=rs.getString("content");
        regDate=rs.getDate("regDate");
        hit=rs.getInt("hit");
        boardId=rs.getString("boardId");

    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public String toString() {
        return "Board{" +
                "no=" + no +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", regDate=" + regDate +
                ", hit=" + hit +
                ", boardId='" + boardId + '\'' +
                '}';
    }
}
