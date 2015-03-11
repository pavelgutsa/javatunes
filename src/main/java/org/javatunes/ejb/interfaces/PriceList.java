package org.javatunes.ejb.interfaces;

import java.util.concurrent.Future;

import javax.ejb.Local;

import org.javatunes.model.ItemPrice;

@Local
public interface PriceList {

   public Future<ItemPrice> getPrice(Long id);
   public void setPrice(Long id, ItemPrice price);
}
