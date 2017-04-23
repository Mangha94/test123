package kr.co.ttinfo.bookManager.book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ttinfo on 2017-03-12.
 */
public class Ch1Servlet3 extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("test","hello");
        RequestDispatcher view=req.getRequestDispatcher("WEB-INF/view.jsp");

        view.forward(req,resp);
    }
}
