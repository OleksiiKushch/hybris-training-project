package com.epam.training.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;

public class Car extends GeneratedCar
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger( Car.class.getName() );
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem( ctx, type, allAttributes );
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	@Override
	public EnumerationValue getColor(SessionContext ctx) {
		return null;
	}

	@Override
	public void setColor(SessionContext ctx, EnumerationValue value) {

	}

	@Override
	public Engine getEngine(SessionContext ctx) {
		return null;
	}

	@Override
	public void setEngine(SessionContext ctx, Engine value) {

	}

	@Override
	public String getModel(SessionContext ctx) {
		return null;
	}

	@Override
	public void setModel(SessionContext ctx, String value) {

	}
}
