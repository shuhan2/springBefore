import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoCContextImpl implements IoCContext,Cloneable {
    static final String BEAN_CLAZZ_IS_MANDATORY = "beanClazz is mandatory";
    static final String IS_ABSTRACT = " is abstract";
    static final String HAS_NO_DEFAULT_CONSTRUCTOR = " has no default constructor.";
    private static final String HAS_STARTED_TO_GET_BEAN = "has started to getBean";
    static Map<Class,Class> registerMap = new HashMap<>();
    private static List<Class> getBeanMap = new ArrayList<>();
    @Override
    public void registerBean(Class<?> beanClazz)  {
        if (beanClazz == null ) {
            throw new IllegalArgumentException(BEAN_CLAZZ_IS_MANDATORY);
        }
        if (beanClazz.isInterface() || Modifier.isAbstract(beanClazz.getModifiers())) {
            throw new IllegalArgumentException(beanClazz.getName() + IS_ABSTRACT);
        }
        try {
            beanClazz.getConstructor();
        } catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException(beanClazz.getName() + HAS_NO_DEFAULT_CONSTRUCTOR);

        }

        if (getBeanMap.indexOf(beanClazz) >-1) {
            throw new IllegalStateException(beanClazz.getName() + HAS_STARTED_TO_GET_BEAN);
        }
        if (!registerMap.containsKey(beanClazz)){
            registerMap.put(beanClazz,beanClazz);
        }
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz)  {
        if (resolveClazz == null || beanClazz == null ) {
            throw new IllegalArgumentException(BEAN_CLAZZ_IS_MANDATORY);
        }
        if (beanClazz.isInterface() || Modifier.isAbstract(beanClazz.getModifiers())) {
            throw new IllegalArgumentException(beanClazz.getName() + IS_ABSTRACT);
        }
        try {
            beanClazz.getConstructor();

        } catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException(beanClazz.getName() + HAS_NO_DEFAULT_CONSTRUCTOR);
        }

        if (!registerMap.containsKey(beanClazz)){
            registerMap.put(resolveClazz,beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException {
        getBeanMap.add(registerMap.get(resolveClazz));
        if (resolveClazz == null){
            throw new IllegalStateException(BEAN_CLAZZ_IS_MANDATORY);
        }
        if (!registerMap.containsKey(resolveClazz)) {
            throw new IllegalStateException(BEAN_CLAZZ_IS_MANDATORY);
        }
        return createInstance(resolveClazz);
    }

    public <T> T createInstance(Class<T> resolveClazz) throws IllegalAccessException {
        Object instance = null;
        try {
            instance = registerMap.get(resolveClazz).newInstance();
        } catch (InstantiationException exception) {

        } catch (IllegalAccessException exception) {

        }
        for (Field declaredField : resolveClazz.getDeclaredFields()) {

            Class<?> type = declaredField.getType();
            if (!registerMap.containsKey(type)) {
                throw new IllegalStateException();
            }
            declaredField.set(instance,getBean(type));

        }
        return (T) instance;
    }



}
