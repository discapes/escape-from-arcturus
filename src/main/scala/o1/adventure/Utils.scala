package o1.adventure

trait Named:
  def name: String

object Utils:
  // find a named object in a collection by its name (case-insensitive, ignore spaces)
  // if there's no match then it tries to match the prefix, succeeding if there's exactly one match
  // if there's multiple identical items, it ignores the duplicates
  def findByName[T <: Named](collection: Iterable[T], name: String): Option[T] =
    val cleanedName = name.toLowerCase.replaceAll("\\s+", "")
    val exactMatches = collection.filter(item =>
      item.name.toLowerCase.replaceAll("\\s+", "") == cleanedName
    )
    if exactMatches.nonEmpty then Some(exactMatches.head)
    else
      val prefixMatches = collection.filter(item =>
        item.name.toLowerCase.replaceAll("\\s+", "").startsWith(cleanedName)
      )
      if prefixMatches.size == 1 || prefixMatches
          .map(_.name.toLowerCase.replaceAll("\\s+", ""))
          .toSet
          .size == 1
      then Some(prefixMatches.head)
      else None
