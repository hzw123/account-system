package cn.mauth.account.core.util.cache;

import cn.mauth.account.SpringContextUtil;
import cn.mauth.account.core.util.GlobalConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用于提供一个基础缓存类，用于提供初始化及获取逻辑
 */
public abstract class CacheManager {

    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private static final ConcurrentHashMap<String, Map<String, List<Object>>> sysCache = new ConcurrentHashMap<>();

    private static final String linkSymbol = GlobalConstant.keyLinkSymbol;

    /**
     * 如果从缓存中不能得到需求的值，需要子类负责完成应执行的功能。
     * @return
     */
    public abstract List<Object> addNewCacheValue(String searchKey);

    public List<?> getCacheValue(Class service, Method serviceMethod, Class dataType,
                                 String[] keyNames, String databaseKey){
        String cacheName = getCacheName(service, serviceMethod);
        if(!sysCache.containsKey(cacheName)){
            initCache(service, serviceMethod, dataType, keyNames);
        }
        Map<String, List<Object>> cache = sysCache.get(cacheName);
        String mapDataKey = dataType.getSimpleName()+linkSymbol+databaseKey;
        if(cache.containsKey(mapDataKey)){
            return cache.get(mapDataKey);
        } else {
            List<Object> objects = addNewCacheValue(databaseKey);
            cache.put(mapDataKey, objects);
            return objects;
        }
    }

    /**
     * @param service 提供数据服务的类名
     * @param serviceMethod 提供数据服务的方法名
     * @param dataType 数据类型
     * @param keyNames 缓存获取指定数据的key。譬如在AccountRule数据中，keyNames为transType
     */
    public static void initCache(Class service, Method serviceMethod, Class dataType,
                                 String[] keyNames){
        Map<String, List<Object>> cache = new HashMap<>();
        String cacheName = getCacheName(service, serviceMethod);
        if(sysCache.containsKey(cacheName)){
            cache = sysCache.get(cacheName);
        } else {
            sysCache.put(cacheName, cache);
        }
        Object obj = SpringContextUtil.getBean(service);
        try {
            Object data = serviceMethod.invoke(obj, null);

            Field[] keyFields = new Field[keyNames.length];
            for(int i = 0; i < keyNames.length; i++){
                keyFields[i] = dataType.getField(keyNames[i]);
            }

            List<?> list = (List<?>)data;
            for(Object object : list){
                StringBuilder keyBuilder = new StringBuilder(dataType.getSimpleName()+linkSymbol);
                for(int i = 0; i < keyFields.length; i++){
                    keyBuilder.append(keyFields[i].get(object));
                    keyBuilder.append(linkSymbol);
                }
                String key = keyBuilder.toString().substring(0, keyBuilder.length() - 1);
                List<Object> datas = new ArrayList<>();
                if(cache.containsKey(key)){
                    datas = cache.get(key);
                }
                datas.add(object);
                cache.put(key, datas);
            }
        } catch (Exception e) {
            logger.error("执行缓存数据获取方法出错！", e);
            return;
        }
    }

    private static String getCacheName(Class service, Method serviceMethod){
        return service.getSimpleName() + serviceMethod.getName();
    }

    public static void flushSysCache(){
        AccountCache.initAccountCache();
        logger.info("账户缓存初始化完成");
        TransRuleCache.initTransRuleCache();
        logger.info("记账规则缓存初始化完成");
    }

}
