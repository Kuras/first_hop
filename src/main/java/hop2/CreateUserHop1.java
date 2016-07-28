package hop2;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.SessionImpl;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.security.*;
import java.util.Arrays;


public class CreateUserHop1 {

    public static void main( String[] args ) throws Exception {
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
                new SimpleCredentials( "admin", "admin".toCharArray() ) );
        try {
            //create node
            Node rootNode = session.getRootNode();
           	Node grantedNode = rootNode.addNode("granted");
            session.save();
            System.out.println("Granted node: " + grantedNode.getPath());

            //create user
            UserManager um = ( ( SessionImpl ) session ).getUserManager();
            User so = um.createUser( "Marian", "oosz" );
            User soAuth = ( User ) um.getAuthorizable( "Stasiek" );
            System.out.println( so.getPrincipal() );
            System.out.println( soAuth.getPrincipal() );

            // Grant test user
            AccessControlManager acm = ( ( SessionImpl ) session ).getAccessControlManager();
            AccessControlPolicyIterator acpi = acm.getApplicablePolicies(grantedNode.getPath());
            while ( acpi.hasNext() ) {
                AccessControlPolicy acp = acpi.nextAccessControlPolicy();
                Privilege[] privileges = new Privilege[] { acm.privilegeFromName(Privilege.JCR_WRITE) };
                printPrivileges(privileges);

                ((AccessControlList) acp).addAccessControlEntry(new PrincipalImpl("Stasiek"), privileges);
                AccessControlEntry[] ace = ((AccessControlList) acp).getAccessControlEntries();
                for (int i=0; i<ace.length; i++) {
                    System.out.println("AccessControlEntry: "+i);
                    System.out.println("Principal: "+ace[i].getPrincipal().getName());
                    printPrivileges(ace[i].getPrivileges());
                }
                acm.setPolicy(grantedNode.getPath(), acp);
            }
            // Apply the policy
           	session.save();

            // Test user login
           	Session sessionTest = repository.login(new SimpleCredentials("Marian", "oosz".toCharArray()));
           	Node testRootNode = sessionTest.getRootNode();
           	testRootNode.getNode(grantedNode.getPath().substring(1)).addNode("my node");
            sessionTest.save();
           	sessionTest.logout();
        } finally {
            // Admin logout
            session.logout();
        }
    }

    private static void printPrivileges( Privilege[] privileges ) {
        Arrays.stream( privileges ).forEach( p -> System.out.println(p) );
    }

} 