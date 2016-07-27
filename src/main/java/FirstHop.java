import org.apache.jackrabbit.commons.JcrUtils;

import javax.jcr.GuestCredentials;
import javax.jcr.Repository;
import javax.jcr.Session;

/** 
* First hop example. Logs in to a content repository and prints a 
* status message. 
*/ 
public class FirstHop { 

    /** 
    * The main entry point of the example application. 
    * 
    * @param args command line arguments (ignored) 
    * @throws Exception if an error occurs 
    */ 
    public static void main(String[] args) throws Exception { 
        Repository repository = JcrUtils.getRepository();
        Session session = repository.login(new GuestCredentials());
        try { 
            String user = session.getUserID(); 
            String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
            System.out.println( 
            "Logged in as " + user + " to a " + name + " repository."); 
        } finally { 
            session.logout();
        }

//        Repo is initialize when first session start | ----sess1----sess2----sess3-------------sessn----> and close when a last session end
//        See on lock file!


//        Session session1 = repository.login(new GuestCredentials());
//        try {
//            String user = session1.getUserID();
//            String name = repository.getDescriptor(Repository.REP_NAME_DESC);
//            System.out.println(
//            "Logged in as " + user + " to a " + name + " repository.");
//        } finally {
//            session1.logout();
//        }
    }
} 