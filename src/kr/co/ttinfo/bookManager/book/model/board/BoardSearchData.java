package kr.co.ttinfo.bookManager.book.model.board;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class BoardSearchData {

    private String boardId;

    private String title;

    private String writer;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
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

    @Override
    public String toString() {
        return "BoardSearchData{" +
                "boardId='" + boardId + '\'' +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                '}';
    }
}


