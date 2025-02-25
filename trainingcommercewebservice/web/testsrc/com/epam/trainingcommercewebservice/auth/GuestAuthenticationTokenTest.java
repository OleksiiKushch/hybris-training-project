/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package com.epam.trainingcommercewebservice.auth;

import de.hybris.bootstrap.annotations.UnitTest;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;


@UnitTest
public class GuestAuthenticationTokenTest
{
	/**
	 * Fortify issue test
	 */
	@Test
	public void shouldReturnFalseWhenComparedToNull()
	{
		final GuestAuthenticationToken token = new GuestAuthenticationToken("email", Collections.emptyList());
		Assert.assertFalse(token.equals(null));
	}

}
