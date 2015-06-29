package org.javatunes.model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.javatunes.model.Book;

//@RunWith(Arquillian.class)
public class BookTest {

	// ======================================
	// = Attributes =
	// ======================================

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("javatunes-test");;
	private EntityManager em;
	private EntityTransaction tx;
	private static ValidatorFactory vf;
	private static Validator validator;

	// ======================================
	// = Lifecycle Methods =
	// ======================================

	@BeforeClass
	public static void initEntityManager() throws Exception {
		//emf = Persistence.createEntityManagerFactory("javatunes-test");
		vf = Validation.buildDefaultValidatorFactory();
		validator = vf.getValidator();
	}
	
	@AfterClass
	public static void close() {
		vf.close();
		emf.close();
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
		shouldRaiseConstraintViolationCauseNullTitle();
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
		
		Set<ConstraintViolation<Book>> violations = validator
				.validate(book);
		assertEquals(0, violations.size());
		
		System.out.println("shouldCreateH2G2Book validation succeeded");

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
	
	public void shouldRaiseConstraintViolationCauseNullTitle() {

		Book book = new Book(null, "Null title, should fail", 12.5F,
				"1-84023-742-2", 354, false);
		
		Set<ConstraintViolation<Book>> violations = validator
				.validate(book);
		
		assertEquals(1, violations.size());
		
		assertEquals("may not be null", violations.iterator().next()
				.getMessage());
		assertEquals(null, violations.iterator().next()
				.getInvalidValue());
		assertEquals("{javax.validation.constraints.NotNull.message}", violations
				.iterator().next().getMessageTemplate());
		
		System.out.println("shouldRaiseConstraintViolationCauseNullTitle succeeded");
	}
}
