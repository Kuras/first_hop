package hop2;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.core.security.principal.PrincipalImpl;

import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.security.*;


public class CreateUserHop {

    /**
    * The main entry point of the example application.
    *
    * @param args command line arguments (ignored)
    * @throws Exception if an error occurs
    */
    public static void main(String[] args) throws Exception {
    Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
        new SimpleCredentials("admin", "admin".toCharArray()));
        try {
            UserManager um = ((JackrabbitSession ) session).getUserManager();
            User user = um.createUser("john1", "doe1");
            Node root = session.getRootNode();

            /*   And assign some ALC as follows... And then play with it like this, which really sucks without proper documentation, one has to reverse engineer everything, wtf */

            AccessControlManager acm = session.getAccessControlManager();
            AccessControlPolicyIterator it = acm.getApplicablePolicies(root.getPath());
            while ( it.hasNext() ) {
                AccessControlPolicy acp = it.nextAccessControlPolicy();

                Privilege[] privileges = new Privilege[]{acm.privilegeFromName(Privilege.JCR_WRITE)};

                ((AccessControlList )acp).addAccessControlEntry(new PrincipalImpl(user.getID()), privileges);

                acm.setPolicy(root.getPath(), acp);
            }

            System.out.println(root.getPath());
        } finally {
            session.logout();
        }
    }

} 