package kr.co.ttinfo.bookManager.Servlet;

import kr.co.ttinfo.bookManager.book.model.book.BookCenter;
import kr.co.ttinfo.bookManager.book.model.book.Books;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttinfo on 2017-03-13.
 */
//
@WebServlet(value = "/book")
public class BookListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     req.setCharacterEncoding("UTF-8");

        //req.setAttribute("re");
        BookCenter bc=new BookCenter();

        String cmd=req.getParameter("cmd");
        if(cmd==null){
            cmd="";
        }
        if(cmd.equals("delete")) {
            String id = req.getParameter("id");
            bc.remove(id);

            resp.sendRedirect("/book");
        return;
        }
        else if(cmd.equals("borrow")){
            String id=req.getParameter("id");
            bc.borrowBook(id);

            resp.sendRedirect("/book");
            return;
        }
        else if(cmd.equals("returnBook")){
            String id=req.getParameter("id");
            if(bc.ReturnBook(id)){
                resp.sendRedirect("/book");
            }else{

            }
            return;
        }
            String classification=req.getParameter("classification");
            String keyword=req.getParameter("keyword");
            List<Books> books = null;
            if(classification==null||keyword==null||"".equals(keyword)){
                books=bc.getBooks();
            }else{
                if("title".equals(classification)){
                    books=bc.findBytitle(keyword);
                }else if("id".equals(classification)){
                    books=new ArrayList<>();
                    books.add(bc.findById(keyword));
                }else if("writer".equals(classification)){
                    books=bc.findBywriter(keyword);
                }else if("publisher".equals(classification)){
                    books=bc.findBypublisher(keyword);
                }
            }
            req.setAttribute("books", books);
            req.setAttribute("classification",classification);
            req.setAttribute("keyword",keyword);

            req.setAttribute("test",req.getSession().getAttribute("test"));

        RequestDispatcher view = req.getRequestDispatcher("BookList.jsp");

        view.forward(req, resp);

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("book","bookList");
        Books books=new Books();
        BookCenter bc=new BookCenter();

        String title=req.getParameter("title");
        books.setTitle(title);
        String writer=req.getParameter("writer");
        books.setWriter(writer);
        String publisher=req.getParameter("publisher");
        books.setPublisher(publisher);
        String price=req.getParameter("price");
        books.setPrice(Integer.parseInt(price));
        String classification=req.getParameter("classification");
        books.setClassification(classification);

        bc.addBook(books);

        resp.sendRedirect("/book");
    }
}
