package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.AdminJcr;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;

@RestController
public class SampleController {

    @Autowired
    AdminJcr adminJcr;


    @RequestMapping("/sample")
    public String sampleIt() throws RepositoryException {
        adminJcr.context( session ->  {
                Node root = session.getRootNode();
                Node prop = root.addNode( "prop" );
                prop.setProperty( "propertyName", new String[]{"First value", "Second value", "Third value"} );
                session.save();

                Node node = root.getNode( "prop" );
                System.out.println( node.getPath() );
                System.out.println( Arrays.toString( node.getProperty( "propertyName" ).getValues() ) );
            });

        return "Hello! Welcome to Spring Boot Sample. ";
    }
}
