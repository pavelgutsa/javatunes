package org.javatunes.ejb.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Singleton;

import org.javatunes.ejb.interfaces.PriceList;
import org.javatunes.model.ItemPrice;
import java.util.logging.Logger;

/**
 * Session Bean implementation class CatalogBean
 */
@Singleton
public class PriceListBean implements PriceList {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	// Price list
	private static Map<Long, ItemPrice> price_list = new HashMap<Long, ItemPrice>();
	
	@Override
	@Asynchronous
	public Future<ItemPrice> getPrice(Long id) {
		// Delegate to SearchUtility findById method
		return new AsyncResult<ItemPrice>(price_list.get(id));
	}

	@Override
	@Asynchronous
	public void setPrice(Long id, ItemPrice price) {
		price_list.put(id, price);		
	}
	
	@PostConstruct
	void postConstruct() {
		System.out.println("PriceListBean.PostConstruct@"+this);
	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("PriceListBean.PreDestroy"+this);
	}
}
