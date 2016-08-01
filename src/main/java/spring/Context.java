package spring;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/*
* Functional Interface
* */
public interface Context {
    public void run( Session session ) throws RepositoryException;
}
