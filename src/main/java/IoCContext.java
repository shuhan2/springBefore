public interface IoCContext {
    void registerBean(Class<?> beanClazz) throws NoSuchMethodException;

    <T>void registerBean(Class<? super T> resolveClazz,Class<T> beanClazz) throws NoSuchMethodException;

    <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException;
}
