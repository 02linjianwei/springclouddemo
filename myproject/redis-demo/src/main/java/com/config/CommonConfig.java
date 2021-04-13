package com.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class CommonConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean createSqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        /** 设置mybatis configuration 扫描路径 */
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setConfigLocation((resolver.getResources("classpath:mybatis-config.xml"))[0]);
        // sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        /** 设置datasource */
        mybatisSqlSessionFactoryBean.setDataSource(applicationContext.getBean(DataSource.class));
        // sqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));//加载配置文件的地址;
        //sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:sqlmapper/*.xml"));//加载配置文件的地址;

//        sqlSessionFactoryBean.setMapperLocations(new Resource[]{new ClassPathResource("classpath:mapper/*.xml")});

        /** 设置typeAlias 包扫描路径 */
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage("com.business.mapper");
        return mybatisSqlSessionFactoryBean;
    }
}
