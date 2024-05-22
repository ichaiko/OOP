package ru.nsu.chaiko.dsl;

import groovy.lang.GroovyObjectSupport;
import groovy.lang.Closure;
import groovy.lang.MetaProperty;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class GroovyConfigurable extends GroovyObjectSupport {
    public void postProcess() throws Exception {
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName());
            if (isCollectionOfGroovyConfigurable(metaProperty, value)) {
                processCollection(metaProperty, (Collection<?>) value);
            }
        }
    }

    private boolean isCollectionOfGroovyConfigurable(MetaProperty metaProperty, Object value) throws NoSuchFieldException {
        if (Collection.class.isAssignableFrom(metaProperty.getType()) && value instanceof Collection) {
            ParameterizedType collectionType = (ParameterizedType) getClass().getDeclaredField(metaProperty.getName()).getGenericType();
            Class<?> itemClass = (Class<?>) collectionType.getActualTypeArguments()[0];
            return GroovyConfigurable.class.isAssignableFrom(itemClass);
        }
        return false;
    }

    private void processCollection(MetaProperty metaProperty, Collection<?> collection) throws Exception {
        Collection newValue = collection.getClass().getDeclaredConstructor().newInstance();
        for (Object o : collection) {
            if (o instanceof Closure) {
                Object item = createAndConfigureItem((Closure<?>) o);
                newValue.add(item);
            } else {
                newValue.add(o);
            }
        }
        setProperty(metaProperty.getName(), newValue);
    }

    private Object createAndConfigureItem(Closure<?> closure) throws Exception {
        Class<?> itemClass = closure.getDelegate().getClass();
        Object item = itemClass.getDeclaredConstructor().newInstance();
        closure.setDelegate(item);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
        ((GroovyConfigurable) item).postProcess();
        return item;
    }
}
