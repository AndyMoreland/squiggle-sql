package com.truemesh.squiggle.tests.support;

import com.truemesh.squiggle.output.Outputable;
import com.truemesh.squiggle.output.ToStringer;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

public class SqlMatcher extends TypeSafeMatcher<Outputable> {
    private final Matcher<String> outputMatcher;
    
    public SqlMatcher(String expectedSql) {
        outputMatcher = equalToIgnoringWhiteSpace(expectedSql);
    }

    public boolean matchesSafely(Outputable outputable) {
        return outputMatcher.matches(ToStringer.toString(outputable));
    }

    public void describeTo(Description description) {
        description.appendText("generates SQL ").appendDescriptionOf(outputMatcher);
    }

    public static SqlMatcher generatesSql(String expectedSql) {
        return new SqlMatcher(expectedSql);
    }
}
