import static java.lang.reflect.Modifier.ABSTRACT;

public class IoCContextImpl implements IoCContext {
    static final String BEAN_CLAZZ_IS_MANDATORY = "beanClazz is mandatory";
    static final String IS_ABSTRACT = " is abstract";
    static final String HAS_NO_DEFAULT_CONSTRUCTOR = " has no default constructor.";

    @Override
    public void registerBean(Class<?> beanClazz) throws NoSuchMethodException, SecurityException  {
        if (beanClazz == null ) {
            throw new IllegalArgumentException(BEAN_CLAZZ_IS_MANDATORY);
        } else if (beanClazz.getModifiers()  == ABSTRACT) {
            throw new IllegalArgumentException(beanClazz.getName() + IS_ABSTRACT);
        } else if (beanClazz.getConstructor() == null) {
            throw new IllegalArgumentException(beanClazz.getName() + HAS_NO_DEFAULT_CONSTRUCTOR);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return null;
    }
}
