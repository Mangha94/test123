package kr.co.ttinfo.bookManager.book.model.book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 책 관련 프로퍼티 관리
 */
public class Books {
    private String title;
    private String writer;
    private String publisher;
    private int price;
    private String classification;
    private String id;
    private Date regDate;
    private boolean rented;

    //북 초기화
    public Books(){}

    public Books (ResultSet rs) throws SQLException
    {
        id = rs.getString ("id");
        title = rs.getString ("title");
        writer = rs.getString ("writer");

        publisher = rs.getString ("publisher");
        price = rs.getInt ("price");
        classification = rs.getString ("classification");
        rented = rs.getBoolean ("rented");

        regDate = rs.getDate ("regDate");

    }

    public void books(){
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    //책 관련 인자들을 받고 내보낸다.
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPrice ()
    {
        return price;
    }

    public void setPrice (int price)
    {
        this.price = price;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getId(){return id;}

    public void setId(String id){
        this.id=id;
    }
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String toString()
    {
        return "[id='"+id+'\''+
                "책제목='"+title+'\''+
                "저자='"+writer+'\''+
                ",출판사='"+publisher+'\''+
                ",가격='"+price+'\''+
                ",분류='"+classification+'\''+
                ",대여여부="+isRented()+'\''+
                ']'+"\n";
    }


}