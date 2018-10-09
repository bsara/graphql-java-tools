package com.coxautodev.graphql.tools


import kotlin.reflect.KProperty



/**
 * @author Brandon Sara
 */
abstract class GraphQLInput {

  val presentProps = mutableMapOf<String, Any?>()



  protected fun <T> prop(): GraphQLInputPropDelegate<T?> {
    return this.prop(null)
  }


  protected fun <T> prop(defaultValue: T): GraphQLInputPropDelegate<T> {
    return GraphQLInputPropDelegate(defaultValue)
  }


  @JvmOverloads
  protected fun <T> javaProp(propName: String, defaultValue: T? = null): GraphQLInputJavaPropDelegate<T?> {
    return GraphQLInputJavaPropDelegate(propName, defaultValue)
  }



  protected class GraphQLInputPropDelegate<TProp>(defaultValue: TProp) {

    private var _value: TProp = defaultValue


    operator fun setValue(gqlInput: GraphQLInput, property: KProperty<*>, newValue: TProp) {
      this._value = newValue

      gqlInput.presentProps[property.name] = newValue
    }


    operator fun getValue(gqlInput: GraphQLInput, property: KProperty<*>): TProp {
      return (gqlInput.presentProps[property.name] as TProp)
    }
  }



  protected class GraphQLInputJavaPropDelegate<TProp>(private val propName: String, defaultValue: TProp) {

    private var _value: TProp = defaultValue


    fun setValue(gqlInput: GraphQLInput, newValue: TProp) {
      this._value = newValue

      gqlInput.presentProps[this.propName] = newValue
    }


    fun getValue(gqlInput: GraphQLInput): TProp {
      return (gqlInput.presentProps[this.propName] as TProp)
    }
  }
}