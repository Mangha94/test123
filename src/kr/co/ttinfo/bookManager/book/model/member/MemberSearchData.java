package kr.co.ttinfo.bookManager.book.model.member;

/**
 * Created by Lsh on 2017-04-04.
 */
public class MemberSearchData {
    private String name;
    private String memberId;
    private String phonnumber;
    private String birthday;

    public MemberSearchData(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPhonnumber() {
        return phonnumber;
    }

    public void setPhonnumber(String phonnumber) {
        this.phonnumber = phonnumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
