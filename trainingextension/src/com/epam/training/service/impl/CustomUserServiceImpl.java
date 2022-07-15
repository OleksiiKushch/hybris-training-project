package com.epam.training.service.impl;

import com.epam.training.service.CustomUserService;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.user.impl.DefaultUserService;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class CustomUserServiceImpl extends DefaultUserService implements CustomUserService {
    public static final String GET_USERS_WITHOUT_ADDRESS = "SELECT {" + UserModel.PK + "} FROM {" +
            UserModel._TYPECODE + " AS u LEFT JOIN " + AddressModel._TYPECODE + " AS a " +
            "ON {u:" + UserModel.PK + "} = {a:" + AddressModel.OWNER + "} } WHERE {a:" + AddressModel.PK + "} IS NULL";

    private FlexibleSearchService flexibleSearchService;

    @Required
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public List<UserModel> getAllUsersWithoutAddress() {
        FlexibleSearchQuery query = new FlexibleSearchQuery(GET_USERS_WITHOUT_ADDRESS);
        SearchResult<UserModel> result = flexibleSearchService.search(query);
        return result.getResult();
    }
}