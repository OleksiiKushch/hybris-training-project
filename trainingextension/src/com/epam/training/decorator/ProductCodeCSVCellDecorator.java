package com.epam.training.decorator;

import de.hybris.platform.util.CSVCellDecorator;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * make src string attribute lowercase and without spaces
 */
public class ProductCodeCSVCellDecorator implements CSVCellDecorator {
    private static final String REGEX_WHITESPACE = "\\s";

    @Override
    public String decorate( int position, Map<Integer, String> srcLine )
    {
        String parsedValue = srcLine.get(position);
        return Objects.nonNull(parsedValue) ? parsedValue.toLowerCase().replaceAll(REGEX_WHITESPACE, StringUtils.EMPTY) : null;
    }
}
