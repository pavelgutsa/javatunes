package org.javatunes.model;

public class InventoryItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private MerchandiseItem merchandiseItem;
	private long itemQuantity;

	public MerchandiseItem getMerchandiseItem() {
		return merchandiseItem;
	}

	public long getItemQuantity() {
		return itemQuantity;
	}
	
	public void setItemQuantity(long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public InventoryItem(MerchandiseItem merchandiseItem, long itemQuantity) {
		this.merchandiseItem = merchandiseItem;
		this.itemQuantity = itemQuantity;
	}

	// override Object.equals
	public boolean equals(Object compare) {
		boolean result = false;
		InventoryItem other = null;

		if (compare instanceof InventoryItem) {
			// cast to MusicItem
			other = (InventoryItem) compare;

			// if all the ids are equal, the objects are equal
			result = other.getMerchandiseItem().equals(this.getMerchandiseItem());
		}
		return result;
	}
}
