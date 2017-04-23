package kr.co.ttinfo.bookManager.book.model.book;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ttinfo on 2017-02-21.
 */
public class BookIdAdd {
    BookCenter bc;
    DateFormat df = new SimpleDateFormat("yyyyMMdd");
    int cnt=1;
    Date date =new Date();

    public void BookIdAdd(BookCenter bc){
        this.bc=bc;
    }
    public void bookIdAdd(Books book){
        book.setRegDate(date);

        if(df.format (book.getRegDate()).equals(df.format(date))){
            ++cnt;
        }
        book.setId(""+df.format(date)+ String.format(("0000%d"),cnt));
    }
}
