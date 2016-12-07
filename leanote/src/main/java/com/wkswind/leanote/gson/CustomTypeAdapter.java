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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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

    private CustomTypeAdapter() {
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
                    } else if (date2longField(field)) {
                        writeUTC2LongField(out, field, value);
                    } else {
                        field2Json(out, field, value);
                    }
                }
            }
        } catch (Exception e) {
            Timber.e(e);
        }
        out.endObject();
    }

    private void writeUTC2LongField(JsonWriter out, Field field, T receiver) throws IllegalAccessException, IOException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }

        final String name = field.getName();
        final String format = field.getAnnotation(UTC2Long.class).dateFormat();
        if (!TextUtils.isEmpty(format)) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            final Object value = field.get(receiver);
            long time = (long) value;
            if (time != 0) {
                out.name(name);
                out.value(simpleDateFormat.format(new Date(time)));
            }
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public T read(JsonReader in) throws IOException {
        Object obj = parseWithObjectTypeAdapter(in);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Map) {
            try {
                T t = (T) clazz.newInstance();
                Map map = (Map) obj;
                for (Map.Entry entry : (Iterable<Map.Entry>) map.entrySet()) {
                    String key = String.valueOf(entry.getKey());
                    Object value = entry.getValue();
                    final Field field = getFieldByName(key);
                    if (field != null && fieldShouldJson(field)) {
//                        field.setAccessible(true);
                        if (string2arrayField(field)) {
                            if (value instanceof List) {
                                final String delimiter = field.getAnnotation(String2Array.class).delimiter();
                                List list = (List) value;
                                value = TextUtils.join(delimiter, list);
                            }
                        } else if (date2longField(field)) {
                            value = getUTC2LongValue(field, value);
                        }
                        writeField(field, t, value);
                    }
                }
                return t;
            } catch (InstantiationException | IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

//    private static final SimpleDateFormat UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault());

    private long getUTC2LongValue(@NonNull Field field, @NonNull Object value) throws ParseException {
        String format = field.getAnnotation(UTC2Long.class).dateFormat();
        if (!TextUtils.isEmpty(format)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            String date = String.valueOf(value);
            return simpleDateFormat.parse(date).getTime();
        }

        return 0;
    }

    private boolean date2longField(Field field) {
        return field.getAnnotation(UTC2Long.class) != null;
    }

    private void writeSingleString2ArrayField(JsonWriter out, Field field, T receiver) throws IllegalAccessException, IOException {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        String delimiter = field.getAnnotation(String2Array.class).delimiter();
        final Object value = field.get(receiver);
        out.name(field.getName());
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

    private void writeField(@NonNull Field f, @NonNull T receiver, @NonNull Object value) throws IllegalAccessException {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }

        Class<?> fieldType = f.getType();
        if (fieldType.equals(int.class)) {
            f.setInt(receiver, ((Number) value).intValue());
        } else if (fieldType.equals(long.class)) {
            f.setLong(receiver, ((Number) value).longValue());
        } else if (fieldType.equals(double.class)) {
            f.setDouble(receiver, ((Number) value).doubleValue());
        } else {
            f.set(receiver, value);
        }
    }

    private void field2Json(JsonWriter out, Field field, T receiver) throws IllegalAccessException, IOException {
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
        } else if (value instanceof Integer) {
            out.value((Integer) value);
        } else if (value instanceof Double) {
            out.value((Double) value);
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

    public static class NoteTypeAdapter extends CustomTypeAdapter<Note> {
        public NoteTypeAdapter() {
            super();
        }
    }

    public static class NotebookTypeAdapter extends CustomTypeAdapter<Notebook> {
        public NotebookTypeAdapter() {
            super();
        }
    }
}
