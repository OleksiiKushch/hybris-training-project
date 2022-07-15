package com.epam.training.service.impl;

import com.epam.training.service.CustomUserService;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static de.hybris.platform.testframework.Assert.assertEquals;
import static org.mockito.Mockito.*;

@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class CustomUserServiceImplTest {
    @Mock
    private FlexibleSearchService flexibleSearchService;

    @InjectMocks
    private CustomUserService userService = new CustomUserServiceImpl();

    @Test
    public void testGetAllUsersWithoutAddress() {
        UserModel expect1 = mock(UserModel.class);
        UserModel expect2 = mock(UserModel.class);
        SearchResult searchResult = mock(SearchResult.class);
        when(searchResult.getResult()).thenReturn(Arrays.asList(expect1, expect2));
        FlexibleSearchQuery query = new FlexibleSearchQuery(CustomUserServiceImpl.GET_USERS_WITHOUT_ADDRESS);
        when(flexibleSearchService.search(query)).thenReturn(searchResult);

        List<UserModel> result = userService.getAllUsersWithoutAddress();

        assertEquals(2, result.size());
        assertEquals(expect1, result.get(0));
        assertEquals(expect2, result.get(1));
        verify(flexibleSearchService).search(query);
    }
}