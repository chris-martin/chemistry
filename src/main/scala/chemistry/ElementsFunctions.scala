package chemistry

trait ElementsFunctions {

  def atomicMass(symbol: String)(implicit elements: Elements): Double =
    elements(symbol).mass
}
