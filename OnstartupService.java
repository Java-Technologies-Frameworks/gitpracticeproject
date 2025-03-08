package androidx.test.rule;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.runner.JUnitCore.runClasses;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ServiceTestRuleTest {

  public static class TestService extends Service {

    private final IBinder binder = new Binder();

    @Override
    public IBinder onBind(Intent intent) {
      return binder;
    }
  }

  public static class ServiceThatCantBeBoundTo extends Service {
    @Override
    public IBinder onBind(Intent intent) {
      // returns null so clients can not bind to the service
      return null;
    }
  }

  public static class ServiceThatIsNotDefinedInManifest extends Service {
    // This service is not declared in the manifest on purpose
    @Override
    public IBinder onBind(Intent intent) {
      // returns null so clients can not bind to the service
      return null;
    }
  }

  public static class TimeoutService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
      SystemClock.sleep(100);
      return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      SystemClock.sleep(100);
      return super.onStartCommand(intent, flags, startId);
    }
  }

  public static class StartedServiceLifecycleTest {
    private static StringBuilder log = new StringBuilder();

    @Rule
    public final ServiceTestRule serviceRule =
        new ServiceTestRule() {

          @Override
          public void beforeService() {
            log.append("beforeService ");
          }

          @Override
          public void startService(@NonNull Intent intent) throws TimeoutException {
            log.append("startService ");
          }

          @Override
          public IBinder bindService(@NonNull Intent intent) throws TimeoutException {
            log.append("bindService ");
            return null;
          }

          @Override
          public void afterService() {
            log.append("afterService ");
          }

          @Override
          void shutdownService() throws TimeoutException {
            log.append("shutdownService ");
          }
        };

    @Before
    public void before() {
      log.append("before ");
    }

    @After
    public void after() {
      log.append("after ");
    }

    @Test
    public void dummyTestToLaunchService() throws TimeoutException {
      log.append("test ");
      serviceRule.startService(new Intent());
      fail("This is a dummy test to start a service");
    }
  }

  @Test
  public void checkLifecycleOfStartedService() {
    Result result = runClasses(StartedServiceLifecycleTest.class);
    assertEquals(1, result.getFailureCount());
    assertThat(
        result.getFailures().get(0).getMessage(), is("This is a dummy test to start a service"));
    assertThat(
        StartedServiceLifecycleTest.log.toString(),
        is("beforeService before test startService after shutdownService afterService "));
  }

