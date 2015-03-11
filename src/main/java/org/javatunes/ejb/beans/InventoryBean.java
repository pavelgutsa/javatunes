package org.javatunes.ejb.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import org.javatunes.ejb.interfaces.Inventory;
import org.javatunes.model.InventoryItem;
import org.javatunes.model.MerchandiseItem;
import java.util.logging.Logger;

/**
 * Session Bean implementation class InventoryBean
 */
@Singleton
@LocalBean
public class InventoryBean implements Inventory {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	// Inventory
	private static Map<Long, InventoryItem> inventory = new HashMap<Long, InventoryItem>();

	@Interceptors({ org.javatunes.interceptors.LoggerInterceptor.class,
		org.javatunes.interceptors.ProfilerInterceptor.class })
	public InventoryItem findById(Long merchandiseId) {
		logger.info("findById - " + merchandiseId);

		return inventory.get(merchandiseId);
	}
	
	@Interceptors({ org.javatunes.interceptors.LoggerInterceptor.class,
		org.javatunes.interceptors.ProfilerInterceptor.class })
	public Collection<InventoryItem> findByKeyword(String keyword,
			MerchandiseItem.MerchandiseType merchandiseType) {
		
		logger.info("findByKeyword - " + keyword);
		
		// declare return value
		Collection<InventoryItem> result = new ArrayList<InventoryItem>();
		
		// iterate through the catalog, looking for a keyword match
		for (InventoryItem inventoryItem : inventory.values()) {
			if (inventoryItem.getMerchandiseItem().getMerchandiseType().equals(merchandiseType) && 
				inventoryItem.getMerchandiseItem().matchKeyword(keyword) &&
				inventoryItem.getItemQuantity() > 0) 
			{
				result.add(inventoryItem);
			}
		}

		return result;
	}

	// Add to inventory
	public void add(MerchandiseItem merchandiseItem, long itemQuantity) {
		inventory.put(
				new Long(merchandiseItem.getId()), 
				new InventoryItem(merchandiseItem, itemQuantity));
	}

	@PostConstruct
	void postConstruct() {
		System.out.println("InventoryBean.PostConstruct@" + this);
	}

	@PreDestroy
	void preDestroy() {
		System.out.println("InventoryBean.PreDestroy" + this);
	}
}
