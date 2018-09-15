import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.ABSTRACT;

public class IoCContextImpl implements IoCContext,Cloneable {
    static final String BEAN_CLAZZ_IS_MANDATORY = "beanClazz is mandatory";
    static final String IS_ABSTRACT = " is abstract";
    static final String HAS_NO_DEFAULT_CONSTRUCTOR = " has no default constructor.";
    private static final String HAS_STARTED_TO_GET_BEAN = "has started to getBean";
    static List<Class> registerList = new ArrayList<>();
    private static List<Class> getBeanList = new ArrayList<>();
    @Override
    public void registerBean(Class<?> beanClazz) throws NoSuchMethodException {
        if (beanClazz == null ) {
            throw new IllegalArgumentException(BEAN_CLAZZ_IS_MANDATORY);
        } else if (beanClazz.getModifiers()  == ABSTRACT) {
            throw new IllegalArgumentException(beanClazz.getName() + IS_ABSTRACT);
        } else if (beanClazz.getDeclaredConstructor() == null) {
            throw new IllegalArgumentException(beanClazz.getName() + HAS_NO_DEFAULT_CONSTRUCTOR);
        } else if (getBeanList.indexOf(beanClazz) > -1) {
            throw new IllegalStateException(beanClazz.getName() + HAS_STARTED_TO_GET_BEAN);
        } else if (registerList.indexOf(beanClazz)  > -1){
            return;
        }else {
            registerList.add(beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz)  {
        getBeanList.add(resolveClazz);
        if (resolveClazz == null){
            throw new IllegalStateException(BEAN_CLAZZ_IS_MANDATORY);
        }
        if (registerList.indexOf(resolveClazz) < 0) {
            throw new IllegalStateException(BEAN_CLAZZ_IS_MANDATORY);
        }
        Object instance = null;
        try {
            instance = resolveClazz.newInstance();
        } catch (InstantiationException exception) {

        } catch (IllegalAccessException exception) {

        }

        return (T) instance;
    }
}
