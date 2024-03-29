import budget.Main;
import org.hyperskill.hstest.v6.stage.BaseStageTest;
import org.hyperskill.hstest.v6.testcase.CheckResult;
import org.hyperskill.hstest.v6.testcase.TestCase;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BudgetManagerTest extends BaseStageTest<String> {

    public BudgetManagerTest() {
        super(Main.class);
    }

    @Override
    public List<TestCase<String>> generate() {
        return List.of(
            new TestCase<String>()
                .setInput(
                    "Almond 250gm $35.43\n" +
                        "LEGO DUPLO Town Farm Animals $10.10\n" +
                        "Sensodyne Pronamel Toothpaste $19.74\n" +
                        "Hershey's milk chocolate bars $8.54\n" +
                        "Gildan LT $8.61\n")
                .setAttach(
                    "Almond 250gm $35.43\n" +
                        "LEGO DUPLO Town Farm Animals $10.10\n" +
                        "Sensodyne Pronamel Toothpaste $19.74\n" +
                        "Hershey's milk chocolate bars $8.54\n" +
                        "Gildan LT $8.61\n" +
                        "\n" +
                        "Total: $82,42\n"
                )
        );
    }

    @Override
    public CheckResult check(String reply, String attach) {

        reply = reply.trim().toLowerCase();
        String[] rightOutput = attach.toLowerCase().split("\n");

        if (!reply.toLowerCase().contains("total")) {
            return new CheckResult(false,
                "Output should contain total amount of purchases");
        }

        for (String s : rightOutput) {

            //Skip last or empty line
            if (s.contains("total") || s.equals(""))
                continue;

            if (!reply.contains(s)) {
                return new CheckResult(false,
                    "Output should contain purchase\n" + s);
            }
        }

        Pattern pattern = Pattern.compile("total:\\s+\\$(\\d\\d?[.,]\\d\\d)");
        Matcher matcher = pattern.matcher(reply);

        if (matcher.find()) {
            try {
                String totalAmount = matcher.group(1);
                double total = Double.parseDouble(totalAmount);
                if (Math.abs(82.42 - total) > 0.0001)
                    return new CheckResult(false,
                        "Your total amount: $" + totalAmount + "\n" +
                            "Expected: $82.42");
            } catch (NumberFormatException ex) {
                return new CheckResult(false,
                    "Total amount should be a number");
            }
            return new CheckResult(true);
        } else {
            return new CheckResult(false,
                "Total amount wasn't found.\n" +
                    "It should look like: Total: $0.00");
        }
    }
}
