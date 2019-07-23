package com.codemobiles.cmscb

fun main() {

    // ------------------------------- Declaration -------------------------------
    // var เปลี่ยนแปลงค่าได้
    // implicit declaration
    var a1 = 1234
    var a2 = "1234"
    var a3 = true
    var a4 = 1.5

    // explicit declaration
    var b1:Int = 1234
    var b2:String = "1234"
    var b3:Boolean = true
    var b4:Float = 1.5F

    a1 = 5555
    b1 = 5555

    val c1 = 1234
    val d1:Int = 1234
    // val เปลี่ยนแปลงค่าไม่ได้
    // c1 = 5555
    // d1 = 5555

    val money = 1_000_000

    println("money: $money")


    // ------------------------------- Function -------------------------------

    normalFunction()
    argsFunction(10,5)
    println(returnFunction())

    // ------------------------------- Array (Fix size) -------------------------------
    var array1 = arrayOf(123,"123",true) // เก็บค่าแบบ value any เก็บค่าอะไรก็ได้ type อะไรก็ได้
    for (item in array1) {
        print(item)
    }

//    var array2 = arrayOf<Int>(123,456,789)
    var array2 = intArrayOf(123,456,789) // fix type
    for (item in array2) {
        print(item)
    }

    // ------------------------------- Array List(Dynamic size) -------------------------------
    var array3 = ArrayList<String>()
    array3.add("123")
    array3.add("456")
    array3.add("789")


    // ------------------------------- null safety -------------------------------
    println()
    val name: String? = null // ใส่ค่า null ได้
//    print(name!!) // unwrap

    when ("1") {
        "1", "222", "444" -> {

        }
        "2" -> {

        }
        "3" -> {

        }
        else -> {

        }
    }

    // ------------------------------- test new obj -------------------------------
    val obj1 = Person1("aaa","1234",22) // kotlin ไม่จำเป็นคำว่า new ขึ้นต้น

}



fun normalFunction() {
    println("normal")
}

fun argsFunction(x1: Int, x2: Int) {
    println("sum = ${x1 + x2}")
}


fun returnFunction():Int{
   return 50 * 20
}


// ------------------------------- class -------------------------------

// primary class || constructor overloading ถ้ามันเกิดต้องโยนค่าให้ทันที เพราะงั้นจะมีค่าทันท่ตอน obj เกิด
class Person1(val username:String, val password:String, val age:Int) {

    fun printUsername(){
        println(username)
    }

    fun printPassword(){
        println(password
        )
    }

    // ถูกเรียกครั้งแรกตอนที่ obj ถูกสร้าง
    init {
        if (username.length > 0) {

        }
    }
}

// secondary class ตอนเกิดมีทางเลือกแล้ว ไม่จำเป็นต้องระบุ parameter ตอนสร้าง สามารถมาเพิ่มทีหลังได้
// ข้อดีคือตอนเวลาเกิด เราสามารถเขียนดัก โดยใช้โลจิคได้
class Person2 {
    var username:String = ""
    var password:String = ""
    var age:Int = 0

    // ถูกเรียกครั้งแรกตอนที่ obj ถูกสร้าง
    init {
        if (username.length > 0) {

        }
    }

    constructor(){
        //default constructor
    }

    constructor(_username: String, _password: String){
        username = _username
        password = _password
    }
}

// data class ใช้บ่อยมาก แตกต่างกันตรงที่ตอนทำ equal มันจะเอา value ของ obj มาเช็คกัน
data class Person3(val username:String, val password:String, val age:Int) {

    fun printUsername(){
        println(username)
    }

    fun printPassword(){
        println(password
        )
    }

    // ถูกเรียกครั้งแรกตอนที่ obj ถูกสร้าง
    init {
        if (username.length > 0) {

        }
    }
}


