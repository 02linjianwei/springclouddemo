package com.config;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.business.rabbitmqdemo.entity.Publisher;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.FieldRetrievingFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class CommonConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setJdbcTypeForNull(null);
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        OracleKeyGenerator oracleKeyGenerator = new OracleKeyGenerator();
//        FieldRetrievingFactoryBean fieldRetrievingFactoryBean = new FieldRetrievingFactoryBean();
//        fieldRetrievingFactoryBean.setStaticField(String.valueOf(FieldStrategy.IGNORED));
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setKeyGenerator(oracleKeyGenerator);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfig);
        //mybatisSqlSessionFactoryBean.setConfigLocation((resolver.getResources("classpath:mybatis-config.xml"))[0]);
        // sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        /** 设置datasource */
        mybatisSqlSessionFactoryBean.setDataSource(applicationContext.getBean(DataSource.class));
        // sqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));//加载配置文件的地址;
        //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:sqlmapper/*.xml"));//加载配置文件的地址;

//        sqlSessionFactoryBean.setMapperLocations(new Resource[]{new ClassPathResource("classpath:mapper/*.xml")});

        /** 设置typeAlias 包扫描路径 */
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.business.redyw.mapper");
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        PageHelper pageHelper = new PageHelper();
        Interceptor[] interceptors ={optimisticLockerInterceptor,paginationInterceptor};
        mybatisSqlSessionFactoryBean.setPlugins(interceptors);
        return mybatisSqlSessionFactoryBean;
    }


}
