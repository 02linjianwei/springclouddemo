package com.util;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.TableInfoHelper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MybatisMetaDataUtil {
     private static final Map<Class<?>,MybatisMetaDataUtil.EntityField> VERSION_FIELD_CACHE = new ConcurrentHashMap<>();
    public MybatisMetaDataUtil() {

    }

    public static TableInfo getTableInfo(Class entityClass) {
        return TableInfoHelper.getTableInfo(entityClass);
    }
    private static class EntityField{
        private Field field;
        private boolean version;
        private String columnName;

        EntityField(Field field,boolean version) {
            this.field = field;
            this.version = version;
        }
        EntityField(Field field,boolean version,String columnName) {
            this.field = field;
            this.version = version;
            this.columnName = columnName;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public boolean isVersion() {
            return version;
        }

        public void setVersion(boolean version) {
            this.version = version;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }
}
