package com.epam.training.facade.populator;

import com.epam.trainingextension.dto.UserData;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.user.UserModel;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
public class CustomUserPopulatorTest {

    @Test
    public void testPopulate() {
        Date exceptCreationTime = mock(Date.class);
        Date exceptModifiedTime = mock(Date.class);
        UserModel source = Mockito.mock(UserModel.class);
        when(source.getUid()).thenReturn("unicrobix@i.ua");
        when(source.getName()).thenReturn("Alex");
        when(source.getCreationtime()).thenReturn(exceptCreationTime);
        when(source.getModifiedtime()).thenReturn(exceptModifiedTime);

        CustomUserPopulator populator = new CustomUserPopulator();
        UserData target = new UserData();
        populator.populate(source, target);

        assertEquals("unicrobix@i.ua", target.getEmail());
        assertEquals("Alex", target.getFirstName());
        assertEquals(exceptCreationTime, target.getCreationTime());
        assertEquals(exceptModifiedTime, target.getModifiedTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPopulate_ifAnyArgumentIsNull() {
        CustomUserPopulator populator = new CustomUserPopulator();
        populator.populate(null, null);
    }
}