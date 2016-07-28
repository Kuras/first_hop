package hop1;

import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.*;
import java.util.Arrays;

public class CreateProperty {

    public static void main(String[] args) throws Exception {
    Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
        new SimpleCredentials("admin", "admin".toCharArray()));
        try {
            createNode( session );
            session.save();
        } finally {
            session.logout();
        }
    }

    public static void createNode( Session session ) throws RepositoryException {
        Node root = session.getRootNode();
        // Store content
        Node prop = root.addNode("prop");
        prop.setProperty("propertyName", new String[] {"First value", "Second value", "Third value"});
        session.save();

        // Retrieve content
        Node node = root.getNode("prop");
        System.out.println(node.getPath());
        System.out.println( Arrays.toString( node.getProperty( "propertyName" ).getValues() ) );

        // Remove content
        root.getNode("prop").remove();
    }
} 