package hop1;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.GuestCredentials;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentAccessFail {

    public static void main( String[] args ) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login( new GuestCredentials() );

        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ),
                () -> getResource( session ) );

        try {
            executor.invokeAll( callables )
                    .stream()
                    .map( future -> {
                        try {
                            return future.get();
                        } catch ( Exception e ) {
                            throw new IllegalStateException( e );
                        }
                    } )
                    .forEach( System.out::println );
        } finally {
            session.logout();
        }
    }

    private static String getResource( Session session ) throws RepositoryException {
//      Run in new thread for one session (non thread-safe)
        return session.getUserID();
    }

}
