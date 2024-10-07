package com.ra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.ra")
public class AppConfig implements WebMvcConfigurer {
    // cau hinh view jsp
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    // cau hinh keets noi database
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/session08_pt_fk_06?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }
    // cau hinh hibernate
    @Bean
    public LocalSessionFactoryBean sessionFactoryBean (){
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.ra.model.entity");
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql","show");
        properties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        // chế độ tự tạo bảng
        properties.setProperty("hibernate.hbm2ddl.auto","update");
        sessionFactoryBean.setHibernateProperties(properties);
        return sessionFactoryBean;
    }


}