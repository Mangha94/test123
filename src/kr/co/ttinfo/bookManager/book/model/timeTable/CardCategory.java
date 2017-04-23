package kr.co.ttinfo.bookManager.book.model.timeTable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardCategory {
    private int cardCategoryNo;
    private String cardCategoryTitle;

    public CardCategory(){}

    public CardCategory(ResultSet rs) throws SQLException {
        cardCategoryNo =rs.getInt("cardCategoryNo");
        cardCategoryTitle =rs.getString("cardCategoryTitle");
    }

    public int getCardCategoryNo() {
        return cardCategoryNo;
    }

    public void setCardCategoryNo(int cardCategoryNo) {
        this.cardCategoryNo = cardCategoryNo;
    }

    public String getCardCategoryTitle() {
        return cardCategoryTitle;
    }

    public void setCardCategoryTitle(String cardCategoryTitle) {
        this.cardCategoryTitle = cardCategoryTitle;
    }

    @Override
    public String toString() {
        return "CardCategory{" +
                "cardCategoryNo=" + cardCategoryNo +
                ", cardCategoryTitle='" + cardCategoryTitle + '\'' +
                '}';
    }
}
