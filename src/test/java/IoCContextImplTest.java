import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IoCContextImplTest {

    private static final String ABSTRACT_CLASS_IS_ABSTRACT = "AbstractClass is abstract";

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_null() {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(null);
        } catch (Exception exception) {
            assertEquals(IoCContextImpl.BEAN_CLAZZ_IS_MANDATORY,exception.getMessage());
            assertEquals(null,exception.getCause());
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_not_instan() {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(AbstractClass.class);
        } catch (Exception exception) {
            assertEquals(ABSTRACT_CLASS_IS_ABSTRACT,exception.getMessage());assertEquals(IllegalArgumentException.class,exception.getClass());
            assertEquals(IllegalArgumentException.class,exception.getClass());

        }

    }
}
