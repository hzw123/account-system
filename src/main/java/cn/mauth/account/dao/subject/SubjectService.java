package cn.mauth.account.dao.subject;

import java.util.HashSet;
import java.util.Set;

public class SubjectService {

    private static Set<String> platformSubNo;

    public static boolean isPlatformSubNo(String subNo){
        if(platformSubNo == null || platformSubNo.size() < 1){
            initPlatformSubNo();
        }
        return platformSubNo.contains(subNo);
    }

    private synchronized static void initPlatformSubNo(){
        if(platformSubNo != null && platformSubNo.size() > 0){
            return;
        }else {
            platformSubNo = new HashSet<>();
            platformSubNo.add("1002100001");
            platformSubNo.add("2011301001");
        }
    }
}
