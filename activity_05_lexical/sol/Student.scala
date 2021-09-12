class Student(private var id : Integer, private var name : String) {

  def getId(): Integer = id

  def getName(): String = name

  def setName(name: String) {
    this.name = name
  }

  override def toString: String = id + ":" + name
}