package spring;

import lombok.Getter;
import lombok.Setter;
import org.apache.jackrabbit.core.TransientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Getter
@Setter
@Component
public class JackRabbitRepository {

    private Repository repository;
    // admin credentials
    @Value("${loginAdminJcr}")
    private String loginAdminJcr;
    @Value("${passAdminJcr}")
    private String passAdminJcr;

    // jcr config
    @Value("${configJcr}")
    private String configJcr;
    @Value("${homeJcr}")
    private String homeJcr;

    @PostConstruct
    public void init() {
        repository = new TransientRepository( configJcr, homeJcr );
    }


    public Session newAdminSession() throws RepositoryException {
        return repository.login( new SimpleCredentials( loginAdminJcr, passAdminJcr.toCharArray() ) );
    }
}
