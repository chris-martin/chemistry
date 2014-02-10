package chemistry

sealed case class Element(
  number: Int,
  mass: Double,
  symbol: String,
  name: String
)
