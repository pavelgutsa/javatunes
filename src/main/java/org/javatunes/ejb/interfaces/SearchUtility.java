/*
 * This code is sample code, provided as-is, and we make no
 * warranties as to its correctness or suitability for
 * any purpose.
 *
 * We hope that it's useful to you.  Enjoy.
 * Copyright 2006-14 LearningPatterns Inc.
 */


package org.javatunes.ejb.interfaces;

import java.util.Collection;


public interface SearchUtility {

	public <T> Collection<T> trim(Collection<T> results);
	public void init();
}
