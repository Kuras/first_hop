package hop1;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentCreate {

    public static void main( String[] args ) throws Exception {
        Repository repository = JcrUtils.getRepository();
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ),
                () -> getResource( repository ) );

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

    }

    private static String getResource( Repository repository ) throws RepositoryException {
//      Run in new thread for different session (thread-safe)
        Session session = repository.login(
                new SimpleCredentials( "admin", "admin".toCharArray() ) );
        try {
            CreateProperty.createNode( session );
            return session.getUserID();
        } finally {
            session.logout();
        }
    }

}
