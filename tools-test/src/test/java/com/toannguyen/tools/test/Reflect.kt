package com.toannguyen.tools.test
import io.mockk.mockkClass
import org.koin.core.Koin
import org.koin.core.error.NoBeanDefFoundException
import java.lang.reflect.Field
import java.lang.reflect.Modifier
object Reflect {
    /**
     * Create instance of class with parameter value lookup from koin scope
     */
    @Suppress("unchecked_cast")
    fun <T> create(scope: Koin, clazz: Class<T>): T {
        val constructor = clazz.constructors.firstOrNull()
            ?: clazz.declaredConstructors.firstOrNull()
            ?: error("Not found constructor for ${clazz.simpleName}")
        val paramTypes = constructor.genericParameterTypes
        return try {
            constructor.newInstance(
                *paramTypes.map {
                    val paramClazz = (it as Class<*>).kotlin
                    try {
                        scope.get(paramClazz)
                    } catch (e: NoBeanDefFoundException) {
                        try {
                            mockkClass(paramClazz)
                        } catch (e: Throwable) {
                            throw IllegalArgumentException(
                                "Can not mock ${paramClazz.java.simpleName}\nDetail: ${e.message}",
                            e
                            )
                        }
                    }
                }.toTypedArray()
            ) as T
        } catch (e: Throwable) {
            println("Error lookup for ${clazz.name}")
            throw e
        }
    }
    fun set(obj: Any, fieldName: String, value: Any) {
        with(getField(obj, fieldName)) {
            isAccessible = true
            val modifiersField = Field::class.java.getDeclaredField("modifiers")
            modifiersField.isAccessible = true
            modifiersField.setInt(this, modifiers and Modifier.FINAL.inv())
            set(obj, value)
            isAccessible = false
        }
    }
    fun getField(obj: Any, fieldName: String): Field {
        return getField(obj.javaClass, fieldName, 5)
    }
    private fun getField(clazz: Class<*>, fieldName: String, deep: Int): Field {
        fun tryGet(callback: () -> Field) = try {
            callback()
        } catch (e: Throwable) {
            null
        }
        fun fail(): Nothing = error("Not found $fieldName in ${clazz.simpleName}")
        var field = tryGet { clazz.getDeclaredField(fieldName) }
        if (field != null) return field
        field = tryGet { clazz.getField(fieldName) }
        if (field != null) return field
        val superClazz = clazz.superclass
        if (superClazz != null) {
            val nextDeep = deep - 1
            if (deep <= -1) fail()
            return getField(superClazz, fieldName, nextDeep)
        }
        fail()
    }
}














