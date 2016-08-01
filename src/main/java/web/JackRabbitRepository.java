package web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.AdminJcr;

@Configuration
@ComponentScan("spring.*")
//@ImportResource("classpath:dmsRepository.xml")
@PropertySource("classpath:application.properties")
public class JackRabbitRepository {

    @Bean(name = "adminJcr")
    public AdminJcr adminJcr() {
        return new AdminJcr();
    }

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