package chemistry

sealed class Elements private (val seq: Seq[Element]) {

  def this(it: Iterable[Element]) =
    this(it.toSeq.sortBy(_.number))

  private def index[A](f: Element => A): Map[A, Element] =
    seq.map(x => (f(x), x)).toMap

  val byNumber = index(_.number)

  val bySymbol = index(_.symbol)

  def apply(number: Int): Element = byNumber(number)

  def apply(symbol: String): Element = bySymbol(symbol)

  override def toString = seq.toString()
}

object Elements {

  def apply(els: Iterable[Element]): Elements = new Elements(els)

  def apply(): Elements = defaultElements

  private lazy val defaultElements: Elements = {
    val input = new java.io.InputStreamReader(
      classOf[Elements] getResourceAsStream "elements.txt")
    val parseResult = Parser.parseAll(Parser.file, input)
    parseResult getOrElse { throw new Exception(parseResult.toString) }
  }

  private object Parser extends scala.util.parsing.combinator.RegexParsers {

    override val whiteSpace = "".r

    lazy val file: Parser[Elements] = rep(line) ^^ {
      elementOptions => Elements(elementOptions.flatten)
    }

    lazy val line: Parser[Option[Element]] = (element | ignoredLine) ^^ {
      case e: Element => Some(e)
      case _ => None
    }

    lazy val ignoredLine: Parser[Unit] = "(\\s*(#[^\\n]*)?)?\\n".r ^^ { _ => }

    lazy val element: Parser[Element] =
      (number <~ tab) ~ (symbol <~ tab) ~ (name <~ tab) ~
      (mass <~ "(\\t[^\\n]*)?\\n".r) ^^
      { case number ~ symbol ~ name ~ mass =>
        Element( number = number, symbol = symbol, name = name, mass = mass ) }

    lazy val number: Parser[Int] = "\\d+".r ^^ (_.toInt)

    lazy val symbol: Parser[String] = "[^\\t]+".r

    lazy val name: Parser[String] = "[^\\t]+".r

    lazy val mass: Parser[Double] = mass2 | ("[" ~> mass2 <~ "]")

    lazy val mass2: Parser[Double] =
      """[\d\.]+""".r <~ opt("""\(\d+\)""".r) ^^ (_.toDouble)

    lazy val tab: Parser[Unit] = "\\t".r ^^ { _ => }
  }
}
