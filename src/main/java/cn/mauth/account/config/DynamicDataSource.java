package cn.mauth.account.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal< String > DATA_SOURCE_KEY = new ThreadLocal<>();

    public static void setDataSourceKey ( String dataSource ) {

        DATA_SOURCE_KEY.set( dataSource );

    }

    private static void clear () {

        DATA_SOURCE_KEY.remove();

    }

    @Override
    protected Object determineCurrentLookupKey () {

        final String lookupKey = DATA_SOURCE_KEY.get();

        clear();

        return lookupKey;
    }
}
