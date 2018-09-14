import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IoCContextImplTest {

    private static final String ABSTRACT_CLASS_IS_ABSTRACT = "AbstractClass is abstract.";

    private IoCContext context = new IoCContextImpl();

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_null() {
        try {
            context.registerBean(null);
        } catch (Exception exception) {
            assertEquals(IoCContextImpl.BEAN_CLAZZ_IS_MANDATORY,exception.getMessage());
            assertEquals(null,exception.getCause());
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_can_not_instanced() {
        try {
            context.registerBean(AbstractClass.class);
        } catch (Exception exception) {
            assertEquals(AbstractClass.class.getName() + IoCContextImpl.IS_ABSTRACT,exception.getMessage());assertEquals(IllegalArgumentException.class,exception.getClass());
            assertEquals(IllegalArgumentException.class,exception.getClass());

        }

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_not_have_constructors() {
        try {
            context.registerBean(NotDefaultConstructorClass.class);
        } catch (Exception exception) {
            assertEquals(NotDefaultConstructorClass.class.getName() + IoCContextImpl.HAS_NO_DEFAULT_CONSTRUCTOR,exception.getMessage());
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }

    }
}
