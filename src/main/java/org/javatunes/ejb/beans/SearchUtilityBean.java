/*
 * This code is sample code, provided as-is, and we make no
 * warranties as to its correctness or suitability for
 * any purpose.
 *
 * We hope that it's useful to you.  Enjoy.
 * Copyright 2006-12 LearningPatterns Inc.
 */

package org.javatunes.ejb.beans;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.javatunes.ejb.interfaces.Inventory;
import org.javatunes.ejb.interfaces.PriceList;
import org.javatunes.ejb.interfaces.SearchUtility;
import org.javatunes.model.ItemPrice;
import org.javatunes.model.MusicItem;
import java.util.logging.Logger;

/**
 * This class is a stateless session bean that emulates the search of some
 * catalog. We do this so we don't need a database at this early stage of the
 * course. This will be replaced with a persistent entity that goes to a
 * database in a later lab.
 */

@Singleton
@DependsOn( { "InventoryBean", "PriceListBean"} )
@Local(SearchUtility.class)
public class SearchUtilityBean implements SearchUtility {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Inject
	Inventory inventory;

	@Inject
	PriceList prices;

	@Resource
	private SessionContext m_ctx;

	@Resource(name = "maxSearchResults")
	int maxSearchResults;
	
	public void init() {
		logger.info("Init");
	}

	@PostConstruct
	void postConstruct() {
		logger.info("SearchUtilityBean.PostConstruct@" + this);

		add(10, "Diva", "Annie Lennox", "1992-01-04", "17.97", "13.99");
		add(10, "Dream of the Blue Turtles", "Sting", "1985-02-05", "17.97",
				"14.99");
		add(10, "Trouble is...", "Kenny Wayne Shepherd Band", "1997-08-08",
				"17.97", "14.99");
		add(10, "Lie to Me", "Jonny Lang", "1997-08-26", "17.97", "14.99");
		add(10, "Little Earthquakes", "Tori Amos", "1992-01-18", "17.97",
				"14.99");
		add(10, "Seal", "Seal", "1991-08-18", "17.97", "14.99");
		add(10, "Ian Moore", "Ian Moore", "1993-12-05", "9.97", "9.97");
		add(10, "So Much for the Afterglow", "Everclear", "1997-01-19",
				"16.97", "13.99");
		add(10, "Surfacing", "Sarah McLachlan", "1997-12-04", "17.97", "13.99");
		add(10, "Hysteria", "Def Leppard", "1987-06-20", "17.97", "14.99");
		add(10, "A Life of Saturdays", "Dexter Freebish", "2000-12-06",
				"16.97", "12.99");
		add(10, "Human Clay", "Creed", "1999-10-21", "18.97", "13.28");
		add(10, "My, I'm Large", "Bobs", "1987-02-20", "11.97", "11.97");
		add(10, "So", "Peter Gabriel", "1986-10-03", "17.97", "13.99");
		add(10, "Big Ones", "Aerosmith", "1994-05-08", "18.97", "14.99");
		add(10, "90125", "Yes", "1983-10-16", "11.97", "11.97");
		add(10, "1984", "Van Halen", "1984-08-19", "11.97", "11.97");
		add(10, "Escape", "Journey", "1981-02-25", "11.97", "11.97");
	}

	public <T> Collection<T> trim(Collection<T> results) {
		logger.info("trimming to " + maxSearchResults);
		if ((maxSearchResults == 0) || (results.size() < maxSearchResults)) {
			return results;
		} else {
			List<T> trimmed = new ArrayList<T>(maxSearchResults);
			int i = 0;
			for (T cur : results) {
				trimmed.add(cur);
				if (++i == maxSearchResults) {
					break;
				}
			}
			return trimmed;
		}
	}

	private void add(long itemQuantity, String title, String artist,
			String releaseDate, String listPrice, String price) {
		// Add to inventory
		MusicItem musicItem = new MusicItem(title, artist,
				Date.valueOf(releaseDate));
		inventory.add(musicItem, itemQuantity);

		// Add to price list
		prices.setPrice(musicItem.getId(), new ItemPrice(new BigDecimal(
				listPrice), new BigDecimal(price)));
	}
}
