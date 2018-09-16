import Annotation.CreateOnFly;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class IoCContextImpl implements IoCContext {
    static final String BEAN_CLAZZ_IS_MANDATORY = "beanClazz is mandatory";
    static final String IS_ABSTRACT = " is abstract";
    static final String HAS_NO_DEFAULT_CONSTRUCTOR = " has no default constructor.";
    public static final String HAS_NOT_BEEN_REGISTERED = " has not been registered";
    static Map<Class,Class> registerMap = new HashMap<>();
    private  List<Class> getBeanList = new ArrayList<>();
    public static Map<Object,List<Class>> fieldMap = new LinkedHashMap();
    public Stack<Object> classStack = new Stack<>();

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

        if (getBeanList.indexOf(beanClazz) >-1) {
            throw new IllegalStateException();
        }
        if (!registerMap.containsKey(beanClazz)){
            registerMap.put(beanClazz,beanClazz);
            while (beanClazz.getSuperclass()!=null){
                beanClazz = beanClazz.getSuperclass();
                registerBean(beanClazz);
            }
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
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(beanClazz.getName() + HAS_NO_DEFAULT_CONSTRUCTOR);
        }

        if (!registerMap.containsKey(beanClazz)){
            registerMap.put(resolveClazz,beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws Exception {
        getBeanList.add(registerMap.get(resolveClazz));
        if (resolveClazz == null){
            throw new IllegalStateException(BEAN_CLAZZ_IS_MANDATORY);
        }
        if (!registerMap.containsKey(resolveClazz)) {
            throw new IllegalStateException(resolveClazz.getName() + HAS_NOT_BEEN_REGISTERED);
        }

        return createInstance(resolveClazz);
    }

    private <T> T createInstance(Class<T> resolveClazz) throws Exception {
        Object instance = null;

        instance = registerMap.get(resolveClazz).newInstance();
        classStack.push(instance);
        getField(resolveClazz, instance);
        return (T) instance;
    }

    private <T> void getField(Class<T> resolveClazz, Object instance) throws Exception {
        for (Field declaredField : resolveClazz.getDeclaredFields()) {
            while (resolveClazz.getSuperclass()!=null){
                resolveClazz = (Class<T>) resolveClazz.getSuperclass();
                getField (resolveClazz,instance);
            }
            if (declaredField.isAnnotationPresent(CreateOnFly.class)) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();

                if (!registerMap.containsKey(type)) {
                    throw new IllegalStateException();
                }

                declaredField.set(instance, getBean(type));

                if (!fieldMap.containsKey(instance)) {
                    fieldMap.put(instance, new ArrayList<>());
                }
                fieldMap.get(instance).add(declaredField.getType());
            }

        }
    }


    @Override
    public void close() throws Exception {
        Exception storeException = null;
        for (Object closeable : classStack) {
            try {
                ((AutoCloseable)closeable).close();
            } catch (ClassCastException exception) {

            } catch (Exception exception) {
                storeException = storeException == null ? exception:storeException;
            }
        }
        if ( storeException != null ) {
            throw storeException;
        }
    }
}
