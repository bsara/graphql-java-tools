@file:JvmName("GraphQLInputUtils")

package com.coxautodev.graphql.tools


import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField



/**
 * @author Brandon Sara
 * TODO: Need to be able to handle nested GraphQLInput objects found in `input`
 */
fun <T : Any> copyFromInput(input: GraphQLInput, obj: T, objPropPaths: Map<String, Any> = mapOf()): T {
  input.presentProps.forEach { propName, inputValue ->
    val objPropPath = (objPropPaths[propName] ?: propName)

    if (objPropPath is Function2<*, *, *>) {
      (objPropPath as Function2<T, *, Unit>)(obj, inputValue)
    } else if (objPropPath is String) {
      val finalPropPath = (objPropPath ?: propName)

      try {
        _callPropertySetter(obj, finalPropPath.split("."), inputValue)
      } catch (ex: MissingPropertyException) {
        if (ex.propName != propName) {
          throw ex
        }
      }
    }
  }

  return obj
}


/**
 * @author Brandon Sara
 * TODO: Convert to use reflectasm
 */
private fun _callPropertySetter(obj: Any, propPathParts: List<String>, newValue: Any?) {
  val propName = propPathParts.first()
  val objProp = obj::class.declaredMemberProperties.firstOrNull { it.name == propName }


  if (objProp == null || objProp !is KMutableProperty1<*, *>) {
    throw MissingPropertyException(obj.javaClass, propName)
  }


  if (propPathParts.size < 2) {
    (objProp as KMutableProperty1<Any, Any?>).set(obj, newValue)
    return
  }


  var nestedObj = (objProp as KProperty1<Any, Any?>).get(obj)

  if (nestedObj == null) {
    nestedObj = objProp.javaField!!.type.newInstance()

    (objProp as KMutableProperty1<Any, Any?>).set(obj, nestedObj)
  }


  _callPropertySetter(nestedObj!!, propPathParts.slice(1..(propPathParts.size - 1)), newValue)
}
