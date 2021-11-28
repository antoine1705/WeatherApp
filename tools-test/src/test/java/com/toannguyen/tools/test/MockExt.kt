package com.toannguyen.tools.test

import io.mockk.mockk
import io.mockk.spyk
import kotlin.reflect.KClass

inline fun <reified T : Any> BaseTest.mock(relaxed: Boolean = false): T {
    val value = mockk<T>(relaxed = relaxed)
    getKoin().declare(value, secondaryTypes = listOf(T::class))
    return value
}

inline fun <reified T : Any> spy(obj: Any, objToCopy: T, fieldName: String): T {
    val spy = spyk(objToCopy)
    Reflect.set(obj, fieldName, spy)
    return spy
}

inline fun <reified T : Any> BaseTest.bean(factory: () -> T): T {
    val bean = factory()
    getKoin().declare(bean, secondaryTypes = listOf(T::class))
    return bean
}

inline fun <reified T : Any> mock(obj: Any, fieldName: String): T {
    val mockValue = mockk<T>()
    Reflect.set(obj, fieldName, mockValue as Any)
    return mockValue
}

inline fun <reified T> BaseTest.autoWire(preCall: () -> Unit = {}): T {
    preCall()
    val clazz = T::class.java
    return Reflect.create(getKoin(), clazz)
}

inline fun <reified T : Any> BaseTest.declare(realizationClazz: KClass<out T>): T {
    return Reflect.create(getKoin(), realizationClazz.java).also {
        getKoin().declare(it, secondaryTypes = listOf(T::class))
    }
}

inline fun <reified T : Any> getPrivateField(obj: Any, fieldName: String): T? {
    val field = Reflect.getField(obj, fieldName)
    field.isAccessible = true

    return (field.get(obj) as? T)
}

fun setPrivateValue(obj: Any, fieldName: String, value : Any) {
    Reflect.set(obj, fieldName, value)
}
