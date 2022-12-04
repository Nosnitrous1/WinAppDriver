import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runners.AllTests;

class Start {
    public static void main(String args[]) throws InterruptedException {
//        JUnitCore.runClasses(CalcTest.class, NotepadeTest.class, MesAuth.class).getFailures();
//        JUnitCore.runClasses(MesAuth.class).getFailures();
        JUnitCore.runClasses(TksoForward.class).getFailures();
    }
}