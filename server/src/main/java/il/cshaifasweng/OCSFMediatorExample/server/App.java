package il.cshaifasweng.OCSFMediatorExample.server;


import java.io.IOException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class App 
{
	private static SimpleServer server;
	
    public static void main(String[] args ) throws IOException
    {
        server = new SimpleServer(3000);
        server.listen();
        SimpleServer.init();
        
    }

}