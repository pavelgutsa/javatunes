package org.javatunes.model;

import java.sql.Date;

public class MusicItem extends MerchandiseItem
implements java.io.Serializable
{
   private static final long serialVersionUID = 1L;

   private String     title;
   private String     artist;
   private Date       releaseDate;

   public MusicItem(String title, String artist, Date releaseDate)
   {
	  super(MerchandiseType.CD);
      this.setTitle(title);
      this.setArtist(artist);
      this.setReleaseDate(releaseDate);
   }

   public String getTitle() { return title; }
   public String getArtist() { return artist; }
   public Date getReleaseDate() { return releaseDate; }

   public void setTitle(String title) { this.title = title; }
   public void setArtist(String artist) { this.artist = artist; }
   public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }
   
   // Keyword search match methos
   @Override
   public boolean matchKeyword(String keyword) {
	   keyword = keyword.toLowerCase();

	   return getTitle().toLowerCase().indexOf(keyword) != -1 || 
			  getArtist().toLowerCase().indexOf(keyword) != -1;
   }

   // override Object.equals
   @Override
   public boolean equals(Object compare)
   {
      boolean result = false;
      MusicItem other = null;

      if (compare instanceof MusicItem)
      {
         // cast to MusicItem
         other = (MusicItem) compare;

         // if all the ids are equal, the objects are equal
         result = other.getId().equals(this.getId()) ;
      }
      return result;
   }
}
