import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;


import java.util.Set;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import Utils.CustomResponse;
import Utils.HTTPRequest;
import org.json.simple.JSONObject;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;

public class TestRunner {
    public static void main(String args[]) {
//        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
//                .selectors(
//                        selectClass(MyFirstJUnitJupiterTests.class)
//                )
//                .filters(
//                        includeClassNamePatterns(".*Tests")
//                )
//                .build();
//
//        try (LauncherSession session = LauncherFactory.openSession()) {
//            TestPlan testPlan = session.getLauncher().discover(request);
//            TestIdentifier rootTest = testPlan.getRoots().iterator().next();
//            Set<TestIdentifier> children = testPlan.getChildren(rootTest.getUniqueId());
//            TestIdentifier k = children.iterator().next();
//            System.out.println(k);
//
//
//            // ... discover additional test plans or execute tests
//        }
    }
}
