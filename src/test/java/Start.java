import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;

import java.util.List;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

class Start {
    public static void main(String args[]) throws InterruptedException {
//        JUnitCore.runClasses(CalcTest.class, NotepadeTest.class, MesAuth.class, TksoForward.class).getFailures();
//        JUnitCore.runClasses(MesAuth.class).getFailures();
//        JUnitCore.runClasses(TksoForward.class).getFailures();
//        JUnitCore.runClasses(Excel.class).getFailures();

        // jUnit5
        final LauncherDiscoveryRequest request =
                LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectClass(TksoForward.class))
                        .build();

        final Launcher launcher = LauncherFactory.create();
        final SummaryGeneratingListener listener = new SummaryGeneratingListener();

        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        long testFoundCount = summary.getTestsFoundCount();
        List<Failure> failures = summary.getFailures();
        System.out.println("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
        failures.forEach(failure -> System.out.println("failure - " + failure.getException()));
    }

//    @Test
//    void exceptionTesting() {
//        Executable closureContainingCodeToTest = () -> {throw new IllegalArgumentException("a message");};
//        Throwable throwable = assertThrows(IllegalArgumentException.class, closureContainingCodeToTest, "a message");
//        assertEquals("a message", throwable.getMessage());
//    }

}
