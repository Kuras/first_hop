package web;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:dmsRepository.xml")
public class JackRabbitRepository {
//
//    @Bean(name = "repository")
//    public JndiObjectFactoryBean getJndiObjectFactoryBean() {
//        JndiObjectFactoryBean factoryBean = new JndiObjectFactoryBean();
//        factoryBean.setJndiName( "java:comp/env/jcr/myRepository" );
//        return factoryBean;
//    }
//
//    @Bean(name = "jcrSessionFactory")
//    public JcrSessionFactory getJcrSessionFactory() {
//        JcrSessionFactory sessionFactory  = new JcrSessionFactory();
//        return sessionFactory;
//    }
//
//    @Bean(name = "jcrTemplate")
//    public JcrTemplate getJcrTemplate() {
//        JcrTemplate jcrTemplate  = new JcrTemplate();
//        return jcrTemplate;
//    }


}