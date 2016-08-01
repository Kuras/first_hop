package spring;


import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;

public class Main {
    public static void main( String[] args ) throws RepositoryException {
        ( new AdminJcr() ).context( new AdminJcr.Context() {
            @Override
            public void run( Session session ) throws RepositoryException {
                Node root = session.getRootNode();
                Node prop = root.addNode( "prop" );
                prop.setProperty( "propertyName", new String[]{"First value", "Second value", "Third value"} );
                session.save();

                Node node = root.getNode("prop");
                System.out.println(node.getPath());
                System.out.println( Arrays.toString( node.getProperty( "propertyName" ).getValues() ) );
            }
        } );
    }
}
