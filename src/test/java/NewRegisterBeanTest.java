import TestClass.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;


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
            int beforeLength = IoCContextImpl.registerMap.size();
            context.registerBean(SuperValidClass.class,ValidClass.class);
            int afterLength = IoCContextImpl.registerMap.size();
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

    @Test
    void should_create_instance_when_execute_register_and_getBean() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        IoCContext context = new IoCContextImpl();
        context.registerBean(SuperValidClass.class,ValidClass.class);
        SuperValidClass validClass = context.getBean(SuperValidClass.class);
        assertEquals(validClass.getClass(),SuperValidClass.class);
    }

    @Test
    void should_override_previous_instance_when_register_same_resolveClazz() throws NoSuchMethodException, InstantiationException, IllegalAccessException {
        IoCContext context = new IoCContextImpl();
        context.registerBean(SuperValidClass.class,ValidClass.class);
        context.registerBean(SuperValidClass.class,AnotherValidClass.class);

        SuperValidClass instance = context.getBean(SuperValidClass.class);

        assertEquals(instance.getClass(), AnotherValidClass.class);
    }
}
