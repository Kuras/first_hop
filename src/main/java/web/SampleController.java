package web;

import hop1.CreateProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import spring.AdminJcr;
import spring.CombineObservableSubscriber;

import javax.jcr.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class SampleController {

    @Autowired
    AdminJcr adminJcr;

    @Autowired
    CombineObservableSubscriber combineObservableSubscriber;


    @RequestMapping("/sample")
    public String sampleIt() throws RepositoryException {
        adminJcr.context( session -> {
            Node root = session.getRootNode();
            Node perla = root.addNode( "perla" );
            perla.setProperty( "propertyPerlaName", new String[]{"First value", "Second value", "Third value"} );
            session.save();

            Node node = root.getNode( "perla" );
            System.out.println( node.getPath() );
            System.out.println( Arrays.toString( node.getProperty( "propertyPerlaName" ).getValues() ) );
            return Optional.empty();
        } );

        return "Hello! Welcome to Spring Boot Sample. ";
    }

    @RequestMapping("/rxJavaC")
    public void sampleRxC() {

        combineObservableSubscriber.combine();
        combineObservableSubscriber.combine1();

        RestTemplate restTemplate = new RestTemplate();
        String quote = restTemplate.getForObject( "http://localhost:8111/rxJavaS", String.class );
        System.out.println( quote );
    }

    @RequestMapping("/rxJavaS")
    public String sampleRxS() throws RepositoryException {
        adminJcr.context( session -> {
            Node root = session.getRootNode();
            Node perla = root.addNode( "perla" );
            perla.setProperty( "propertyPerlaName", new String[]{"First value", "Second value", "Third value"} );
            session.save();

            Node node = root.getNode( "perla" );
            System.out.println( node.getPath() );
            System.out.println( Arrays.toString( node.getProperty( "propertyPerlaName" ).getValues() ) );
            return Optional.empty();
        } );

        return "Thread name:  " + Thread.currentThread().getName();
    }

    @RequestMapping("/concurrent")
    public String concurrent() throws RepositoryException, InterruptedException {
        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> getResource(),
                () -> getResource(),
                () -> getResource(),
                () -> getResource(),
                () -> getResource(),
                () -> getResource(),
                () -> getResource(),
                () -> getResource() );

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


        return "Hello! Cuncurretn test. ";
    }

    @RequestMapping("/recurrence")
    public String recurrence() throws RepositoryException {
        return adminJcr.context( session -> {
            Node root = session.getRootNode();
            StringBuilder sb = new StringBuilder();
            sb.append( "/" + "<br>" );

//            printTree( sb, root );
            sb.append( printTree1( root ) );

            return sb.toString();
        } );
    }

    private void printTree( StringBuilder sb, Node node ) throws RepositoryException {
        if ( !node.hasNodes() ) {
            sb.append( node.getPath().toString() );
            sb.append( "<br>" );
        } else {
            NodeIterator i$ = node.getNodes();
            while ( i$.hasNext() ) {
                printTree( sb, i$.nextNode() );
            }
        }
    }

    private String printTree1( Node node ) throws RepositoryException {
        NodeIterator i$ = node.getNodes();
        if ( !i$.hasNext() ) {
            return node.getPath().toString() + "<br>";
        } else {
            while ( i$.hasNext() ) {
                return printTree1( i$.nextNode() );
                // multiple return point => hard to perform tail call!
                // return printTree1 (node_1);
                // return printTree1 (node_2);
                // return printTree1 (node_3);
                // return printTree1 (node_4);
            }
            return "<br>";
        }
    }

    private String getResource() throws RepositoryException {
//      Run in new thread for different session (thread-safe)
        return adminJcr.context( session -> {
            CreateProperty.createNode( session );
            return session.getUserID();
        } );
    }
}
