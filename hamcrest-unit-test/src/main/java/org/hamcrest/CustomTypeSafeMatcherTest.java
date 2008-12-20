package org.hamcrest;

public class CustomTypeSafeMatcherTest extends AbstractMatcherTest {
    private static final String STATIC_DESCRIPTION = "I match non empty strings";
    private Matcher<String> customMatcher;

    @Override
    protected void setUp() throws Exception {
        customMatcher = new CustomTypeSafeMatcher<String>(STATIC_DESCRIPTION) {
            @Override
            public boolean matchesSafely(String item) {
                return false;
            }

            @Override
            public void describeMismatchSafely(String item, Description mismatchDescription) {
              mismatchDescription.appendText("an " + item);
            }
        };
    }

    public void testUsesStaticDescription() throws Exception {
        assertDescription(STATIC_DESCRIPTION, customMatcher);
    }

    public void testReportsMismatch() {
      assertMismatchDescription("an item", customMatcher, "item");
    }

    @Override
    protected Matcher<?> createMatcher() {
        return customMatcher;
    }
}
