import TestClass.*;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloseTest {
    @Test
    void should_invoke_close_in_reverse_order_as_create() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        context.registerBean(SuperMyBean.class);
        context.registerBean(MyDependency.class);
        context.registerBean(SuperMyDependency.class);
        MyBean myBean = context.getBean(MyBean.class);
        context.close();
        Date myDate = myBean.getMyDependency().getTime();
        Date superDate = myBean.getSuperMyDependency().getTime();
        assertTrue(myDate.after(superDate));
    }

    @Test
    void should_invoke_other_close_and_throw_first_exception_when_invoke_close() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(AnotherBean.class);
        context.registerBean(SuperAnotherBean.class);
        context.registerBean(AnotherDependency.class);
        context.registerBean(SuperAnotherDependency.class);
        AnotherBean myBean = context.getBean(AnotherBean.class);
        assertThrows(IllegalArgumentException.class,() -> context.close());
        assertTrue(myBean.getAnotherDependency().isClosed());
        assertTrue(myBean.getSuperAnotherDependency().isClosed());
    }
}
