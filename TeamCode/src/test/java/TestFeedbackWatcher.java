import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class TestFeedbackWatcher extends TestWatcher {
    @Override
    protected void succeeded(Description description) {
        System.out.println(description.getMethodName() + " PASSED");
    }

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println(description.getMethodName() + " FAILED");
    }
}
