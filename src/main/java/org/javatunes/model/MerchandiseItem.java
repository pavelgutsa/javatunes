package org.javatunes.model;

import java.util.Random;

public abstract class MerchandiseItem
implements java.io.Serializable
{
   private static final long serialVersionUID = 1L;
   
   public enum MerchandiseType {
		CD, BOOK;
   }
   
   private Long merchandiseId;
   private MerchandiseType merchandiseType;

   public MerchandiseItem(MerchandiseType merchandiseType)
   {
	   this.merchandiseType = merchandiseType;
	   this.merchandiseId = new Random().nextLong();
   }

   public Long getId() { return merchandiseId; }
   public MerchandiseType getMerchandiseType() { return merchandiseType; }
   
   // Abstract method to match keyword search
   public abstract boolean matchKeyword(String keyword);

   // override Object.equals
   public boolean equals(Object compare)
   {
      boolean result = false;
      MerchandiseItem other = null;

      if (compare instanceof MerchandiseItem)
      {
         // cast to MerchandiseItem
         other = (MerchandiseItem) compare;

         // if all the ids are equal, the objects are equal
         result = other.getId().equals(this.getId()) ;
      }
      return result;
   }
}
