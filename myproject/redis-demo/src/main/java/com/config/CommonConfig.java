package com.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class CommonConfig {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private Environment environment;

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
        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/**/*Mapper.xml"));//加载配置文件的地址;
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
    @Bean
    public CuratorFramework curatorFramework() {
        //创建curatorFramework实例
        //指定了客户端连接到zookeeper服务端的策略;这里是采用重试的机制(5次,每次间隔1秒)
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString(environment.getProperty("zk.host")).namespace(environment.getProperty("zk.namespace")).retryPolicy(new RetryNTimes(5,1000)).build();
        curatorFramework.start();
        return curatorFramework;
    }

}
