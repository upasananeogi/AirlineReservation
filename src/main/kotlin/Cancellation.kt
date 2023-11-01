import java.time.LocalDateTime
import java.util.Scanner
data class Database(val user: String, val flightno:String,val departureFlight: String, val returnFlight: String
                    , val time: LocalDateTime, val pnr :String, val Total :Double)

class Cancellation {
    val sc= Scanner(System.`in`)
    val flights = mutableListOf(
        Database("Upasana", "IN123", "DELHI", "BANGALORE", LocalDateTime.of(2023, 11, 1, 9, 0), "KOL",12000.0),
        Database("Shaunak", "IN234", "KOLKATA", "DELHI", LocalDateTime.of(2023, 11, 10, 14, 0), "SMX12",10000.0),
        Database("Ria", "IN232", "BANGALORE", "DELHI", LocalDateTime.of(2023, 11, 8, 8, 0), "MDK13",11000.0),
        Database("Mansie", "IN211", "DELHI", "KOLKATA", LocalDateTime.of(2023, 11, 15, 8, 0), "MED12",13000.0)
    )
   fun Givedetails(){
       println("Enter your pnr")
       val can = readln()
       val matchingBooking = flights.find { it.pnr==can }
       if (matchingBooking != null) {
           println(" ${matchingBooking.user} - ${matchingBooking.departureFlight} to ${matchingBooking.returnFlight},  Price: Rs${matchingBooking.Total}")

           val refundAmount = Refund(matchingBooking.Total)
           println("Booking with PNR $can has been canceled and removed from the database.")
           println("Total paid: ${matchingBooking.Total}")
           println("After cancellation fee, the refund amount is $refundAmount")
           flights.remove(matchingBooking)
       }
       else {
           println("Booking with PNR $can was not found in the database.")
       }

   }

    fun Refund(total: Double):Double{
        val cancellationfee= 0.3
        val refundAmount= total*(1-cancellationfee)
        return refundAmount


    }

}
