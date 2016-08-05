package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Service
public class AdminJcr {

    @Autowired
    JackRabbitRepository jackRabbitRepository;

    public <T> T context( Context<T> context ) throws RepositoryException {
        Session session = null;
        try {
            session = jackRabbitRepository.newAdminSession();
            return context.run( session );
        } finally {
            if ( session != null ) {
                session.logout();
            }
        }
    }

}
