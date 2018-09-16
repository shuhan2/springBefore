import TestClass.MyBean;
import TestClass.MyDependency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DependencyTest {

    @Test
    void should_throw_IllegalStateException_when_getBean_not_registered_dependency() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        Executable executable = () -> context.getBean(MyBean.class) ;
        assertThrows(IllegalStateException.class,executable);
    }

    @Test
    void should_get_dependency_when_getBean_Class_after_register_class_and_dependency() throws IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(MyBean.class,myBean.getClass());
        assertEquals(MyDependency.class,myBean.getMyDependency().getClass());
    }
}
