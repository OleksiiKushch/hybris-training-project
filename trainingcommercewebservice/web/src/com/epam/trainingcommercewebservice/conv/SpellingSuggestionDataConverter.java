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
package com.epam.trainingcommercewebservice.conv;


import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.SpellingSuggestionData;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Converts SpellingSuggestionData to simpler response syntax.
 */
public class SpellingSuggestionDataConverter implements Converter
{
	@Override
	public void marshal(final Object object, final HierarchicalStreamWriter writer, final MarshallingContext marshallingContext)
	{
		final SpellingSuggestionData<SearchStateData> spellingSuggestionData = (SpellingSuggestionData<SearchStateData>) object;
		if (writer instanceof JsonWriter)
		{
			((JsonWriter) writer).startNode("query", String.class);
			((JsonWriter) writer).setValue(spellingSuggestionData.getQuery().getQuery().getValue());
			((JsonWriter) writer).endNode();
			((JsonWriter) writer).startNode("suggestion", String.class);
			((JsonWriter) writer).setValue(spellingSuggestionData.getSuggestion());
			((JsonWriter) writer).endNode();
		}
		else
		{
			writer.startNode("query");
			writer.setValue(spellingSuggestionData.getQuery().getQuery().getValue());
			writer.endNode();
			writer.startNode("suggestion");
			writer.setValue(spellingSuggestionData.getSuggestion());
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext unmarshallingContext)
	{
		return null;
	}

	@Override
	public boolean canConvert(final Class aClass)
	{
		return aClass.equals(SpellingSuggestionData.class);
	}
}
