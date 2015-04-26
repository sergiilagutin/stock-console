package app.service

import org.scalatest.FunSuite

class StockServiceTest extends FunSuite {

  trait EmptyStock extends StockService {
    Stock.stock.clear()
  }

  test("deposit new item") {
    new EmptyStock {
      assert(10 === deposit("item", 10))
    }
  }

  test("deposit existent item") {
    new EmptyStock {
      deposit("item", 10)
      assert(15 === deposit("item", 5))
    }
  }

  test("buy unreal item") {
    new EmptyStock {
      assert(Right("Item [item] not found") === buy("item", 10))
    }
  }

  test("buy with insufficient amount") {
    new EmptyStock {
      deposit("item", 10)
      assert(Right("Insufficient amount") === buy("item", 20))
    }
  }

  test("buy") {
    new EmptyStock {
      deposit("item", 10)
      assert(Left(8) === buy("item", 2))
    }
  }

  test("listAll") {
    new EmptyStock {
      deposit("item2", 20)
      deposit("item3", 15)
      deposit("item1", 10)

      val expected = Seq(
        ("item1", 10),
        ("item2", 20),
        ("item3", 15)
      )

      assert(expected === listAll)
    }
  }

}