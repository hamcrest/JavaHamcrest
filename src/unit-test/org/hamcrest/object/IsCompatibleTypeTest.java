package org.hamcrest.object;

import org.hamcrest.AbstractMatcherTest;
import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.object.IsCompatibleType.classCompatibleWith;

public class IsCompatibleTypeTest extends AbstractMatcherTest {
    public static class BaseClass {
    }

    public static class ExtendedClass extends BaseClass {
    }

    public interface BaseInterface {
    }

    public interface ExtendedInterface extends BaseInterface {
    }

    public static class ClassImplementingBaseInterface implements BaseInterface {
    }

    protected Matcher<?> createMatcher() {
        return classCompatibleWith(BaseClass.class);
    }

    public void testMatchesSameClass() {
        assertThat(BaseClass.class, classCompatibleWith(BaseClass.class));
    }

    public void testMatchesSameInterface() {
        assertThat(BaseInterface.class, classCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedClass() {
        assertThat(ExtendedClass.class, classCompatibleWith(BaseClass.class));
    }

    public void testMatchesClassImplementingInterface() {
        assertThat(ClassImplementingBaseInterface.class, classCompatibleWith(BaseInterface.class));
    }

    public void testMatchesExtendedInterface() {
        assertThat(ExtendedInterface.class, classCompatibleWith(BaseInterface.class));
    }

//    public void testDoesNotMatchIncompatibleTypes() {
//        assertThat(BaseClass.class, not(compatibleType(ExtendedClass.class)));
//        assertThat(Integer.class, not(compatibleType(String.class)));
//    }

    public void testHasReadableDescription() {
        assertDescription("type < java.lang.Runnable", classCompatibleWith(Runnable.class));
    }
}
