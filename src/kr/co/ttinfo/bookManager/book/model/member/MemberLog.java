package kr.co.ttinfo.bookManager.book.model.member;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ttinfo on 2017-02-21.
 */
public class MemberLog {
        MemberCenter mc;
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        public MemberLog(MemberCenter mc){
            this.mc=mc;
        }

        public void memberLog(Members member){

            int cnt=1;

            Date date =new Date();

            member.setRegDate(date);

            for (Members m : mc.getMembers ())
            {
                if(df.format (m.getRegDate()).equals(df.format(date))){
                    ++cnt;
                }
            }

            member.setMemberNum (""+df.format(date)+ String.format(("0000%d"),cnt));
        }
    }

