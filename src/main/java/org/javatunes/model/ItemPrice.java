package org.javatunes.model;

import java.math.BigDecimal;

public class ItemPrice
implements java.io.Serializable
{
   private static final long serialVersionUID = 1L;
   
   // properties
   private BigDecimal listPrice;
   private BigDecimal price;

   public ItemPrice(BigDecimal listPrice, BigDecimal price)
   {
      this.listPrice = listPrice;
      this.price = price;
   }

   public BigDecimal getListPrice() { return listPrice; }
   public BigDecimal getPrice() { return price; }

   // override Object.equals
   public boolean equals(Object compare)
   {
      boolean result = false;
      ItemPrice other = null;

      if (compare instanceof ItemPrice)
      {
         // cast to MusicItem
         other = (ItemPrice) compare;

         // if ids and prices are equal, the objects are equal
         result = other.getPrice().equals(this.getPrice()) && 
        		  other.getListPrice().equals(this.getListPrice());
      }
      return result;
   }
}
