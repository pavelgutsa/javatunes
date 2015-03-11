package org.javatunes.ejb.interfaces;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import javax.ejb.Local;

import org.javatunes.model.CatalogItem;
import org.javatunes.model.MerchandiseItem;

@Local
public interface Catalog {
   public Collection<CatalogItem> findByKeyword(String keyword, MerchandiseItem.MerchandiseType merchandiseType) 
		   throws InterruptedException, ExecutionException;
}
