package web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import spring.AdminJcr;
import spring.CombineObservableSubscriber;
import spring.JackRabbitRepository;

import javax.jcr.RepositoryException;

@Configuration
@ComponentScan("spring.*")
@PropertySource("classpath:application.properties")
public class JackRabbitConfiguration {

    @Bean(name = "adminJcr")
    public AdminJcr adminJcr() {
        return new AdminJcr();
    }

    @Bean(name = "jackRabbitRepository")
    public JackRabbitRepository jackRabbitRepository() throws RepositoryException {
        return new JackRabbitRepository();
    }

    @Bean(name = "combineObservableSubscriber")
    public CombineObservableSubscriber combineObservableSubscriber() {
        return new CombineObservableSubscriber();
    }

}