import TestClass.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class NewRegisterBeanTest {
    @Test
    void should_throw_IllegalArgumentException_when_register_null() {
        IoCContextImpl context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(null,null);
        assertThrows(IllegalArgumentException.class,executable, IoCContextImpl.BEAN_CLAZZ_IS_MANDATORY);

    }

    @Test
    void should_throw_IllegalArgumentException_when_register_abstract_class() {
        IoCContextImpl context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(AbstractSuperClass.class , AnotherAbstractClass.class);

        assertThrows(IllegalArgumentException.class , executable,AnotherAbstractClass.class.getName() + IoCContextImpl.IS_ABSTRACT);

    }

    @Test
    void should_throw_IllegalArgumentException_when_register_not_have_constructors() {
        IoCContext context = new IoCContextImpl();
        try {
            context.registerBean(SuperValidClass.class,ValidClass.class);
            int beforeLength = IoCContextImpl.registerList.size();
            context.registerBean(SuperValidClass.class,ValidClass.class);
            int afterLength = IoCContextImpl.registerList.size();
            assertEquals(beforeLength,afterLength);
            assertDoesNotThrow(() -> {context.registerBean(SuperValidClass.class,ValidClass.class);
                context.registerBean(SuperValidClass.class,ValidClass.class);});
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void should_return_when_register_class_has_been_registered() {
        IoCContextImpl context = new IoCContextImpl();
        Executable executable = () -> context.registerBean(SuperValidClass.class, ValidClass.class);
        assertThrows(IllegalArgumentException.class , executable,AnotherAbstractClass.class.getName() + IoCContextImpl.HAS_NO_DEFAULT_CONSTRUCTOR);

    }

}
