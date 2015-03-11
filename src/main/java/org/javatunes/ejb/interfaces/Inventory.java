package org.javatunes.ejb.interfaces;

import java.util.Collection;

import javax.ejb.Local;

import org.javatunes.model.InventoryItem;
import org.javatunes.model.MerchandiseItem;

@Local
public interface Inventory {

	public void add(MerchandiseItem merchandiseItem, long itemQuantity);
	public Collection<InventoryItem> findByKeyword(String keyword, MerchandiseItem.MerchandiseType merchandiseType);
	public InventoryItem findById(Long id);
}
