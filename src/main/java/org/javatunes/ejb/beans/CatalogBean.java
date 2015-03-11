package org.javatunes.ejb.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.logging.Logger;

import org.javatunes.ejb.interfaces.Catalog;
import org.javatunes.ejb.interfaces.Inventory;
import org.javatunes.ejb.interfaces.PriceList;
import org.javatunes.model.CatalogItem;
import org.javatunes.model.InventoryItem;
import org.javatunes.model.ItemPrice;
import org.javatunes.model.MerchandiseItem;

/**
 * Session Bean implementation class CatalogBean
 */
@Singleton
@DependsOn({ "InventoryBean", "PriceListBean" })
@LocalBean
@Interceptors({ org.javatunes.interceptors.LoggerInterceptor.class,
		org.javatunes.interceptors.ProfilerInterceptor.class })
public class CatalogBean implements Catalog {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Inject
	private Inventory inventory;

	@Inject
	private PriceList prices;

	public Collection<CatalogItem> findByKeyword(String keyword,
			MerchandiseItem.MerchandiseType merchandiseType) 
					throws InterruptedException, ExecutionException {

		logger.info("findByKeyword - " + keyword);

		// declare return value
		Collection<CatalogItem> result = new ArrayList<CatalogItem>();

		// Get the inventory list
		Collection<InventoryItem> inventoryItems = inventory.findByKeyword(
				keyword, merchandiseType);

		// iterate through the inventory, look for merchandise price
		for (InventoryItem inventoryItem : inventoryItems) {
			Future<ItemPrice> res = prices.getPrice(inventoryItem
					.getMerchandiseItem().getId());
			ItemPrice itemPrice = res.get(); 
			if (itemPrice != null) {
				result.add(new CatalogItem(inventoryItem, itemPrice));
			}
		}

		return result;
	}

	@PostConstruct
	void postConstruct() {
		System.out.println("CatalogBean.PostConstruct@" + this);
	}

	@PreDestroy
	void preDestroy() {
		System.out.println("CatalogBean.PreDestroy" + this);
	}
}
