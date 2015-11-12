
//import slick.driver.PostgresDriver.api._
import com.typesafe.slick.driver.ms.SQLServerDriver.api._
import slick.jdbc.meta.MTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


// The main application
object BasicSQLServerCalls extends App {
  val serverConnectionString = "jdbc:jtds:sqlserver://127.0.0.1:1433/AU;user=user;password=password"
  val driver = "net.sourceforge.jtds.jdbc.Driver"
  val db = Database.forURL(serverConnectionString, driver=driver)

  try {

    // The query interface for the Suppliers table
    val suppliers: TableQuery[Suppliers] = TableQuery[Suppliers]

    // the query interface for the Coffees table
    val coffees: TableQuery[Coffees] = TableQuery[Coffees]

    // Create the schema by combining the DDLs for the Suppliers and Coffees
    // tables using the query interfaces
    // Create the tables, including primary and foreign keys
    val setupAction: DBIO[Unit] = DBIO.seq(
      // Create the schema by combining the DDLs for the Suppliers and Coffees
      // tables using the query interfaces
      (suppliers.schema ++ coffees.schema).create,

      // Insert some suppliers
      suppliers += (101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"),
      suppliers += ( 49, "Superior Coffee", "1 Party Place", "Mendocino", "CA", "95460"),
      suppliers += (150, "The High Ground", "100 Coffee Lane", "Meadows", "CA", "93966"),

      coffees ++= Seq (
        ("Colombian",         101, 7.99, 0, 0),
        ("French_Roast",       49, 8.99, 0, 0),
        ("Espresso",          150, 9.99, 0, 0),
        ("Colombian_Decaf",   101, 8.99, 0, 0),
        ("French_Roast_Decaf", 49, 9.99, 0, 0)
      )
    )

    val tables:Future[Vector[MTable]] = db.run(MTable.getTables(suppliers.baseTableRow.tableName))

    tables.onSuccess {
      case s if ! s.isEmpty => println(s"Result: $s")
    }

//    if (tables.isEmpty) {
//      println("Table dont not exist, creating it")
//      Await.result(db.run(setupAction), Duration.Inf)
//    } else {
//      println("Table exists")
//    }

  } finally db.close
}

