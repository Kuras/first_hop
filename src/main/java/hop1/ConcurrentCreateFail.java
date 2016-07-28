package hop1;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentCreateFail {

    public static void main( String[] args ) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
                new SimpleCredentials("admin", "admin".toCharArray()));

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
        CreateProperty.createNode( session );
        return session.getUserID();
    }

}
