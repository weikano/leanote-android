package com.wkswind.leanote.gson;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.wkswind.leanote.database.Note;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-12-6.
 */

public class NoteTypeAdapter extends CustomTypeAdapter<Note> {
    public NoteTypeAdapter(){
        super();
    }

    @Override
    public void write(JsonWriter out, Note value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        Class<? extends Note> clazz = value.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (!writeField(f)) {
                continue;
            }
            final String name = f.getName();
            try {
                Object fieldValue = f.get(value);
                if (fieldValue == null) {
                    continue;
                }
//                out.name(name);
                //接着判断字段是不是Tags和Files，这两者要转换成JSONArray
                if (name.equals("Tags") || name.equals("Files")) {
                    out.name(name);
                    String str = String.valueOf(fieldValue);
                    String[] array = str.split(",");
                    out.beginArray();
                    for (String item : array) {
                        out.value(item);
                    }
                    out.endArray();
                } else {
                    write(out, f, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        out.endObject();
    }

    @Override
    public Note read(JsonReader in) throws IOException {
        Object obj = parseWithObjectTypeAdapter(in);
        if(obj == null){
            return null;
        }
        if(obj instanceof LinkedTreeMap){
            Note note = new Note();
            LinkedTreeMap map = (LinkedTreeMap) obj;
            for(Iterator<Map.Entry> iter = map.entrySet().iterator();iter.hasNext();){
                Map.Entry entry = iter.next();
                String key = entry.getKey().toString();
                Object value = entry.getValue();
                Field field = getFieldByName(key);
                if(writeField(field)){
                    setFieldValue(field, note ,value);
                }
            }
        }
//        JsonToken token = in.peek();
//        if (token.equals(JsonToken.NULL)) {
//            return null;
//        }
//        Note note = new Note();
//        Class<Note> clazz = Note.class;
//        if(token.equals(JsonToken.BEGIN_OBJECT)){
//            in.beginObject();
//            token = in.peek();
//            while(!token.equals(JsonToken.END_OBJECT)){
//                String name = in.nextName();
//                try {
//                    Field field = clazz.getDeclaredField(name);
//                    if(field == null){
//                        continue;
//                    }
//                    field.setAccessible(true);
//                    if(token.equals(JsonToken.BEGIN_ARRAY)){
//                        in.beginArray();
//                        List<Object> list = new ArrayList<Object>();
//                        while (in.hasNext()) {
//                            list.add(read(in));
//                        }
//                        String result = TextUtils.join(",", list);
//                        field.set(note, result);
//                    }else{
//                        switch(token) {
//                            case STRING:
//                                field.set(note, in.nextString());
//                                break;
//                            case NUMBER:
//                                field.set(note, in.nextDouble());
//                                break;
//                            case BOOLEAN:
//                                field.set(note, in.nextBoolean());
//                                break;
//                            case END_ARRAY:
//                                in.endArray();
//                                break;
//                        }
//                    }
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//
//            }
//            in.endObject();
//        }
//        return note;
    }
}
