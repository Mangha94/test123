package kr.co.ttinfo.bookManager.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.smartcardio.Card;

import kr.co.ttinfo.bookManager.book.model.timeTable.CardCategory;
import kr.co.ttinfo.bookManager.book.model.timeTable.CardCenter;
import kr.co.ttinfo.bookManager.book.model.timeTable.CardComment;
import kr.co.ttinfo.bookManager.book.model.timeTable.CardContent;

@WebServlet(value = "/card")
public class CardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        CardCenter cardCenter = new CardCenter();

        String cmd = req.getParameter("cmd");
        if (cmd == null) {
            cmd = "";
        }
        if ("delete".equals(cmd)) {
            delete(req, resp, cardCenter);
        } else if ("getNo".equals(cmd)) {
            getNo(req, resp, cardCenter);
        } else if ("".equals(cmd)) {
            view(req, resp, cardCenter);
        }else if("commentList".equals(cmd)){
            commentListView(req, resp, cardCenter);
        }
    }

    private void getNo(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws ServletException, IOException {
        /**
         * 수정할 것의 넘버를 받아서 해당 정보를 뿌려준다
         */
        String cardCategoryNoStr = req.getParameter("cardCategoryNo");
        String contentNoStr = req.getParameter("contentNo");
        String commentNoStr = req.getParameter("commentNo");

        if (cardCategoryNoStr != null && contentNoStr != null && commentNoStr != null) {
            int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
            int contentNo = Integer.parseInt(contentNoStr);
            int commentNo = Integer.parseInt(commentNoStr);
            CardComment getComment = cardCenter.getComment(commentNo);

            req.setAttribute("commentNo", commentNo);
            req.setAttribute("getComment", getComment);

            resp.sendRedirect("/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo);
        } else if (cardCategoryNoStr != null && contentNoStr != null) {
            int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
            int contentNo = Integer.parseInt(contentNoStr);
            CardContent getContent = cardCenter.getContent(contentNo);

            req.setAttribute("contentNo", contentNo);
            req.setAttribute("getContent", getContent);

            resp.sendRedirect("/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo);
        } else if (cardCategoryNoStr != null) {
            int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
            CardCategory getCardCategory = cardCenter.getCardCategory(cardCategoryNo);

            System.out.println(getCardCategory);
            req.setAttribute("cardCategoryNo", cardCategoryNo);
            req.setAttribute("getCardCategory", getCardCategory);

            resp.sendRedirect("/card");
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws ServletException, IOException {
        /**
         * 카드 넘버가 넘어왔을때 해당 카드,콘텐츠,댓글을 전부 삭제한다
         * 콘텐츠 넘버가 넘어 왔을때 해당 콘텐츠,댓글을 전부 삭제한다
         * 댓글 넘버가 넘어 왔을때 해당 댓글을 삭제한다
         */
        String cardCategoryNoStr = req.getParameter("cardCategoryNo");
        String contentNoStr = req.getParameter("contentNo");
        String commentNoStr = req.getParameter("commentNo");

        if (cardCategoryNoStr != null && contentNoStr != null && commentNoStr != null) {
            int commentNo = Integer.parseInt(commentNoStr);
            int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
            int contentNo = Integer.parseInt(contentNoStr);

            cardCenter.deleteComment(commentNo);

            //resp.sendRedirect("/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo);
            req.setAttribute("message","삭제되었습니다");
            req.setAttribute("url","/card");
            RequestDispatcher view = req.getRequestDispatcher("WEB-INF/move.jsp");

            view.forward(req, resp);
        } else if (cardCategoryNoStr != null && contentNoStr != null) {
            int contentNo = Integer.parseInt(contentNoStr);
            int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
            cardCenter.deleteCommentsByContent(contentNo);
            cardCenter.deleteContent(contentNo);

            resp.sendRedirect("/card?cardCategoryNo=" + cardCategoryNo);
        } else if (cardCategoryNoStr != null) {
            int categoryNo = Integer.parseInt(cardCategoryNoStr);

            cardCenter.deleteAllComments(categoryNo);
            cardCenter.deleteAllContents(categoryNo);
            cardCenter.deleteCategory(categoryNo);

            resp.sendRedirect("/card");
        }
    }

    private void commentListView(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws ServletException, IOException {
        String contentNo=req.getParameter("contentNo");

        List<CardComment> commentList = cardCenter.getComments(Integer.parseInt(contentNo));
        req.setAttribute("commentList",commentList);

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/commentList.jsp");

        view.forward(req, resp);
    }
    private void view(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws ServletException, IOException {
        /**
         * 커멘드가 없을때는 카드들을 뿌린다
         * 카드 넘버가 넘어왔을때 해당 콘텐츠 목록을 뿌린다
         * 콘텐츠 넘버가 넘어왔을때 해당 콘텐츠와 해당 댓글 목록을 뿌린다
         */
        List<CardCategory> categoryList = cardCenter.getCardCategories();

        req.setAttribute("categoryList", categoryList);

        Map<Integer, List<CardContent>> contentMap = new HashMap<>();
        Map<Integer, List<CardComment>> commentMap = new HashMap<>();

        for (CardCategory cardCategory : categoryList) {
            List<CardContent> contentList = cardCenter.getContents(cardCategory.getCardCategoryNo());
            contentMap.put(cardCategory.getCardCategoryNo(), contentList);

            for (CardContent cardContent : contentList) {
                List<CardComment> commentList = cardCenter.getComments(cardContent.getContentNo());
                commentMap.put(cardContent.getContentNo(), commentList);
            }
        }

        req.setAttribute("contentMap", contentMap);
        req.setAttribute("commentMap", commentMap);

        String commentNoStr = req.getParameter("commentNo");

        if (commentNoStr != null) {
            int commentNo = Integer.parseInt(commentNoStr);
            CardComment getComment = cardCenter.getComment(commentNo);

            req.setAttribute("commentNo", commentNo);
            req.setAttribute("getComment", getComment);
        }

        RequestDispatcher view = req.getRequestDispatcher("Card.jsp");

        view.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        CardCenter cardCenter = new CardCenter();

        String cmd = req.getParameter("cmd");
        if ("addCardCategory".equals(cmd)) {
            addCardCategory(req, resp, cardCenter);

        } else if ("addContent".equals(cmd)) {
            addContent(req, resp, cardCenter);

        } else if ("addComment".equals(cmd)) {

            addComment(req, resp, cardCenter);

        } else if ("modify".equals(cmd)) {
            modify(req, resp, cardCenter);

        } else if ("complete".equals(cmd)) {
            complete(req, resp, cardCenter);

        } else if ("returnResult".equals(cmd)) {
            returnResult(req, resp, cardCenter);

        }

    }

    private void addCardCategory(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException {

        String cardCategoryTitle = req.getParameter("cardCategoryTitle");
        CardCategory category = new CardCategory();

        category.setCardCategoryTitle(cardCategoryTitle);
        cardCenter.addCategory(category);

        resp.sendRedirect("/card");
    }

    private void addContent(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException {
        String cardCategoryNoStr = req.getParameter("cardCategoryNo");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        CardContent cardContent = new CardContent();

        int categoryNo = Integer.parseInt(cardCategoryNoStr);
        cardContent.setCardCategoryNo(categoryNo);
        cardContent.setTitle(title);
        cardContent.setContent(content);
        cardCenter.addContent(cardContent);

        resp.sendRedirect("/card?categoryNo=" + categoryNo);
    }

    private void addComment(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException, ServletException {
        String cardCategoryNoStr = req.getParameter("cardCategoryNo");
        String contentNoStr = req.getParameter("contentNo");
        String comment = req.getParameter("comment");
        String commentWriter = req.getParameter("commentWriter");

        int cardCategoryNo = Integer.parseInt(cardCategoryNoStr);
        int contentNo = Integer.parseInt(contentNoStr);

        CardComment cardComment = new CardComment();

        cardComment.setCardCategoryNo(cardCategoryNo);
        cardComment.setContentNo(contentNo);
        cardComment.setComment(comment);
        cardComment.setCommentWriter(commentWriter);
        cardCenter.addComment(cardComment);
//        resp.sendRedirect("/card?cardCategoryNo=" + cardCategoryNo + "&contentNo=" + contentNo);
        req.setAttribute("message","등록되었습니다");
        req.setAttribute("url","/card");

        RequestDispatcher view = req.getRequestDispatcher("WEB-INF/move.jsp");

        view.forward(req, resp);
    }

    private void returnResult(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException {
        /**
         * 되돌리기 버튼을 누르면 해당 콘텐츠는 원래상태로 바뀐다
         */
        String categoryNoStr = req.getParameter("categoryNo");
        String contentNoStr = req.getParameter("contentNo");
        if (categoryNoStr != null) {
            if (contentNoStr != null) {
                int categoryNo = Integer.parseInt(categoryNoStr);
                int contentNo = Integer.parseInt(contentNoStr);
                cardCenter.returnCompleted(contentNo);

                resp.sendRedirect("/card?cardCategoryNo=" + categoryNo + "&contentNo=" + contentNo);
            }
        }
    }

    private void complete(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException {
        /**
         * 완료 버튼을 누르면 해당 콘텐츠는 완료상태로 바뀐다
         */
        String categoryNoStr = req.getParameter("categoryNo");
        String contentNoStr = req.getParameter("contentNo");
        if (categoryNoStr != null) {
            if (contentNoStr != null) {
                int categoryNo = Integer.parseInt(categoryNoStr);
                int contentNo = Integer.parseInt(contentNoStr);
                cardCenter.complete(contentNo);

                resp.sendRedirect("/card?cardCategoryNo=" + categoryNo + "&contentNo=" + contentNo);
            }
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp, CardCenter cardCenter) throws IOException {
        /**
         * 카드 넘버가 넘어왔을때 카드를 수정한다
         * 콘텐츠 넘버가 넘어왔을때 콘텐츠를 수정한다
         * 댓글 넘버가 넘어왔을때 댓글을 수정한다
         */
        String cardCategoryNoStr = req.getParameter("cardCategoryNo");
        String contentNoStr = req.getParameter("contentNo");
        String commentNoStr = req.getParameter("commentNo");
        if (cardCategoryNoStr != null && contentNoStr != null && commentNoStr != null) {
            int commentNo = Integer.parseInt(commentNoStr);
            CardComment cardComment = cardCenter.getComment(commentNo);

            String commentWriter = req.getParameter("commentWriter");
            String comment = req.getParameter("comment");

            cardComment.setCommentWriter(commentWriter);
            cardComment.setComment(comment);
            cardCenter.modifyComment(cardComment);

            resp.sendRedirect("/card");
        } else if (cardCategoryNoStr != null && contentNoStr != null) {
            int contentNo = Integer.parseInt(contentNoStr);
            CardContent cardContent = cardCenter.getContent(contentNo);
            String content = req.getParameter("content");

            cardContent.setContent(content);
            cardCenter.modifyContent(cardContent);

            resp.sendRedirect("/card");
        } else if (cardCategoryNoStr != null) {
            CardCategory category = cardCenter.getCardCategory(Integer.parseInt(cardCategoryNoStr));
            String cardTitle = req.getParameter("cardTitle");

            category.setCardCategoryTitle(cardTitle);
            cardCenter.modifyCategory(category);

            resp.sendRedirect("/card");
        }
    }
}
