package com.wkswind.leanote.gson;

import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.wkswind.leanote.database.Note;
import com.wkswind.leanote.database.Notebook;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class CustomTypeAdapter<T> extends TypeAdapter<T> {
    private ObjectTypeAdapter ota;
    private final Class clazz;
    private final LruCache<String, Field> fieldCache = new LruCache<String, Field>(160) {
        @Override
        protected int sizeOf(String key, Field value) {
            return 1;
        }
    };

    public CustomTypeAdapter() {
        clazz = getGenericType(0);
        ota = (ObjectTypeAdapter) ObjectTypeAdapter.FACTORY.create(new Gson(), new TypeToken<Object>() {
        });
    }

    private Object parseWithObjectTypeAdapter(JsonReader in) throws IOException {
        return ota.read(in);
    }

    private Class getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    private synchronized Field getFieldByName(String name) {
        Field cache = fieldCache.get(name);
        if (cache == null) {
            try {
                Field f = clazz.getDeclaredField(name);
                if (f != null) {
                    fieldCache.put(name, f);
                }
                return f;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

    protected void setFieldValue(Field field, T recever, Object value) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            field.set(recever, value);
        } catch (IllegalAccessException e) {
            Timber.e(e);
        }
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (fieldShouldJson(field)) {
                    if (string2arrayField(field)) {
                        writeSingleString2ArrayField(out, field, value);
                    } else {
                        writeSingleField(out, field, value);
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(JsonReader in) throws IOException {
        Object obj = parseWithObjectTypeAdapter(in);
        if(obj == null) {
            return null;
        }
        if(obj instanceof Map){
            try {
                T t = (T) clazz.newInstance();
                Map map = (Map) obj;
                for (Map.Entry entry : (Iterable<Map.Entry>) map.entrySet()) {
                    String key = String.valueOf(entry.getKey());
                    Object value = entry.getValue();
                    final Field field = getFieldByName(key);
                    if (field != null && fieldShouldJson(field)) {
                        field.setAccessible(true);
                        if (string2arrayField(field)) {
                            if (value instanceof List) {
                                final String delimiter = field.getAnnotation(String2Array.class).delimiter();
                                List list = (List) value;
                                value = TextUtils.join(delimiter, list);
                            }
                        }
                        field.set(t, value);
                    }
                }
                return t;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void writeSingleString2ArrayField(JsonWriter out, Field field, T receiver) throws IllegalAccessException, IOException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        String delimiter = field.getAnnotation(String2Array.class).delimiter();
        final Object value = field.get(receiver);
        out.beginArray();
        if (value != null) {
            String string = String.valueOf(value);
            String[] array = string.split(delimiter);
            for (String s : array) {
                out.value(s);
            }
        }
        out.endArray();
    }


    private void writeSingleField(JsonWriter out, Field field, T receiver) throws IllegalAccessException, IOException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

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

    }

    private boolean fieldShouldJson(@NonNull Field field) {
        int modifiers = field.getModifiers();
        return !(Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers) || Modifier.isVolatile(modifiers)) && field.getAnnotation(Expose.class) == null;

    }

    private boolean string2arrayField(@NonNull Field field) {
        return field.getAnnotation(String2Array.class) != null;
    }

    public static class NoteTypeAdapter extends CustomTypeAdapter<Note>{
        public NoteTypeAdapter(){
            super();
        }
    }

    public static class NotebookTypeAdapter extends CustomTypeAdapter<Notebook>{
        public NotebookTypeAdapter(){
            super();
        }
    }
}
