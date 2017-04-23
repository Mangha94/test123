package kr.co.ttinfo.bookManager.book.model.book;

import kr.co.ttinfo.bookManager.book.BookManageDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 책 장부를 구성한다
 */
public class BookCenter
{

    //todo 싱글턴(인스턴스
    private List<Books> booklist;
    private HashMap bookmap;
    BookIdAdd IdAdd=new BookIdAdd();

    BookManageDB bookManageDB = new BookManageDB ();


    /**
     * 책장부를 생성
     */
    public BookCenter()
    {
        booklist = new ArrayList<>();
        bookmap=new HashMap();
    }

    /**
     * 책 고유 아이디를 생성한다
     * @param book
     */
    public void addBook(Books book)
    {
            IdAdd.bookIdAdd(book);

        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "INSERT INTO books (id,title,writer,publisher,price,classification,regDate,rented) VALUES (? ,? ,? ,? ,? , ?, ?, ?)";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1,book.getId ());
            pstmt.setString(2,book.getTitle ());
            pstmt.setString(3,book.getWriter ());
            pstmt.setString (4,book.getPublisher());
            pstmt.setInt(5, book.getPrice ());
            pstmt.setString(6, book.getClassification ());
            pstmt.setDate(7, new java.sql.Date(book.getRegDate ().getTime()));
            pstmt.setBoolean(8,book.isRented());

            pstmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close ();

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();
        }

    }

    /**
     * 책장부의 복사본을 표출 본 장부는 감출필요가 있음
     * @return
     */
    public List<Books> getBooks()
    {
        List<Books> list = new ArrayList<>();
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from Books";                        // sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            // pStmt.setString (1, "test");

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                list.add (new Books (rs));
            }

            rs.close ();

            conn.close ();

        }
         catch(Exception ex)
         {
         }
        return list;
    }

    /**
     * 날짜별로 아이디 부여
     */
    public boolean checkIdDate(){
        for(Books books : booklist){
            Date date=new Date();
            if(books.getRegDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    /**
     * 책의 제목을 찾아 지운다
     * @param removetitle
     * @return 지워지면 true
     */
    public boolean bookremove(String removetitle)
    {
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "DELETE FROM books WHERE title=?";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1,removetitle);

            pstmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close ();

            return true;

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();

            return false;
        }
    }

    /**
     * 책의 고유 id을 찾아 지운다
     * @param id
     * @return
     */
    public boolean remove(String id){
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "DELETE FROM books WHERE id=?";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1,id);

            pstmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close ();

            return true;

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();

            return false;
        }
    }

    /**
     * 각각 책의 제목 작자 아이디 출판사를 검색할수 있게 한다
     * @param element
     * @param keyWord
     * @return
     */
    private List<Books> search(String element,String keyWord)
    {
        List<Books>findList=new ArrayList<>();

        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from books where " + element + " LIKE ?";// sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setString (1, "%"+keyWord+"%");

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                findList.add(new Books (rs));                       //복수일땐 while,하나 만 리턴할 경우 if
            }

            rs.close ();

            conn.close ();

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();
        }
        return findList;
    }
    //제목으로 찾기
    public List<Books> findBytitle(String title)
    {
        return search("title",title);
    }

    //작가로 찾기
    public List<Books>findBywriter(String writer)
    {
        return search("writer",writer);
    }

    //출판사로 찾기
    public List<Books>findBypublisher(String publisher)
    {
        return search("publisher",publisher);
    }

    public List<Books>findByDate(String regDate){return search("regDate",regDate);}
    //아이디로 찾기
    public Books findById(String id){
        Books returnVal = null;

        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from books where id=?";                        // sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setString (1, id);

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            if (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                returnVal = new Books (rs);
            }

            rs.close ();

            conn.close ();

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();
        }

        return returnVal;
    }

    //책이 빌려갔다는 메소드
    public void borrowBook(String id) {
        updateRented(id,true);
    }

    //책을 반납하는 메소드
    public boolean ReturnBook(String id){

        updateRented(id,false);

        return true;
    }

    private boolean updateRented(String id,boolean isRented) {
        try {
            Connection conn = bookManageDB.makeConnect();

            String sql = "UPDATE books SET rented=? where id=?";                        // sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement(sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setBoolean(1, isRented);
            pStmt.setString(2, id);

            pStmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}

