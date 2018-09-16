import TestClass.MyBean;
import TestClass.MyDependency;
import TestClass.SuperMyBean;
import TestClass.SuperMyDependency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void should_get_dependency_in_superClass_and_then_get_dependency_in_superClass_when_create_instance() throws IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);
        context.registerBean(SuperMyDependency.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(MyBean.class,myBean.getClass());
        assertEquals(SuperMyDependency.class,myBean.getSuperMyDependency().getClass());
        int myDependencyIndex = IoCContextImpl.fieldMap.get(myBean).indexOf(myBean.getMyDependency().getClass());
        int superMyDependencyIndex = IoCContextImpl.fieldMap.get(myBean).indexOf(myBean.getSuperMyDependency().getClass());
        assertTrue(myDependencyIndex > superMyDependencyIndex);
    }
}
