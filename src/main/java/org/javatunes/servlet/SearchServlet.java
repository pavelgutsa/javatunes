package org.javatunes.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javatunes.ejb.interfaces.Catalog;
import org.javatunes.ejb.interfaces.SearchUtility;
import org.javatunes.model.CatalogItem;
import org.javatunes.model.MerchandiseItem;
import java.util.logging.Logger;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(name = "SearchServlet", urlPatterns = { "/search" })
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// Storage for init parameter
	private static String c_allowEmptyKeyword;

	// Inject Catalog EJB
	@Inject
	Catalog cat;
	
	@Inject
	SearchUtility searchUtil;

	// Retrieve value of init parameter
	public void init() throws ServletException {
		c_allowEmptyKeyword = this.getInitParameter("allowEmptyKeyword");
		searchUtil.init();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Read parameter from HTML form
		String keyword = request.getParameter("keyword");
		logger.info("Searching catalog for " + keyword);

		// Use the init parameter to determine if we
		// should allow an empty string keyword search
		if (keyword == null
				|| (keyword.trim().equals("") && c_allowEmptyKeyword
						.equals("false"))) {
			this.getServletContext().getRequestDispatcher("/")
					.forward(request, response);
		} else {
			// Do the search, store results in request with name "results"
			if (cat != null) {
				String searchKeyword = keyword.trim();
				// TODO: Search on SearchKeyword using findByKeyword
				Collection<CatalogItem> results = null;
				try {
					results = cat
							.findByKeyword(searchKeyword, MerchandiseItem.MerchandiseType.CD);
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("results", searchUtil.trim(results));

				// Store the size of the search results in request with name
				// "size"
				request.setAttribute("size", new Integer(results.size()));
			}

			// Forward to /jsp/searchResults.jsp for display
			this.getServletContext()
					.getRequestDispatcher("/jsp/searchResults.jsp")
					.forward(request, response);
		}
	}
}
