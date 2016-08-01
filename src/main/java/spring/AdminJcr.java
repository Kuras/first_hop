package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Service
public class AdminJcr {

    @Autowired
    OakRepository oakRepository;

    public void context( Context context ) throws RepositoryException {
        Session session = null;
        try {
            session = oakRepository.newAdminSession();
            context.run( session );
        } finally {
            if ( session != null ) {
                session.logout();
            }
        }
    }

}
