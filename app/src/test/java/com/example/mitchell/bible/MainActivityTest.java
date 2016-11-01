package com.example.mitchell.bible;

import android.support.annotation.NonNull;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mitchell.bible.feature.MainActivity;
import com.example.mitchell.bible.injection.ApplicationComponent;
import com.example.mitchell.bible.injection.ApplicationModule;
import com.example.mitchell.bible.service.VersionService;
import com.example.mitchell.bible.service.model.VersionModel;
import com.example.mitchell.bible.service.model.VersionResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by mitchell on 10/4/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = LOLLIPOP, application = TestBibleApp.class)
public class MainActivityTest {

    @Mock private VersionService versionService;
    @Mock private Observable<VersionResponse> callWrapper;
    @Captor private ArgumentCaptor<Callback<VersionResponse>> callbackArgumentCaptor;

    @Rule public DaggerMockRule<ApplicationComponent> daggerMockRule = new DaggerMockRule<>(ApplicationComponent.class, new ApplicationModule(((TestBibleApp) RuntimeEnvironment.application)))
            .set(new DaggerMockRule.ComponentSetter<ApplicationComponent>() {
                @Override
                public void setComponent(ApplicationComponent applicationComponent) {
                    ((TestBibleApp) RuntimeEnvironment.application).setComponent(applicationComponent);
                }
            });
    private MainActivity testObject;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageButton versionLogoButton;

    @Before
    public void setUp() throws Exception {
        when(versionService.versions(anyString())).thenReturn(callWrapper);
        testObject = Robolectric.buildActivity(MainActivity.class).create().get();

        nameTextView = (TextView) testObject.findViewById(R.id.titleTextView);
        descriptionTextView = (TextView) testObject.findViewById(R.id.descriptionTextView);
        versionLogoButton = (ImageButton) testObject.findViewById(R.id.lebButton);



    }

    @Test
    public void nameAndDescriptionSetFromService() throws Exception {
        verify(callWrapper).enqueue(callbackArgumentCaptor.capture());

        Callback<VersionResponse> callback = callbackArgumentCaptor.getValue();

        String expectedDescription = "expectedDescription";
        String expectedName = "expectedName";
//        String extension = ".gif";
//        String expectedPath = "expectedPath";
        VersionResponse VersionResponse = buildCharacter(expectedDescription, expectedName);

        Response response = Response.success(VersionResponse);
        callback.onResponse(null, response);


        assertEquals(expectedName, nameTextView.getText().toString());
        assertEquals(expectedDescription, descriptionTextView.getText().toString());
    }

    @Test
    public void imageLoadedIntoView() throws Exception {
        verify(callWrapper).enqueue(callbackArgumentCaptor.capture());

        Callback<VersionResponse> callback = callbackArgumentCaptor.getValue();

//        String extension = ".gif";
//        String expectedPath = "expectedPath";
        VersionResponse VersionResponse = buildCharacter("description", "name");

        Response response = Response.success(VersionResponse);
        callback.onResponse(null, response);

        //verify(imageLoader).loadImageIntoView(expectedPath + "." + extension, versionLogoButton);
    }

    @Test
    public void unexpectedFailureShowsToast() throws Exception {
        verify(callWrapper).enqueue(callbackArgumentCaptor.capture());

        Callback<VersionResponse> callback = callbackArgumentCaptor.getValue();

        callback.onFailure(null, null);

        assertEquals("Unexpected Error", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void serviceFailureShowsToast() throws Exception {
        verify(callWrapper).enqueue(callbackArgumentCaptor.capture());
        Callback<VersionResponse> callback = callbackArgumentCaptor.getValue();

        Response response = Response.error(404, mock(ResponseBody.class));
        callback.onResponse(null, response);

        assertEquals("Error Loading Data", ShadowToast.getTextOfLatestToast());
    }

    @NonNull
    private VersionResponse buildCharacter(String expectedDescription, String expectedName) {
        List<VersionModel> versions = new ArrayList<>();
        VersionModel version = new VersionModel("leb",expectedName,"leb",null, null, null, null, expectedDescription, null, null, null);
        versions.add(version);
        VersionResponse VersionResponse = new VersionResponse(versions);
        return VersionResponse;
    }
}