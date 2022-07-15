package com.epam.training.decorator;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ProductCodeCSVCellDecoratorTest extends TestCase {
    ProductCodeCSVCellDecorator productCodeCSVCellDecorator;
    Map<Integer, String> testData;

    public void setUp() {
        productCodeCSVCellDecorator = new ProductCodeCSVCellDecorator();
        testData = new HashMap<>();
    }

    public void testIfValueIsNormal_shouldReturnParsedValue() {
        testData.put(1, "Test product");
        assertEquals("testproduct", productCodeCSVCellDecorator.decorate(1, testData));
    }

    public void testIfValueIsNull_shouldReturnNull() {
        testData.put(1, null);
        assertNull(productCodeCSVCellDecorator.decorate(1, testData));
    }
}