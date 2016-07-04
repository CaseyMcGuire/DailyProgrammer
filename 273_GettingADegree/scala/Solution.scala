import scala.io.Source._

object Main {

  def main(args: Array[String]): Unit = { 
    //get our inputs
    val lines: List[String] = stdin.getLines.toList

    //split each line to its component parts: (number, input type, output type)
    val inputs: List[Tuple3[Double, String, String]] = {
      val numAndUnits = lines.map(_.span(!Character.isLetter(_)))
      numAndUnits.map(x => {
        val (num, inputOutput) = x
        val (input, output) = inputOutput.split("") match { case Array(in, out) => (in, out) }
        (num.toDouble, input, output)
      })
    }

    //get our answers
    val answers: List[String] = inputs.map(input => {
      val (num, in, out) = input
      (getConversionUnit(in), getConversionUnit(out)) match {
        case (Some(x), Some(y)) => {
          ConversionUnit.convert(x, y, num) match {
            case Some(convertedNum) => f"$convertedNum%2.0f" + out
            case _ => "No candidate for conversion"
          }
        }
        case (None, Some(_)) => "Input type not known" 
        case (Some(_), None) => "Output type not known"
        case _ => "Neither input type nor output type known" 
      }
    })


    //print our answers
    answers.foreach(println)

  }

  def getConversionUnit(str: String): Option[ConversionUnit] = {
    str match {
      case "r" => Some(Radian)
      case "d" => Some(Degree)
      case "c" => Some(Celsius)
      case "f" => Some(Fahrenheit)
      case "k" => Some(Kelvin)
      case _ => None
    }
  }
}

sealed abstract class ConversionUnit
sealed trait Angle
case object Degree extends ConversionUnit with Angle
case object Radian extends ConversionUnit with Angle

sealed trait Temperature
case object Celsius extends ConversionUnit with Temperature
case object Fahrenheit extends ConversionUnit with Temperature
case object Kelvin extends ConversionUnit with Temperature

object ConversionUnit {

  def convert(inputUnit: ConversionUnit, outputUnit: ConversionUnit, num: Double): Option[Double] = {
    (inputUnit, outputUnit) match {
      case (x: Angle, y: Angle) => Some(convertAngle(x, y, num))
      case (x: Temperature, y: Temperature) => Some(convertTemperature(x, y, num))
      case _ => None
    }
  }

  def convertAngle(from: Angle, to: Angle, num: Double): Double = 
    (from, to) match {
      case (Degree, Radian) => num * (Math.PI / 180.0)
      case (Radian, Degree) => num * (180.0 / Math.PI)
      case _ => num
    }

  def convertTemperature(from: Temperature, to: Temperature, num: Double): Double =
    (from, to) match {
      case (x: Temperature, y: Temperature) if x == y => num
      case (Kelvin, Celsius) => num - 273.15
      case (Kelvin, Fahrenheit) => num * (9.0/5.0) - 459.67
      case (Celsius, Kelvin) => num + 273.15
      case (Fahrenheit, Kelvin) => (num + 459.67) * (5.0 / 9.0)
      case (Celsius, _) => convertTemperature(Kelvin, to, convertTemperature(Celsius, Kelvin, num))
      case (Fahrenheit, _) => convertTemperature(Kelvin, to, convertTemperature(Fahrenheit, Kelvin, num))
    }

}
