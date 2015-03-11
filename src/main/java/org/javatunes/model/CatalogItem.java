package org.javatunes.model;

public class CatalogItem {

	private InventoryItem inventoryItem;
	private ItemPrice itemPrice;

	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}

	public ItemPrice getItemPrice() {
		return itemPrice;
	}
	
	public CatalogItem(InventoryItem inventoryItem, ItemPrice itemPrice) {
		this.inventoryItem = inventoryItem;
		this.itemPrice = itemPrice;
	}

	// override Object.equals
	public boolean equals(Object compare) {
		boolean result = false;
		CatalogItem other = null;

		if (compare instanceof CatalogItem) {
			// cast to CatalogItem
			other = (CatalogItem) compare;

			// if all the ids are equal, the objects are equal
			result = other.getInventoryItem().equals(this.getInventoryItem());
		}
		return result;
	}
}
