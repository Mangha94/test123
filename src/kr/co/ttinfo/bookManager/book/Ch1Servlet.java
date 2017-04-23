package kr.co.ttinfo.bookManager.book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ttinfo on 2017-03-12.
 */
public class Ch1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out=resp.getWriter();
        java.util.Date today=new java.util.Date();
        out.println("<html>"+
                    "<body>"+
                    "<h1 align=center>HF/s' Chapter1 Servlet</h1>"
                    +"<br>"+today+"</body>"+"</html>");
    }


}
