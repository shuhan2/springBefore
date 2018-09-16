import TestClass.MyBean;
import TestClass.ValidClass;
import com.sun.tools.classfile.Dependency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class DependencyTest {

    @Test
    void should_throw_IllegalStateException_when_getBean_not_registered_dependency() {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        Executable executable = () -> context.getBean(MyBean.class) ;
        assertThrows(IllegalStateException.class,executable);
    }

}
