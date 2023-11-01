import java.time.LocalDateTime
import java.util.*
import java.io.File

val sc = Scanner(System.`in`)

data class Flight(val choice: Int, val flightNumber: String, val origin: String, val destination: String, val departureTime: LocalDateTime, val price: Double)

data class BookingDetails(var user: String, val departureFlights: ArrayList<Flight>, val returnFlights: ArrayList<Flight?>)

class Booking {
    val availableSeats = mutableListOf("1A", "1B", "2A", "2B", "3A", "3B", "4A", "4B")
    val flights = listOf(
        Flight(1, "IN123", "DELHI", "BANGALORE", LocalDateTime.of(2023, 11, 1, 9, 0), 12000.0),
        Flight(2, "IN234", "KOLKATA", "DELHI", LocalDateTime.of(2023, 11, 10, 14, 0), 8050.0),
        Flight(3, "IN232", "BANGALORE", "DELHI", LocalDateTime.of(2023, 11, 8, 8, 0), 12050.0),
        Flight(4, "IN211", "DELHI", "KOLKATA", LocalDateTime.of(2023, 11, 15, 8, 0), 8050.0)
    )

    val pnr: String = UUID.randomUUID().toString()
    val bookingDetails = BookingDetails("", ArrayList(), ArrayList())
    var flag = true

    fun CustomerChoosing() {
        while (flag) {
            val selectedFlight = SelectDepartureFlight()

            if (selectedFlight != null) {
                val user = readUserName()
                val returnChoice = readReturnChoice()

                val returnFlight = if (returnChoice) {
                    readReturnFlight(flights)
                } else {
                    null
                }

                bookingDetails.user = user
                bookingDetails.departureFlights.add(selectedFlight)
                bookingDetails.returnFlights.add(returnFlight)

                println("Choose Seat No which is available:")
                val selectedSeat = selectSeat()
                println("Booking Successful!")

                print("Do you want to make another booking? (yes/no): ")
                val choice = sc.next()
                flag = choice.equals("yes", ignoreCase = true)
            }
        }
        displayBookingDetailsToFile(bookingDetails)
    }

    fun readUserName(): String {
        print("Enter your name: ")
        return sc.next()
    }

    fun readReturnChoice(): Boolean {
        print("Do you want to book a return flight? (yes/no): ")
        val choice = sc.next()
        return choice.equals("yes", ignoreCase = true)
    }

    fun readReturnFlight(flights: List<Flight>): Flight? {
        println("Available Return Flights:")
        flights.forEachIndexed { index, flight ->
            println("${index + 1}. ${flight.flightNumber} - ${flight.origin} to ${flight.destination}, Departure Time: ${flight.departureTime}, Price: $${flight.price}")
        }
        print("Choose a return flight (enter the number or 0 to skip): ")
        val flightChoice = sc.nextInt()

        return if (flightChoice == 0) {
            null
        } else {
            flights.find { it.choice == flightChoice }
        }
    }

    fun displayAvailableSeats() {
        println("Available Seats:")
        availableSeats.forEach { seat -> println(seat) }
    }

    fun selectSeat() {
        if (availableSeats.isNotEmpty()) {
            displayAvailableSeats()
            println("Select a seat from the available seats:")
            val selectedSeat = sc.next()

            if (availableSeats.contains(selectedSeat)) {
                availableSeats.remove(selectedSeat)
                println("Seat $selectedSeat selected.")
            } else {
                println("Invalid or already booked seat.")
            }
        } else {
            println("No available seats.")
        }
    }

    fun displayBookingDetailsToFile(booking: BookingDetails) {
        val totalPrice = booking.departureFlights.sumByDouble { it.price } + booking.returnFlights.filterNotNull().sumByDouble { it.price }
        val bookingTime: LocalDateTime = LocalDateTime.now()

        val bookingInfo = "Booking Details:\n" +
                "PNR: $pnr\n" +
                "User: ${booking.user}\n" +
                "Booking Time: $bookingTime\n\n" +
                "Departure Flights:\n"

        val departureFlightInfo = booking.departureFlights.joinToString("\n") { flight ->
            "${flight.flightNumber} - ${flight.origin} to ${flight.destination}, " +
                    "Departure Time: ${flight.departureTime}, Price: $${flight.price}"
        }

        val returnFlightInfo = if (!booking.returnFlights.isNullOrEmpty()) {
            "\nReturn Flights:\n" +
                    booking.returnFlights.filterNotNull().joinToString("\n") { flight ->
                        "${flight.flightNumber} - ${flight.origin} to ${flight.destination}, " +
                                "Departure Time: ${flight.departureTime}, Price: $${flight.price}"
                    }
        } else {
            ""
        }
        val totalPriceInfo = "\nTotal Price: $totalPrice"
        val bookingDetails = bookingInfo + departureFlightInfo + returnFlightInfo + totalPriceInfo
        val fileName = "booking_details.txt"
        File(fileName).writeText(bookingDetails)
        println("Booking details have been saved to $fileName")
    }

    fun SelectDepartureFlight(): Flight? {
        println("Available Flights:")
        flights.forEachIndexed { index, flight ->
            println("${flight.choice}. ${flight.flightNumber} - ${flight.origin} to ${flight.destination}, Departure Time: ${flight.departureTime}, Price: Rs${flight.price}")
        }
        print("Choose a flight (enter the number): ")
        val flightChoice = sc.nextInt()
        return flights.find { it.choice == flightChoice }
    }
}


