package org.alee.reflex;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**********************************************************
 *
 * @author: MY.Liu
 * @date: 2020/7/24
 * @description: xxxx
 *
 *********************************************************/
public final class ReflexClass {

    private static HashMap<Class<?>, Constructor<?>> REFLEX_TYPES = new HashMap<>();

    static {
        try {
            REFLEX_TYPES.put(ReflexObject.class, ReflexObject.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexByte.class, ReflexByte.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexShort.class, ReflexShort.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexInt.class, ReflexInt.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexLong.class, ReflexLong.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexFloat.class, ReflexFloat.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexDouble.class, ReflexDouble.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexBoolean.class, ReflexBoolean.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexChar.class, ReflexChar.class.getConstructor(Class.class, Field.class));

            REFLEX_TYPES.put(ReflexStaticObject.class, ReflexStaticObject.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticByte.class, ReflexStaticByte.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticShort.class, ReflexStaticShort.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticInt.class, ReflexStaticInt.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticLong.class, ReflexStaticLong.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticFloat.class, ReflexStaticFloat.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticDouble.class, ReflexStaticDouble.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticBoolean.class, ReflexStaticBoolean.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticChar.class, ReflexStaticChar.class.getConstructor(Class.class, Field.class));


            REFLEX_TYPES.put(ReflexMethod.class, ReflexMethod.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexStaticMethod.class, ReflexStaticMethod.class.getConstructor(Class.class, Field.class));
            REFLEX_TYPES.put(ReflexConstructor.class, ReflexConstructor.class.getConstructor(Class.class, Field.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> load(Class<?> mappingClass, String realClassName) {
        try {
            return load(mappingClass, Class.forName(realClassName));
        } catch (Exception ignore) {
            return null;
        }
    }


    public static Class<?> load(Class<?> mappingClass, Class<?> realClass) {
        // 获取所有声明的变量
        Field[] fields = mappingClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                // 检查变量是否被修饰为静态变量
                if (Modifier.isStatic(field.getModifiers())) {
                    // 核心 从REFLEX_TYPES中查找变量对应的反射结构
                    Constructor<?> constructor = REFLEX_TYPES.get(field.getType());
                    if (null == constructor) {
                        continue;
                    }
                    // 核心 为映射表中的字段赋值
                    field.set(null, constructor.newInstance(realClass, field));
                }
            } catch (Exception ignore) {
            }
        }
        return realClass;
    }
}
