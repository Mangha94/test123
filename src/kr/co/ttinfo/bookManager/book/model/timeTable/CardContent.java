package kr.co.ttinfo.bookManager.book.model.timeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardContent {
    private int cardCategoryNo;
    private int contentNo;
    private String content;
    private String title;
    private boolean completed;

    public CardContent(){
        completed=false;
    }

    public CardContent(ResultSet rs) throws SQLException {
        cardCategoryNo =rs.getInt("cardCategoryNo");
        contentNo=rs.getInt("contentNo");
        title=rs.getString("title");
        content=rs.getString("content");
        completed=rs.getBoolean("completed");
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

    public void setContentNo(int contentNo) {
        this.contentNo = contentNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
