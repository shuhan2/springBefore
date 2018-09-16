import TestClass.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RegisterAndGetBeanTest {

    @Test
    void should_create_instance_when_execute_register_and_getBean() throws Exception {
        IoCContext context = new IoCContextImpl();
        context.registerBean(ValidClass.class);
        ValidClass validClass = context.getBean(ValidClass.class);
        assertEquals(validClass.getClass(),ValidClass.class);
    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_null() {
        IoCContext context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(null);
        assertThrows(IllegalArgumentException.class, executable,IoCContextImpl.BEAN_CLAZZ_IS_MANDATORY);

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_can_not_instanced() {
        IoCContext context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(AbstractClass.class);
        assertThrows(IllegalArgumentException.class, executable,AbstractClass.class.getName() + IoCContextImpl.IS_ABSTRACT);

    }

    @Test
    void should_throw_IllegalArgumentException_with_message_when_register_Class_not_have_constructors() {
        IoCContext context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(NotDefaultConstructorClass.class);
        assertThrows(IllegalArgumentException.class,executable,NotDefaultConstructorClass.class.getName() + IoCContextImpl.HAS_NO_DEFAULT_CONSTRUCTOR);

    }

    @Test
    void should_return_with_no_error_when_Class_has_been_registered()  {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(ValidClass.class);
            int beforeLength = IoCContextImpl.registerMap.size();
            context.registerBean(ValidClass.class);
            int afterLength = IoCContextImpl.registerMap.size();
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
