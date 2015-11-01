
object Solution {

  private val replacements = Set('c', 'v','C','V')
  private val vowels = Vector('a','e','i','o','u')
  private val allVowels = (vowels ++ vowels.map(_.toUpper)).toSet
  private val consonants = 'a'.to('z').toVector.filter(!allVowels.contains(_))


  def main(args: Array[String]): Unit = {
    val userInput = scala.io.StdIn.readLine
    val answer = replaceConsonantsAndVowels(userInput)
                 .getOrElse("All letters must be c or v")
    println(answer)
  }

  def replaceConsonantsAndVowels(str: String): Option[String] = {
    if(str.exists(!replacements.contains(_))) {
      None
    } else {
   
      def replace(c : Char): Char = {
        c match {
          case 'v' => getRandomElement(vowels)
          case 'V' => getRandomElement(vowels).toUpper
          case 'c' => getRandomElement(consonants)
          case 'C' => getRandomElement(consonants).toUpper
          case _ => throw new RuntimeException("Something has gone terribly wrong")
        }
      }
      Some(str.map(replace(_)))
    }
  }

  /* Returns a random element in the passed Vector */
  private def getRandomElement[T](list : Vector[T]): T = {
    val rng = new scala.util.Random
    list(rng.nextInt(list.length))
  }
}
