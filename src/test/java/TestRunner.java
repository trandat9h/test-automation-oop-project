import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import Utils.HTTPRequest;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.listeners.TestExecutionSummary.Failure;
import org.junit.platform.engine.DiscoverySelector;

public class TestRunner {
    private final SummaryGeneratingListener listener = new SummaryGeneratingListener();
    private static final ArrayList<String> packages = new ArrayList<>() {{
        add("AuctionTest");
        add("AuthenticationTest");
        add("BidTest");
        add("BrandTest");
        add("CategoryTest");
        add("ChatTest");
        add("CommentTest");
        add("ContactTest");
        add("LikeTest");
        add("NewsTest");
        add("NotificationTest");
        add("SliderTest");
        // add all test packages here
    }};
    private final ArrayList<DiscoverySelector> selectors = packageToSelector();
    private LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(selectors)
            .filters(includeClassNamePatterns(".*Test" ))
            .build();


    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        String packageName, API;
        List<TestIdentifier> APIs;


        System.out.println("===================== MENU ====================== \n");
        System.out.println("[System] Choose your URL. Type 'pass' to use the default one. \n");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();

        if (!url.equals("pass"))
            HTTPRequest.setURL(url);

        System.out.println("[System] URL: " + HTTPRequest.baseUrl + "\n");

        System.out.println("[System] Choose a resource you want to test. Type 'all' to test all resources. \n");
        for(int index = 0; index < packages.size(); index++) {
            System.out.println(index + ": " + packages.get(index) + "\n");
        }

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("all")) {
                packageName = userInput;
                break;
            }
            else {
                try {
                    int choice = Integer.parseInt(userInput);
                    if (choice < 0 || choice >= packages.size())
                        throw new Exception();
                    packageName = packages.get(choice);
                    break;
                } catch (Exception e) {
                    System.out.println("[Error] Wrong choice. Choose again! \n");
                }
            }
        }

        System.out.println("[User] I want to test " + packageName + ".\n");

        if (packageName.equals("all")) {
            System.out.println("[System] Running... \n");
            runner.runAll();
        }
        else {
            // this warning can be skipped as above code makes sure this list never null
            APIs = new ArrayList<TestIdentifier>(runner.discoverAPIs(packageName));

            System.out.println("[System] Choose an API you want to test. Type 'all' to test all APIs. \n");
            for (int index = 0; index < APIs.size(); index++) {
                System.out.println(index + ": " + APIs.get(index).getDisplayName() + "\n");
            }

            while (true) {
                String userInput = scanner.nextLine();
                if (userInput.equals("all")) {
                    API = userInput;
                    break;
                } else {
                    try {
                        int choice = Integer.parseInt(userInput);
                        if (choice < 0 || choice >= APIs.size())
                            throw new Exception();
                        API = APIs.get(choice).getDisplayName();
                        break;
                    } catch (Exception e) {
                        System.out.println("[Error] Wrong choice. Choose again! \n");
                    }
                }
            }

            System.out.println("[User] I want to test " + API + ".\n");

            while (true) {
                System.out.println("[System] Choose an execute options: press 1 or 2 to choose\n");
                System.out.println("1. Execute all tests inside chosen API \n");
                System.out.println("2. Execute one test only \n");
                String userInput = scanner.nextLine();
                if (userInput.equals("1")) {
                    System.out.println("[User] I want to run all tests.\n");
                    System.out.println("[System] Running... \n");
                    runner.runTestClass(API);
                    break;
                } else if (userInput.equals("2")) {
                    Set<TestIdentifier> _discoveredTests = runner.discoverTests(API);
                    assert _discoveredTests != null;
                    List<TestIdentifier> discoveredTests = new ArrayList<>(_discoveredTests);
                    System.out.println("[System] List of discovered tests, choose a test pls \n");
                    for (int index = 0; index < discoveredTests.size(); index++) {
                        System.out.println(index + ": " + discoveredTests.get(index).getDisplayName() + "\n");
                    }

                    while (true) {
                        userInput = scanner.nextLine();
                        try {
                            int testIndex = Integer.parseInt(userInput);
                            if (testIndex < 0 || testIndex > discoveredTests.size())
                                throw new Exception();

                            System.out.println("[User] I want to run test " + discoveredTests.get(testIndex).getDisplayName() + "\n");
                            System.out.println("[System] Running... \n");
                            runner.testExecute(discoveredTests.get(testIndex));

                            break;
                        } catch (Exception e) {
                            System.out.println("[Error] Wrong choice! Choose again please. \n");
                        }
                    }
                    break;
                } else {
                    System.out.println("[Error] Wrong choice! Choose again please. \n");
                }
            }
        }

        TestExecutionSummary summary = runner.listener.getSummary();

        failedTestPrint(summary.getFailures());
        summary.printTo(new PrintWriter(System.out));
    }

    private Set<TestIdentifier> discoverTests (String testClassName) {
        request.getDiscoveryListener();

        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        Set<TestIdentifier> roots = testPlan.getRoots();
        Set<TestIdentifier> testClasses = new HashSet<>();

        for (TestIdentifier root :roots) {
            testClasses = testPlan.getChildren(root);
            break;
        }

        for (TestIdentifier testClass: testClasses) {
            if (testClass.getDisplayName().equals(testClassName))
                return testPlan.getChildren(testClass);
        }
        return null;
    }

    private Set<TestIdentifier> discoverAPIs (String packageName) {
        LauncherDiscoveryRequest _request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectPackage(packageName))
                .filters(includeClassNamePatterns(".*Test" ))
                .build();

        _request.getDiscoveryListener();

        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(_request);
        Set<TestIdentifier> roots = testPlan.getRoots();

        for (TestIdentifier root :roots) {
            return testPlan.getChildren(root);
        }

        return null;
    }

    private void testExecute(TestIdentifier testIdentifier) {
        LauncherDiscoveryRequest _request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectUniqueId(testIdentifier.getUniqueId()))
                .build();

        _testExecute(_request);
    }

    private void _testExecute(LauncherDiscoveryRequest request) {
        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    private void testExecute() {
        _testExecute(request);
    }

    private void testExecute(LauncherDiscoveryRequest customRequest) {
        _testExecute(customRequest);
    }

    private void runAll()   {
        // request.getDiscoveryListener();
        testExecute();
    }

    private void runTestClass(String testClass) {
        request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectors)
                .filters(includeClassNamePatterns(".*" + testClass ))
                .build();
        testExecute(request);
    }

    private static ArrayList<DiscoverySelector> packageToSelector() {
        ArrayList<DiscoverySelector> selectors = new ArrayList<>();

        for (String testClassName: packages) {
            selectors.add(selectPackage(testClassName));
        }

        return selectors;
    }

    private static void failedTestPrint(List<Failure> failedTests) {
        try {
            File myObj = new File("error_report.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("error_report.txt");

            for (Failure test: failedTests) {
                TestIdentifier testIdentifier = test.getTestIdentifier();
                myWriter.write("================= Fail Test ==================== \n");
                myWriter.write("Test: " + testIdentifier.getDisplayName() + "\n");
                myWriter.write("Error: " + test.getException().getMessage() + "\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote failed tests to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
