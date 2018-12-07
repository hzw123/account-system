package cn.mauth.account.core.util;

import cn.mauth.account.core.bean.Pageable;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public final class PageUtil {

    public static Map<String,Object> result(PageInfo var){
        Map<String, Object> map = new HashMap<>();
        map.put("data", var);
        map.put("number", var.getTotal());
        return map;
    }

    public static void startPage(Pageable pageable){
        if(StringUtils.isEmpty(pageable.getOrderBy())){
            PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize());
        }else {
            PageHelper.startPage(pageable.getPageNum(),pageable.getPageSize(),pageable.getOrderBy());
        }
    }

}
