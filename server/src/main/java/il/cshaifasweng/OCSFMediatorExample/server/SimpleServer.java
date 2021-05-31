package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
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

import il.cshaifasweng.OCSFMediatorExample.entities.AllocatedObject;
import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.MovieScreening;
import il.cshaifasweng.OCSFMediatorExample.entities.Seat;
import il.cshaifasweng.OCSFMediatorExample.entities.User;


public class SimpleServer extends AbstractServer {

	private static SessionFactory sessionFactory = getSessionFactory();

	private static Session session;
	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		AllocatedObject advanced_msg = (AllocatedObject)msg;
		String msgString = advanced_msg.getMessage();

		if(msgString.startsWith("#getAllMovies")) {
			
			try {
	    		session = sessionFactory.openSession();
                ArrayList<Movie> movies = getAllMovies();
				client.sendToClient(new AllocatedObject(movies, "#receiveMovies"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (msgString.startsWith("#getAllScreenings")) {
		
			String movie_name = msgString.substring(18);
			System.out.println(movie_name);
			try {
	    		session = sessionFactory.openSession();
                ArrayList<MovieScreening> dates = getAllDates(movie_name);
				client.sendToClient(new AllocatedObject(dates, "#receiveScreenings"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (msgString.startsWith("#deleteScreening")) {
			
			MovieScreening screening = (MovieScreening)advanced_msg.getObject();
			MovieScreening temp = screening;
			
			System.out.println(screening.getMovie().getEngName());
			System.out.println(screening.getScreeningDate());
			
			 try {
		    		session = sessionFactory.openSession();
		    	    session.beginTransaction();
		            session.delete(screening);
		            session.getTransaction().commit();
		     }
		    catch (HibernateException e) {
		         e.printStackTrace();
		         session.getTransaction().rollback();
		        }
			 session.close();
			 session = sessionFactory.openSession();
             try {
				ArrayList<MovieScreening> dates = getAllDates(temp.getMovie().getEngName());
				client.sendToClient(new AllocatedObject(dates, "#deletedScreenings"+temp.getMovie().getEngName()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (msgString.startsWith("#addScreening")) {
			
			String movie_date = msgString.substring(13);
			Movie new_movie = session.get(Movie.class, (String)((AllocatedObject)msg).getObject());
			MovieScreening new_screening = new MovieScreening(new_movie, movie_date, null, null);
			try {
		    		session = sessionFactory.openSession();
		    	    session.beginTransaction();
		            session.save(new_screening);
		            session.flush();
		            session.getTransaction().commit();
		    }
		    catch (HibernateException e) {
		         e.printStackTrace();
		         session.getTransaction().rollback();
		        }
			 session.close();
			 session = sessionFactory.openSession();
             try {
				ArrayList<MovieScreening> dates = getAllDates(new_movie.getEngName());
				client.sendToClient(new AllocatedObject(dates, "#deletedScreenings"+new_movie.getEngName()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(msgString.startsWith("#updateScreening"))
		{
			MovieScreening screening = (MovieScreening)advanced_msg.getObject();
			
			String dates = msgString.substring(16);
			System.out.println(dates);
			
			 try {
		    		session = sessionFactory.openSession();
		    	    session.beginTransaction();
		    	    
		    	    session.evict(screening);
		    	    screening.setScreeningDate(dates);

		    	    MovieScreening mergedScreening = (MovieScreening) session.merge(screening);
		            session.getTransaction().commit();
		     }
		    catch (HibernateException e) {
		         e.printStackTrace();
		         session.getTransaction().rollback();
		        }
			 session.close();
			 
			 try {
		    		session = sessionFactory.openSession();
	                ArrayList<MovieScreening> datesUpdated = getAllDates(screening.getMovie().getEngName());
					client.sendToClient(new AllocatedObject(datesUpdated, "#receiveScreenings"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
	
	public static SessionFactory getSessionFactory() throws HibernateException 
	{
        Configuration configuration =new Configuration();
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(MovieScreening.class);
		configuration.addAnnotatedClass(Cinema.class);
		configuration.addAnnotatedClass(Seat.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Hall.class);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		return configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static <T> List<T> getAll(Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
    } 
	
	private static ArrayList<Movie> getAllMovies() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		ArrayList<Movie> data = (ArrayList<Movie>) session.createQuery(query).getResultList();
		return data;
	}
	
	private static ArrayList<MovieScreening> getAllDates(String movie_name) throws Exception {
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<MovieScreening> query = builder.createQuery(MovieScreening.class);
		query.from(MovieScreening.class);
		ArrayList<MovieScreening> data = (ArrayList<MovieScreening>) session.createQuery(query).getResultList();
		ArrayList<MovieScreening> dates = new ArrayList<MovieScreening>();
		for(MovieScreening screening: data)
		{
			if (screening.getMovie().getEngName().equals(movie_name))
			{
				dates.add(screening);
			}
		}
		System.out.print(dates);
		return dates;
	}
		
	private static void build() throws Exception 
	{
        Movie movie1=new Movie("Minions","מיניונים",null,"Chris,Janet","The tiny yellow creatures venture into the real world and face unexpected challenges",40,"Pierre Coffin,jon Hamm ,Allison Janney"); 
		Movie movie2=new Movie("Black Panther","פנתר שחור",null,"Kevin Feige","after the events of \"Captain America: Civil War,\" returns home to the isolated, technologically advanced African nation of Wakanda to take his place as King.",35,"Chadwick Aaron Boseman");
		Movie movie3=new Movie("The Godfather","הסנדק", null,"Albert S. Ruddy","contrasting the life of Corleone father and son ",20,"Al Pacino"); 
		Movie movie4=new Movie("Lucifer","השטן", null,"Ildy Modrovich"," As the Devil, Lucifer tires of the millennia he's spent being the Lord of Hell, punishing people. ",80,"Tom Ellis"); 
		Movie movie5=new Movie("inceptio","הַתחָלָה", null,"Christopher Nolan","Dom Cobb (Leonardo DiCaprio) is a thief with the rare ability to enter people's dreams and steal their secrets from their subconscious",20,"Leonardo DiCaprio"); 
		session.save(movie5);
		session.save(movie4);
		session.save(movie3);
	    session.save(movie2);
	    session.save(movie1);
	    session.flush(); 	    
		ArrayList<Movie> Movies=new ArrayList();
		ArrayList<Hall> Halls=new ArrayList();
		ArrayList<Seat> Seats=new ArrayList();
		Movies.add(movie1);
		Movies.add(movie2);
		Movies.add(movie3);
		Movies.add(movie4);
		Movies.add(movie5);
		Seat seat = new Seat(1, false);
		Seats.add(seat);
		session.save(seat);
		Hall hall=new Hall(1,100,Seats);
		session.save(hall);
		Cinema C1= new Cinema("Haifa",Halls,Movies);
		session.save(C1);
		session.flush(); 
	    MovieScreening screening1 = new MovieScreening(movie1, "04/03/2022 15:21" , C1, hall);
	    MovieScreening screening6 = new MovieScreening(movie5, "04/03/2022 15:21" , C1, hall);
	    MovieScreening screening2 = new MovieScreening(movie2, "30/08/2021 18:00" , C1, hall);
	    MovieScreening screening3 = new MovieScreening(movie1, "23/02/2021 22:14" , C1, hall);
	    MovieScreening screening4 = new MovieScreening(movie3, "19/12/2021 12:00" , C1, hall);
	    MovieScreening screening5 = new MovieScreening(movie4, "07/09/2021 00:00" , C1, hall);
	    session.save(screening1);
	    session.save(screening2);
	    session.save(screening3);
	    session.save(screening4);
	    session.save(screening5);
	    session.save(screening6);
		session.flush();  
	}
	public static void main(String[] args ) throws IOException
    {
        
    }
    
    public static void init( ) {
    	
    	try {
    		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    		session = sessionFactory.openSession();
    		session.beginTransaction();
    	    build();      //building up mock data
    	    session.getTransaction().commit();            

    	    } 
    	catch (Exception e) {
    		if (session != null) {
    			session.getTransaction().rollback();
    		}
    		e.printStackTrace();
    	   
    	} finally{
    		if (session != null)
    		{
    			session.close();
    		}
    	   }
    	}
}
    
    