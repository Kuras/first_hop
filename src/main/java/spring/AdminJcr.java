package spring;

import org.apache.jackrabbit.commons.JcrUtils;
import org.springframework.stereotype.Component;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

@Component
public class AdminJcr {

    public void context( Context context ) throws RepositoryException {
        Repository repository = JcrUtils.getRepository();
        Session session = null;
        try {
            session = repository.login( new SimpleCredentials( "admin", "admin".toCharArray() ) );
            context.run( session );
        } finally {
            if ( session != null ) {
                session.logout();
            }
        }
    }

}
