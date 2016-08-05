package web;

import hop1.CreateProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.AdminJcr;

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


    @RequestMapping("/sample")
    public String sampleIt() throws RepositoryException {
        adminJcr.context(session -> {
            Node root = session.getRootNode();
            Node prop = root.addNode("prop");
            prop.setProperty("propertyName", new String[]{"First value", "Second value", "Third value"});
            session.save();

            Node node = root.getNode("prop");
            System.out.println(node.getPath());
            System.out.println(Arrays.toString(node.getProperty("propertyName").getValues()));
            return Optional.empty();
        });

        return "Hello! Welcome to Spring Boot Sample. ";
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
                () -> getResource());

        executor.invokeAll(callables)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);


        return "Hello! Cuncurretn test. ";
    }

    @RequestMapping("/recurrence")
    public String recurrence() throws RepositoryException {
        return adminJcr.context(session -> {
            Node root = session.getRootNode();
            StringBuilder sb = new StringBuilder(  );
            printTree(sb,root);

            return sb.toString();
        });
    }

    private void printTree( StringBuilder sb, Node node ) throws RepositoryException {
        sb.append( "/n" );
        if (!node.hasNodes()){
            sb.append( node.getPath().toString() + node.getName().toString() );
        } else {
            printTree( sb, node.getNodes().nextNode() );
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
