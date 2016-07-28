package hop2;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;

import javax.jcr.*;
import javax.jcr.security.*;
import java.util.Arrays;


public class CreateGroupHop {

    public static void main( String[] args ) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
                new SimpleCredentials( "admin", "admin".toCharArray() ) );
        try {
            //create node
            Node rootNode = session.getRootNode();
            Node grantedNode = rootNode.addNode( "granted" );
            session.save();
            System.out.println( "Granted node: " + grantedNode.getPath() );

            // Create the Authors group if it doesn't exist already.
            UserManager um = ( ( SessionImpl ) session ).getUserManager();
            ValueFactory valueFactory = session.getValueFactory();
            Authorizable authors = um.getAuthorizable( "authors" );
            if ( authors == null ) {
                authors = um.createGroup( "authors" );
                authors.setProperty( "displayName", valueFactory.createValue( "Authors" ) );
            }

            // Create the default author if it doesn't already exist.
            Authorizable author = um.getAuthorizable( "Stasiek" );
            if ( author == null ) {
                author = um.createUser( "Stasiek", "Oszust" );
                author.setProperty( "displayName", valueFactory.createValue( "Default Author" ) );
            }

            // Add author member to authors group
            ( ( Group ) authors ).addMember( author );
            // Save our session
            session.save();

            // Grant group authors
            AccessControlManager acm = ( ( SessionImpl ) session ).getAccessControlManager();
            AccessControlPolicyIterator acpi = acm.getApplicablePolicies( grantedNode.getPath() );
            while ( acpi.hasNext() ) {
                AccessControlPolicy acp = acpi.nextAccessControlPolicy();
                Privilege[] privileges = new Privilege[]{acm.privilegeFromName( Privilege.JCR_ALL )};
                printPrivileges( privileges );

                ( ( AccessControlList ) acp ).addAccessControlEntry( new PrincipalImpl( "authors" ), privileges );
                AccessControlEntry[] ace = ( ( AccessControlList ) acp ).getAccessControlEntries();
                for ( int i = 0; i < ace.length; i++ ) {
                    System.out.println( "AccessControlEntry: " + i );
                    System.out.println( "Principal: " + ace[i].getPrincipal().getName() );
                    printPrivileges( ace[i].getPrivileges() );
                }
                acm.setPolicy( grantedNode.getPath(), acp );
            }
            // Apply the policy for group
            session.save();

            // Test user login
            Session sessionTest = repository.login( new SimpleCredentials( "Stasiek", "Oszust".toCharArray() ) );
            Node testRootNode = sessionTest.getRootNode();
            Node myNode = testRootNode.getNode( grantedNode.getPath().substring( 1 ) ).addNode( "my node" );
            System.out.println( testRootNode.getNode( grantedNode.getPath().substring( 1 ) ).getNode( "my node" ).getPath() );
            System.out.println( grantedNode.getPath() );
//            System.out.println( grantedNode.getNode( "my node" ) );  //==> error
            System.out.println( myNode.getPath() );
            sessionTest.save();
            sessionTest.logout();
        } finally {
            // Admin logout
            session.logout();
        }
    }

    private static void printPrivileges( Privilege[] privileges ) {
        Arrays.stream( privileges ).forEach( p -> System.out.println( p ) );
    }

} 