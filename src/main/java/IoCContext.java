public interface IoCContext {
    void registerBean(Class<?> beanClazz) throws NoSuchMethodException;

    <T> T getBean(Class<T> resolveClazz);
}