package com.coxautodev.graphql.tools



/**
 * @author Brandon Sara
 */
class MissingPropertySetterException @JvmOverloads constructor(
  val clazz: Class<*>,
  val setterName: String,
  throwable: Throwable? = null
) : Exception(
  "Unable able to find setter for the following property: ${clazz.name}.$setterName",
  throwable
)