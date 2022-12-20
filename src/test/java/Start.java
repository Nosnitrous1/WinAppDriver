import org.junit.runner.JUnitCore;

class Start {
    public static void main(String args[]) throws InterruptedException {
        JUnitCore.runClasses(CalcTest.class, NotepadeTest.class, MesAuth.class, TksoForward.class).getFailures();
//        JUnitCore.runClasses(MesAuth.class).getFailures();
//        JUnitCore.runClasses(TksoForward.class).getFailures();
//        JUnitCore.runClasses(Excel.class).getFailures();
    }
}