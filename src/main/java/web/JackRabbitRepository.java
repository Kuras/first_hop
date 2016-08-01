package web;

import org.apache.jackrabbit.commons.JcrUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.AdminJcr;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;

@Configuration
@ComponentScan("spring.*")
@PropertySource("classpath:application.properties")
public class JackRabbitRepository {

    @Bean(name = "adminJcr")
    public AdminJcr adminJcr() {
        return new AdminJcr();
    }

    @Bean(name = "oak")
    public Repository repository() throws RepositoryException {
        return JcrUtils.getRepository();
    }

}