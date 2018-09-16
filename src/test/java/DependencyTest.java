import TestClass.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DependencyTest {

    @Test
    void should_throw_IllegalStateException_when_getBean_not_registered_dependency() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(DependencyBean.class);
        Executable executable = () -> context.getBean(DependencyBean.class) ;
        assertThrows(IllegalStateException.class,executable);
    }

    @Test
    void should_get_dependency_in_superClass_and_then_get_dependency_in_superClass_when_create_instance() throws Exception {
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
