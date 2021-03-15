package org.json4s

/**
 * Use a map of keys as type hints.  Values may not be mapped by multiple keys
 */
case class MappedTypeHints(hintMap: Map[Class[_], String], override val typeHintFieldName: String = "jsonClass")
  extends TypeHints {
  require(hintMap.size == hintMap.values.toList.distinct.size, "values in type hint mapping must be distinct")

  override val hints: List[Class[_]] = hintMap.keys.toList
  private[this] val lookup: Map[String, Class[_]] = hintMap.map(_.swap)

  def hintFor(clazz: Class[_]) = hintMap.get(clazz)
  def classFor(hint: String, parent: Class[_]) = lookup.get(hint).filter(parent.isAssignableFrom)
}
