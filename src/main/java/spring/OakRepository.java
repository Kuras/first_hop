package spring;

import org.apache.jackrabbit.core.TransientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Component
public class OakRepository {

    private Repository repository;

    /*
        * credentials
   	 */
    @Value("${loginAdminJcr}")
    private String loginAdminJcr;
    @Value("${passAdminJcr}")
    private String passAdminJcr;
    /**
     * jcr config
     */
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

    public String getLoginAdminJcr() {
        return loginAdminJcr;
    }

    public void setLoginAdminJcr( String loginAdminJcr ) {
        this.loginAdminJcr = loginAdminJcr;
    }

    public String getPassAdminJcr() {
        return passAdminJcr;
    }

    public void setPassAdminJcr( String passAdminJcr ) {
        this.passAdminJcr = passAdminJcr;
    }

    public String getConfigJcr() {
        return configJcr;
    }

    public void setConfigJcr( String configJcr ) {
        this.configJcr = configJcr;
    }

    public String getHomeJcr() {
        return homeJcr;
    }

    public void setHomeJcr( String homeJcr ) {
        this.homeJcr = homeJcr;
    }
}
