package com.wkswind.leanote.gson;

import android.support.v4.util.LruCache;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wkswind.leanote.utils.Utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import timber.log.Timber;

/**
 * Created by Administrator on 2016-12-6.
 */

abstract class CustomTypeAdapter<T> extends TypeAdapter<T> {
    private ObjectTypeAdapter ota;
    private final Class clazz;
    private final LruCache<String,Field> fieldCache = new LruCache<String,Field>(160){
        @Override
        protected int sizeOf(String key, Field value) {
            return 1;
        }
    };
    public CustomTypeAdapter(){
        clazz = getGenericType(0);
        ota = (ObjectTypeAdapter) ObjectTypeAdapter.FACTORY.create(new Gson(), new TypeToken<Object>(){});
    }

    protected Object parseWithObjectTypeAdapter(JsonReader in) throws IOException {
        return ota.read(in);
    }

    private Class getGenericType(int index){
        Type genType = getClass().getGenericSuperclass();
        if(!(genType instanceof ParameterizedType)){
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if(index >= params.length || index < 0){
            throw new RuntimeException("Index outof bounds");
        }
        if(!(params[index] instanceof Class)){
            return Object.class;
        }
        return (Class) params[index];
    }

    protected synchronized Field getFieldByName(String name){
        Field cache = fieldCache.get(name);
        if(cache == null){
            try {
                Field f = clazz.getDeclaredField(name);
                if(f != null){
                    fieldCache.put(name, f);
                }
                return f;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

    protected void setFieldValue(Field field, T recever, Object value){
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        try {
            field.set(recever,value);
        } catch (IllegalAccessException e) {
            Timber.e(e);
        }
    }

    protected void write(JsonWriter out, Field field, T receiver){
        if(!field.isAccessible()){
            field.setAccessible(true);
        }
        try {
            final String name = field.getName();
            final Object value = field.get(receiver);
            out.name(name);
            if (value instanceof String) {
                out.value((String) value);
            } else if (value instanceof Boolean) {
                out.value((Boolean) value);
            } else if (value instanceof Long) {
                out.value((Long) value);
            } else if (value instanceof Number) {
                out.value((Number) value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean writeField(Field field){
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || Modifier.isVolatile(modifiers)) {
            return false;
        }
        return field.getAnnotation(Expose.class) == null;

    }
}
