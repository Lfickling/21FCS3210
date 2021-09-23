import org.graphstream.graph.{Edge, Node}
import org.graphstream.graph.implementations.{MultiGraph, SingleGraph, SingleNode}
import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Student: Letitia Fickling
 * Description: Homework 01 - PLGraph
 */

object PLGraphOriginal {

  val PL_CSV_FILE = "pl.csv"
  val USER_DIR    = System.getProperty("user.dir")
  val STYLE       = "stylesheet.css"

  def main(args: Array[String]): Unit = {

    // create the graph
    val graph = new MultiGraph("PL")
    graph.addAttribute("ui.stylesheet", "url('file://" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.antialias")

    // TODO: parse the PL_CSV_FILE to create a directed graph of PLs
    val in = Source.fromFile(PL_CSV_FILE)
    for (line <- in.getLines()) {
      val data: Array[String] = line.split(",")
      val id = data(0).toInt
      val name = data(1)
      println(f"id: $id - name: $name")
    }
    in.close()

    // create nodes
    var node: Node = graph.addNode("A")
    node.addAttribute("ui.label", "A")
    node = graph.addNode("B")
    node.addAttribute("ui.label", "B")
    node = graph.addNode("C")
    node.addAttribute("ui.label", "C")

    // create edges
    var edge: Edge = graph.addEdge("AB", "A", "B", true)
    edge = graph.addEdge("BC", "B", "C", true)
    // alternatively
    graph.addEdge[Edge]("CA", "C", "A", true)

    // display the graph
    graph.display()
  }
}
