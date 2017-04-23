package kr.co.ttinfo.bookManager.book.model.member;

import kr.co.ttinfo.bookManager.book.BookManageDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 관련 장부 생성 클래스
 * 추가 메소드,찾기 메소드
 */
public class MemberCenter {

    //todo 싱글턴(인스턴스)
    private List<Members> memberlist;

    BookManageDB bookManageDB = new BookManageDB ();

    MemberLog ml;

    public MemberCenter() {
        memberlist = new ArrayList<>();
        ml = new MemberLog(this);
    }

    public List<Members> getMembers() {

        List<Members> list = new ArrayList<>();
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from members";                        // sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            // pStmt.setString (1, "test");

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                list.add (new Members (rs));
            }

            rs.close ();

            conn.close ();

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();
        }
        return list;
    }

    /**
     * 회원 하나의 정보를 가져온다
     */
    public Members getMember(String memberNum){
        Members member=null;
        try{
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from members WHERE memberNum=?";
            PreparedStatement pStmt = conn.prepareStatement (sql);
            pStmt.setString(1,memberNum);

            ResultSet rs = pStmt.executeQuery ();

            if (rs.next ())
            {
                member=new Members (rs);
            }

            rs.close ();

            conn.close ();

        }catch(Exception ex){

        }
        return member;
    }

    /**
     * 멤버 추가하는 메소드
     * @param member
     */
    public void addMembers(Members member)

    {
        ml.memberLog(member);

        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "INSERT INTO members (memberId,phonnumber,birthday,regDate,pw,memberNum, name) VALUES (? ,? ,? ,? ,? , ?, ?)";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1,member.getMemberId ());
            pstmt.setString(2,member.getPhonnumber ());
            pstmt.setString(3,member.getBirthday ());
            pstmt.setDate (4, new java.sql.Date (member.getRegDate ().getTime ()));    // 현재 날짜와 시간

            pstmt.setString(5, member.getPw ());
            pstmt.setString(6, member.getMemberNum ());
            pstmt.setString(7, member.getName ());

            pstmt.executeUpdate();                                        // 쿼리를 실행한다.

            conn.close ();

        }
        catch(Exception ex)
        {
            ex.printStackTrace ();
        }
    }

    public List<Members> search(String element, String keyWord) {
        List<Members> findList = new ArrayList<>();
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from members where " + element + " LIKE ?";// sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setString (1, "%"+keyWord+"%");

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            while (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                findList.add(new Members (rs));                       //복수일땐 while,하나 만 리턴할 경우 if
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

    /**
     * 아이디로 회원을 찾는다.
     * @param memberId 회원 아이디
     * @return 회원을 찾았다면 회원 객체를 리턴하고, 찾지 못했다면 null 을 리턴한다.
     */
    public Members findByMemberID(String memberId)
    {
        Members returnVal = null;

        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "select * from members where memberId=?";                        // sql 쿼리
            PreparedStatement pStmt = conn.prepareStatement (sql);// prepareStatement에서 해당 sql을 미리 컴파일한다.
            pStmt.setString (1, memberId);

            ResultSet rs = pStmt.executeQuery ();// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

            if (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                returnVal = new Members (rs);
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

    public List<Members> findByName(String name) {
        return search("name", name);
    }

    public List<Members> findByPhonNumber(String phonnumber) {
        return search("phonnumber", phonnumber);
    }

    public boolean remove(String memberId)
    {
        try
        {
            Connection conn = bookManageDB.makeConnect ();

            String sql = "DELETE FROM members WHERE memberId=?";        // sql 쿼리
            PreparedStatement pstmt = conn.prepareStatement(sql);                          // prepareStatement에서 해당 sql을 미리 컴파일한다.
            pstmt.setString(1,memberId);

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

    public List<Members> findList(MemberSearchData msd){

        List<Members>findList=new ArrayList<>();
        try{
            Connection conn=bookManageDB.makeConnect();

            List<String>whereList=new ArrayList<>();

        if(msd.getBirthday()!=null&&!msd.getBirthday().equals("")){
            whereList.add("birthday='"+msd.getBirthday()+"'");
        }else if(msd.getName()!=null&&!msd.getName().equals("")){
            whereList.add("name='"+msd.getName()+"'");
        }else if(msd.getMemberId()!=null&&!msd.getMemberId().equals("")){
            whereList.add("memberId='"+msd.getMemberId()+"'");
        }else if(msd.getPhonnumber()!=null&&!msd.getPhonnumber().equals("")){
            whereList.add("phonnumber='"+msd.getPhonnumber()+"'");
        }

        String result="";

        if(whereList.size()>0){
            result="WHERE ";

            String[]arrays=whereList.toArray(new String[whereList.size()]);

            result+=String.join(" AND ",arrays);
        }

            String sql="SELECT * FROM members "+result;

            PreparedStatement pStmt=conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next ())
            {                                                        // 결과를 한 행씩 돌아가면서 가져온다.
                findList.add(new Members(rs));                       //복수일땐 while,하나 만 리턴할 경우 if
            }

            pStmt.close();

            conn.close();

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return findList;
    }

    /**
     * 회원정보 수정
     */
    public void editMember(Members member){
        try{
            Connection conn=bookManageDB.makeConnect();
            String sql="UPDATE members SET name=?,memberId=?, phonnumber=?, birthday=?, pw=? WHERE memberNum=?";
            PreparedStatement pStmt=conn.prepareStatement(sql);
            pStmt.setString(1,member.getName());
            pStmt.setString(2,member.getMemberId());
            pStmt.setString(3,member.getPhonnumber());
            pStmt.setString(4,member.getBirthday());
            pStmt.setString(5,member.getPw());
            pStmt.setString(6,member.getMemberNum());

            pStmt.executeUpdate();

            conn.close();

        }catch(Exception ex){

        }
    }
}
