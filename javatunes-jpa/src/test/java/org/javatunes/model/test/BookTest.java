package org.javatunes.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.javatunes.model.Book;
//import javax.validation.ConstraintViolationException;

//@RunWith(Arquillian.class)
public class BookTest {

	// ======================================
	// = Attributes =
	// ======================================

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("javatunes-test");;
	private EntityManager em;
	private EntityTransaction tx;

	// ======================================
	// = Lifecycle Methods =
	// ======================================

	@BeforeClass
	public static void initEntityManager() throws Exception {
		//emf = Persistence.createEntityManagerFactory("javatunes-test");
	}
	
	@Before
	public void initTransaction() throws Exception {
		em = emf.createEntityManager();
		tx = em.getTransaction();
	}
	
	@After
	public void closeEntityManager() throws Exception {
		if (em != null)
			em.close();
	}
		
	// ======================================
	// = Unit tests =
	// ======================================

	@Test
	public void executeBookTest()  throws Exception {
		entityManagerNotNull();
		shouldFindJavaEE7Book();
		shouldCreateH2G2Book();
	}
	
	public void entityManagerNotNull() throws Exception {
		System.out.println("Executing entityManagerNotNull");
		assertNotNull(em);
		System.out.println("entityManagerNotNull success");
	}

	public void shouldFindJavaEE7Book() throws Exception {
		System.out.println("Executing shouldFindJavaEE7Book");
		Book book = em.find(Book.class, 1001L);
		assertEquals("Beginning Java EE 7", book.getTitle());
		System.out.println("shouldFindJavaEE7Book success");
	}

	public void shouldCreateH2G2Book() throws Exception {
		System.out.println("Executing shouldCreateH2G2Book");
		
		// Creates an instance of book
		Book book = new Book("H2G2", "The Hitchhiker's Guide to the Galaxy",
				12.5F, "1-84023-742-2", 354, false);

		// Persists the book to the database
		tx.begin();
		em.persist(book);
		tx.commit();
		assertNotNull("ID should not be null", book.getId());
		
		System.out.println("shouldCreateH2G2Book persisted the book");

		// Retrieves all the books from the database
		List<Book> books = em.createNamedQuery("findBookH2G2", Book.class)
				.getResultList();
		assertEquals(1, books.size());
		book = em.createNamedQuery("findBookH2G2", Book.class)
				.getSingleResult();		
		System.out.println("shouldCreateH2G2Book found a book");
		
		assertEquals("The Hitchhiker's Guide to the Galaxy",
				book.getDescription());
		
		System.out.println("shouldCreateH2G2Book found The Hitchhiker's Guide to the Galaxy book");
	}
	
	/*
	@Test(expected = ConstraintViolationException.class)
	public void shouldRaiseConstraintViolationCauseNullTitle() {

		Book book = new Book(null, "Null title, should fail", 12.5F,
				"1-84023-742-2", 354, false);
		em.persist(book);
	}
	*/	
}
