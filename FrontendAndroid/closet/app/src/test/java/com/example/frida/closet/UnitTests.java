package com.example.frida.closet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class UnitTests {
    private static final String FAKE_STRING = "closet";

    @Mock
    Context mMockContext;

    @Mock
    ToiletDataManager toiletDataManager;


   /* @Test
    public void readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mMockContext.getString(R.string.app_name))
                .thenReturn(FAKE_STRING);
        MapsActivity myObjectUnderTest = new MapsActivity();

        // ...when the string is returned from the object under test...
        String result = "closet";

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));
    }

    @Test
    public void addinNewToilet_withoutOpeningHours_returnsTrue(){
        //{"name":"blaxyz","latitude":47.5269052593013,"longitude":19.013327434659004,
        // "openingHours":[{"day":"Monday","openingHour":"13:12","closingHour":"13:12"}]};
        //String bla = toiletDataManager.newPostAsync("blaxyz", "47.5269052593013", "19.013327434659004", "");
        //String bla = toiletDataManager.getResult();
        when(toiletDataManager.newPostAsync("blaxyz", "47.5269052593013", "19.013327434659004", "")).thenReturn(FAKE_STRING);
        assertThat("200", is("200"));
    }*/
}
