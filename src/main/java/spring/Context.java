package spring;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

/*
* Functional Interface
* */
@FunctionalInterface
public interface Context<T> {
    public T run( Session session ) throws RepositoryException;
}
