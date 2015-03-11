package org.javatunes.ejb.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.javatunes.ejb.interfaces.PriceList;
import org.javatunes.ejb.interfaces.ShoppingCart;
import org.javatunes.model.ItemPrice;
import org.javatunes.model.MerchandiseItem;
import org.javatunes.model.ShoppingCartItem;
import java.util.logging.Logger;

/**
 * Session Bean implementation class CatalogBean
 */
@Stateful
@SessionScoped
@LocalBean
@Named("ShoppingCart")
public class ShoppingCartBean implements ShoppingCart {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Inject
	private PriceList prices;
	
	// Shopping cart
	private static Map<Long, ShoppingCartItem> contents = new HashMap<Long, ShoppingCartItem>();

	@Override
	public boolean contains(Long id) {
		return contents.containsKey(id);
	}

	@Override
	public void add(MerchandiseItem merchandiseItem) 
			throws InterruptedException, ExecutionException 
	{
		if (!contains(merchandiseItem.getId())) {
			Future<ItemPrice> res = prices.getPrice(merchandiseItem.getId());			
			logger.info("Shopping cart does not contain item "
					+ merchandiseItem.getId());
			ItemPrice itemPrice = res.get(); 
			contents.put(new Long(merchandiseItem.getId()), new ShoppingCartItem(
					merchandiseItem, 1, itemPrice));
		} else {
			ShoppingCartItem shoppingCartItem = contents.get(merchandiseItem
					.getId());			
			logger.info("Shopping cart already contains "
					+ shoppingCartItem.getItemQuantity() + " items "
					+ merchandiseItem.getId());
			shoppingCartItem
					.setItemQuantity(shoppingCartItem.getItemQuantity() + 1);
			logger.info("Shopping cart updated with "
					+ shoppingCartItem.getItemQuantity() + " items "
					+ merchandiseItem.getId() + " quantity");
		}
	}

	@Override
	public Collection<ShoppingCartItem> getContents() {
		// TODO Auto-generated method stub
		return contents.values();
	}

	@Override
	@Remove
	public void checkout() {
		System.out.println("ShoppingCartBean checkout called");
	}

	// Lifecycle interceptors
	@PostConstruct
	public void noInitToDo() {
		System.out.println("ShoppingCartBean PostConstruct method called");
	}

	@PreDestroy
	public void noCleanupToDo() {
		System.out.println("ShoppingCartBean PreDestroy method called");
	}
}
