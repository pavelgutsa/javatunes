package org.javatunes.ejb.interfaces;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import javax.ejb.Local;

import org.javatunes.model.MerchandiseItem;
import org.javatunes.model.ShoppingCartItem;

@Local
public interface ShoppingCart {
   public boolean contains(Long id);
   public void add(MerchandiseItem merchandiseItem) 
		   throws InterruptedException, ExecutionException;
   public Collection<ShoppingCartItem> getContents();
   public void checkout();
}
