package com.coxautodev.graphql.tools



/**
 * @author Brandon Sara
 */
class MissingPropertyException @JvmOverloads constructor(
  val clazz: Class<*>,
  val propName: String,
  throwable: Throwable? = null
) : Exception(
  "Unable able to find the following property: ${clazz.name}.$propName",
  throwable
)
