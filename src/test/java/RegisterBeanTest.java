import TestClass.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterBeanTest {

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_null() {
        IoCContext context = new IoCContextImpl();
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
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(AbstractClass.class);
        } catch (Exception exception) {
            assertEquals(AbstractClass.class.getName() + IoCContextImpl.IS_ABSTRACT,exception.getMessage());assertEquals(IllegalArgumentException.class,exception.getClass());
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_not_have_constructors() {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(NotDefaultConstructorClass.class);
        } catch (Exception exception) {
            assertEquals(NotDefaultConstructorClass.class.getName() + IoCContextImpl.HAS_NO_DEFAULT_CONSTRUCTOR,exception.getMessage());
            assertEquals(IllegalArgumentException.class,exception.getClass());
        }
    }
    @Test
    void should_return_with_no_error_when_Class_has_been_registered()  {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(ValidClass.class);
            int beforeLength = IoCContextImpl.registerList.size();
            context.registerBean(ValidClass.class);
            int afterLength = IoCContextImpl.registerList.size();
            assertEquals(beforeLength,afterLength);
            assertDoesNotThrow(() -> {context.registerBean(ValidClass.class);
            context.registerBean(ValidClass.class);});
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }

    }

    @Test
    void should_throw_IllegalStateException_with_message_when_getBean_null()  {
        IoCContext context = new IoCContextImpl();
        assertThrows(IllegalStateException.class, ()->context.getBean(null));
    }

    @Test
    void should_throw_IllegalStateException_when_getBean_Class_not_been_registered() {
        IoCContext context = new IoCContextImpl();
        assertThrows(IllegalStateException.class, () -> context.getBean(NotRegisteredClass.class));
    }

    @Test
    void should_throw_specificException_when_getBean_Class_throw_exception_in_constructor() throws NoSuchMethodException {
        IoCContext context = new IoCContextImpl();
        context.registerBean(ExceptionClass.class);
        assertThrows(SpecificException.class,() -> context.getBean(ExceptionClass.class));
    }

    @Test
    void should_throw_IllegalStateException_when_register_after_start_get_bean()  {
        IoCContext context = new IoCContextImpl();
        assertThrows(IllegalStateException.class,() -> {
            context.registerBean(ValidClass.class);
            context.getBean(ValidClass.class);
            context.registerBean(ValidClass.class);});
    }


}
