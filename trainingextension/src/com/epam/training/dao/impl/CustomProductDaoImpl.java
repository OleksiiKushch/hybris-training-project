package com.epam.training.dao.impl;

import com.epam.training.constants.TrainingextensionConstants;
import com.epam.training.dao.CustomProductDao;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.daos.impl.DefaultProductDao;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomProductDaoImpl extends DefaultProductDao implements CustomProductDao {
    private static final String MSG_PRODUCT_CODE_MUST_NOT_BE_NULL = "Product code must not be null!";
    private static final String MSG_CATALOG_VERSION_MUST_NOT_BE_NULL = "CatalogVersion must not be null!";
    private static final String MSG_PRODUCT_MODEL_NOT_FOUND_BY_CODE_AND_CATALOG_VERSION = "Product model not found with code: %s " +
            "and catalog version: %s%n";

    private static final String SELECT_PRODUCTS_BY_CODE_AND_CATALOG_VERSION =
            "SELECT {p:" + ProductModel.PK + "} FROM {" +
            ProductModel._TYPECODE + " AS p JOIN " + CatalogVersionModel._TYPECODE + " AS cv " +
            "ON {p:" + ProductModel.CATALOGVERSION + "} = {cv:" + CatalogVersionModel.PK + "}" +
            "} WHERE {p:" + ProductModel.CODE       + "} = ?" + ProductModel.CODE + " AND " +
            "{cv:" + CatalogVersionModel.VERSION    + "} = ?" + CatalogVersionModel.VERSION;

    public CustomProductDaoImpl(String typecode) {
        super(typecode);
    }

    @Override
    public String getProductPictureUrl(String productCode, String catalogVersion) {
        ServicesUtil.validateParameterNotNull(productCode, MSG_PRODUCT_CODE_MUST_NOT_BE_NULL);
        ServicesUtil.validateParameterNotNull(catalogVersion, MSG_CATALOG_VERSION_MUST_NOT_BE_NULL);

        Map<String, Object> params = new HashMap<>();
        params.put(ProductModel.CODE, productCode);
        params.put(CatalogVersionModel.VERSION, catalogVersion);

        FlexibleSearchQuery query = new FlexibleSearchQuery(SELECT_PRODUCTS_BY_CODE_AND_CATALOG_VERSION, params);

        ProductModel productModel = getFlexibleSearchService().searchUnique(query);
        if (Objects.isNull(productModel)) {
            throw new ModelNotFoundException(String.format(MSG_PRODUCT_MODEL_NOT_FOUND_BY_CODE_AND_CATALOG_VERSION,
                    productCode, catalogVersion));
        }

        MediaModel mediaModel = productModel.getPicture();
        return Objects.nonNull(mediaModel) ? mediaModel.getURL() : StringUtils.EMPTY;
    }
}
