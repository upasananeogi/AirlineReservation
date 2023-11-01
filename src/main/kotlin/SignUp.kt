import java.util.Scanner

data class User(val username: String, val password: String,val email:String)

class SignUp {
    val users = mutableListOf<User>(
        User("Upasana", "upu123","@gmail"),
        User("Shaunak", "shaunak123","@gmail")
    )
    val scanner = Scanner(System.`in`)
    var flag = true

    fun run() {
        while (flag) {
            println("Choose an option:")
            println("1. Signup")
            println("2. Signin")
            println("3. Exit")

            val choice = scanner.nextInt()

            when (choice) {
                1 -> signup()
                2 -> signin()
                3 -> {
                    println("Exiting the program.")
                    flag = false
                }

                else -> println("Invalid choice. Please choose again.")
            }
        }
    }

    fun signup() {
        print("Enter a username: ")
        val username = scanner.next()
        print("Enter a password: ")
        val password = scanner.next()
        print("Enter email: ")
        val email=scanner.next()
        val newUser = User(username, password,email)
        users.add(newUser)

        println("Signup successful!")
        flag = false
    }

    fun signin() {
        print("Enter your username: ")
        val username = scanner.next()
        print("Enter your password: ")
        val password = scanner.next()

        val user = users.find { it.username == username }

        if (user != null && user.password == password) {
            println("Signin successful! Welcome, $username.")
        } else {
            println("Invalid username or password. Signin failed.")
        }
        flag = false
    }
}