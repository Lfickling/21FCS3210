object Example {
  def main(args: Array[String]) = {
    val john = new Student(101, "John")
    println(john)
    val mary = new Student(202, "Marie")
    mary.setName("Mary")
    println(mary)
  }
}