import java.util.Scanner
fun main() {
    val f = Company()
    f.dispaly()
    println("------------------")
    var s = SignUp()
    s.run()
    println("Choose (1) : For Booking/ Choose (2) : For Cancellation")
    val sc= Scanner(System.`in`)
    val choice= sc.nextInt()
    if(choice==1) {
        val booking = Booking()
        booking.CustomerChoosing()

    }
    else if(choice==2){
        val c= Cancellation()
        c.Givedetails()

    }
}
