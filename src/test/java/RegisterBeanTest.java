import TestClass.AbstractClass;
import TestClass.ValidClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterBeanTest {

    private static final String ABSTRACT_CLASS_IS_ABSTRACT = "AbstractClass is abstract.";

    private IoCContext context = new IoCContextImpl();

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_null() {
        try {
            context.registerBean(null);
        } catch (Exception exception) {
            assertEquals(IoCContextImpl.BEAN_CLAZZ_IS_MANDATORY,exception.getMessage());
            assertNull(exception.getCause());
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

    @Test
    void should_return_with_no_error_when_Class_has_been_registered()  {

        try {
            context.registerBean(ValidClass.class);
            int beforeLength = IoCContextImpl.classList.size();
            context.registerBean(ValidClass.class);
            int afterLength = IoCContextImpl.classList.size();

            assertEquals(beforeLength,afterLength);
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_getBean_null()  {
        assertThrows(IllegalArgumentException.class, ()->context.getBean(null));
    }

    @Test
    void should_throw_IllegalArgumentException_when_getBean_Class_not_been_registered() {
        assertThrows(IllegalArgumentException.class, () -> context.getBean(ValidClass.class));
    }


}
