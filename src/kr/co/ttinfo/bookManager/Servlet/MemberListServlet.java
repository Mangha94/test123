package kr.co.ttinfo.bookManager.Servlet;

import kr.co.ttinfo.bookManager.book.model.board.BoardSearchData;
import kr.co.ttinfo.bookManager.book.model.member.MemberCenter;
import kr.co.ttinfo.bookManager.book.model.member.MemberSearchData;
import kr.co.ttinfo.bookManager.book.model.member.Members;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.List;

/**
 * Created by ttinfo on 2017-03-16.
 */
@WebServlet(value = "/member")
public class MemberListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MemberCenter mc = new MemberCenter();
        MemberSearchData msd = new MemberSearchData();
        Members member = new Members();

        String cmd = req.getParameter("cmd");
        if ("delete".equals(cmd)) {
            String memberId = req.getParameter("memberId");
            mc.remove(memberId);

            resp.sendRedirect("/member");
        } else {
            String memberNum = req.getParameter("memberNum");
            if (memberNum != null) {
                member = mc.getMember(memberNum);

                req.setAttribute("member", member);
            }
                String findName = req.getParameter("findName");
                String findMemberId = req.getParameter("findMemberId");
                String findPhonnumber = req.getParameter("findPhonnumber");
                String findBirthday = req.getParameter("findBirthday");

                List<Members> members = null;

                if (!"".equals(findName)) {
                    msd.setName(findName);
                }
                if (!"".equals(findMemberId)) {
                    msd.setMemberId(findMemberId);
                }
                if (!"".equals(findPhonnumber)) {
                    msd.setPhonnumber(findPhonnumber);
                }
                if (!"".equals(findBirthday)) {
                    msd.setBirthday(findBirthday);
                }

                members = mc.findList(msd);

                req.setAttribute("members", members);
                req.setAttribute("findName", findName);
                req.setAttribute("findMemberId", findMemberId);
                req.setAttribute("findPhonnumber", findPhonnumber);
                req.setAttribute("findBirthday", findBirthday);
            }

            RequestDispatcher view = req.getRequestDispatcher("MemberList.jsp");

            view.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("member", "memberList");

        MemberCenter mc = new MemberCenter();

        String name = req.getParameter("name");
        String memberId = req.getParameter("memberId");
        String phonnumber = req.getParameter("phonnumber");
        String birthday = req.getParameter("birthday");
        String pw=req.getParameter("pw");
        String memberNum=req.getParameter("memberNum");

        if(memberNum!=null){
            Members member=mc.getMember(memberNum);
            member.setName(name);
            member.setMemberId(memberId);
            member.setPhonnumber(phonnumber);
            member.setBirthday(birthday);
            member.setPw(pw);

            mc.editMember(member);

        }else {
            Members member = new Members();
            member.setName(name);
            member.setMemberId(memberId);
            member.setPhonnumber(phonnumber);
            member.setBirthday(birthday);
            member.setPw(pw);

            mc.addMembers(member);
        }

        resp.sendRedirect("/member");
    }
}