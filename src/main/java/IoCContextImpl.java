import static java.lang.reflect.Modifier.ABSTRACT;

public class IoCContextImpl implements IoCContext {
    static final String BEAN_CLAZZ_IS_MANDATORY = "beanClazz is mandatory";
    private static final String IS_ABSTRACT = " is abstract";

    @Override
    public void registerBean(Class<?> beanClazz) {
        if (beanClazz == null ) {
            throw new IllegalArgumentException(BEAN_CLAZZ_IS_MANDATORY);
        } else if (beanClazz.getModifiers()  == ABSTRACT) {
            throw new IllegalArgumentException(beanClazz.getName() + IS_ABSTRACT);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return null;
    }
}
