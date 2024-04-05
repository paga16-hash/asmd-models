package pc.examples

export pc.modelling.PetriNet
import pc.utils.MSet

object PNReadersWriters:

  enum Place:
    case START, CHOICE, WAIT_READ, WAIT_WRITE, ME, READING, WRITING

  export Place.*
  export pc.modelling.PetriNet.*
  export pc.modelling.SystemAnalysis.*
  export pc.utils.MSet

  // DSL-like specification of a Petri Net
  def pnRW = PetriNet[Place](
    MSet(START) ~~> MSet(CHOICE),
    MSet(CHOICE) ~~> MSet(WAIT_READ),
    MSet(CHOICE) ~~> MSet(WAIT_WRITE),
    MSet(WAIT_READ, ME) ~~> MSet(ME, READING),
    MSet(WAIT_WRITE, ME) ~~> MSet(WRITING) ^^^ MSet(READING),
    MSet(READING) ~~> MSet(START),
    MSet(WRITING) ~~> MSet(START, ME)
  ).toSystem

@main def mainPNReadersWriters =
  import PNReadersWriters.*
  // example usage
  println(pnRW.paths(MSet(START, START, ME), 7).toList.mkString("\n"))
