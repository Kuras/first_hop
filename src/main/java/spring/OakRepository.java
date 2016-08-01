package spring;

import org.apache.jackrabbit.commons.JcrUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Component
public class OakRepository {

    private static OakRepository instance;
    private Repository repository;

    /*
   	 * credentials
   	 */
   	@Value("${loginAdminJcr}")
   	private String loginAdminJcr;

   	@Value("${passAdminJcr}")
   	private String passAdminJcr;

    private OakRepository() throws RepositoryException {
        repository = JcrUtils.getRepository();
    }

    public static OakRepository getInstance() throws RepositoryException {
        if ( instance == null ) {
            instance = new OakRepository();
            return instance;
        }
        return instance;
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
}
