package org.javatunes.model;

public class ShoppingCartItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private MerchandiseItem merchandiseItem;
	private long itemQuantity;
	private ItemPrice itemPrice;

	public MerchandiseItem getMerchandiseItem() {
		return merchandiseItem;
	}

	public long getItemQuantity() {
		return itemQuantity;
	}
	
	public void setItemQuantity(long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public ItemPrice getItemPrice() {
		return itemPrice;
	}
	
	public ShoppingCartItem(MerchandiseItem merchandiseItem, long itemQuantity, ItemPrice itemPrice) {
		this.merchandiseItem = merchandiseItem;
		this.itemQuantity = itemQuantity;
		this.itemPrice = itemPrice;
	}

	// override Object.equals
	public boolean equals(Object compare) {
		boolean result = false;
		ShoppingCartItem other = null;

		if (compare instanceof ShoppingCartItem) {
			// cast to MusicItem
			other = (ShoppingCartItem) compare;

			// if all the ids are equal, the objects are equal
			result = other.getMerchandiseItem().equals(this.getMerchandiseItem());
		}
		return result;
	}
}
