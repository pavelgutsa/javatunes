/*
 * This code is sample code, provided as-is, and we make no
 * warranties as to its correctness or suitability for
 * any purpose.
 *
 * We hope that it's useful to you.  Enjoy.
 * Copyright 2006-14 LearningPatterns Inc.
 */

package org.javatunes.servlet;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javatunes.ejb.interfaces.Inventory;
import org.javatunes.ejb.interfaces.ShoppingCart;
import org.javatunes.model.InventoryItem;
import java.util.logging.Logger;

@WebServlet(name = "CartServlet", urlPatterns = { "/cart" })
public class CartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// Inject Inventory EJB
	@Inject
	Inventory inventory;

	// Inject ShoppingCartEJB
	@Inject
	ShoppingCart cart;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String checkoutParam = request.getParameter("checkout");
		if (checkoutParam != null) {
			checkout(request, response);
			return;
		}

		// Read the parameter from the "Add to Cart" link
		String itemID = request.getParameter("itemID");

		// Shopping cart content management (add item if not already in cart)
		if (itemID == null) {
			request.setAttribute("error", "Item ID is missing");
		} else {
			Long id = Long.decode(itemID);
			InventoryItem item = inventory.findById(id);

			if (item.getItemQuantity() < 1) {
				request.setAttribute("error", "No more items available");
			} else {
				try {
					cart.add(item.getMerchandiseItem());
				} catch (InterruptedException | ExecutionException e) {
					request.setAttribute("error", e.getMessage());
					e.printStackTrace();
				}
			}
		}

		// forward to /jsp/cartDisplay.jsp
		this.getServletContext().getRequestDispatcher("/jsp/cartDisplay.jsp")
				.forward(request, response);
	}

	private void checkout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		// TODO: Note this call
		session.invalidate();

		// forward to Home Page
		this.getServletContext().getRequestDispatcher("/")
				.forward(request, response);
	}
}
