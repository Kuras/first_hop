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


public class LogInUserHop {

    public static void main(String[] args) throws Exception {
    Repository repository = JcrUtils.getRepository();
        Session session = repository.login(
        new SimpleCredentials("john", "doe".toCharArray()));
        try {
            Node root = session.getRootNode();

            // Store content
            Node n = root.addNode("john_node");
            session.save();

            // Retrieve content
            Node node = root.getNode("john_node");
            System.out.println(node.getPath());
        } finally {
            session.logout();
        }
    }

} 