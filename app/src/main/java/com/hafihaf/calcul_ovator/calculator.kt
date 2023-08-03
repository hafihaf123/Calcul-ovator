package com.hafihaf.calcul_ovator

import java.util.Scanner
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

internal object Calc {
    @JvmStatic
    fun main(args: Array<String>) {
        val sc = Scanner(System.`in`)
        print("Enter an expression: ")
        val ex = sc.nextLine()
        val res: String = if (ex.toCharArray()[0] == '/') {
            val adv = Adv()
            when (ex) {
                "/" -> {
                    println("options:")
                    println("1. convert (c)")
                    println("2. bodies (b)")
                    println("3. rule of three (Ro3)")
                    println("4. quadratic function (q)")
                    println("5. cube (cb)")
                    println("6. prism (p)")
                    when (val option = sc.nextLine()) {
                        "1", "convert", "c" -> adv.convert()
                        "2", "b", "bodies" -> adv.bodies()
                        "3", "Ro3", "rule of three" -> adv.ruleOf3()
                        "4", "q", "quadratic" -> adv.quadratic()
                        "5", "cb", "cube" -> adv.cube()
                        "6", "p", "prism" -> adv.prism()
                        else -> throw RuntimeException("Unknown option: $option")
                    }
                }

                "/convert", "/unit" -> adv.convert()
                "/bodies", "/body" -> adv.bodies()
                "/Ro3" -> adv.ruleOf3()
                "/quadratic", "/quadr" -> adv.quadratic()
                "/cube" -> adv.cube()
                "/prism" -> adv.prism()
                else -> throw RuntimeException("Unknown command: $ex")
            }
        } else {
            val expression = Expression()
            expression.calc(ex)
        }
        println(res)
    }
}

internal class Expression {
    fun calc(ex: String): String {
        val result = eval(ex)
        return "$ex = $result"
    }

    companion object {
        fun eval(str: String): Double {
            return object : Any() {
                var pos = -1
                var ch = 0
                fun nextChar() {
                    ch = if (++pos < str.length) str[pos].code else -1
                }

                fun skip(charToSkip: Char): Boolean {
                    while (ch == ' '.code) nextChar()
                    if (ch == charToSkip.code) {
                        nextChar()
                        return true
                    }
                    return false
                }

                fun parse(): Double {
                    nextChar()
                    val x = parseExpression()
                    if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                    return x
                }

                fun parseExpression(): Double {
                    var x = parseTerm()
                    while (true) {
                        if (skip('+')) x += parseTerm() // addition
                        else if (skip('-')) x -= parseTerm() // subtraction
                        else return x
                    }
                }

                fun parseTerm(): Double {
                    var x = parseFactor()
                    while (true) {
                        if (skip('*')) x *= parseFactor() // multiplication
                        else if (skip('/')) x /= parseFactor() // division
                        else return x
                    }
                }

                fun parseFactor(): Double {
                    if (skip('+')) return parseFactor() // unary plus
                    if (skip('-')) return -parseFactor() // unary minus
                    var x: Double
                    val startPos = pos
                    if (skip('(')) { // parentheses
                        x = parseExpression()
                        skip(')')
                    } else if (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) { // numbers
                        while (ch >= '0'.code && ch <= '9'.code || ch == '.'.code) nextChar()
                        x = str.substring(startPos, pos).toDouble()
                    } else if (ch >= 'a'.code && ch <= 'z'.code) { // functions
                        while (ch >= 'a'.code && ch <= 'z'.code) nextChar()
                        val func = str.substring(startPos, pos)
                        x = parseFactor()
                        x = when (func) {
                            "sqrt" -> sqrt(x)
                            "sin" -> sin(Math.toRadians(x))
                            "cos" -> cos(Math.toRadians(x))
                            "tan" -> tan(Math.toRadians(x))
                            "ln" -> ln(x)
                            "log" -> log10(x)
                            "fact" -> factorial(x)
                            else -> throw RuntimeException("Unknown function: $func")
                        }
                    } else {
                        throw RuntimeException("Unexpected: " + ch.toChar())
                    }
                    if (skip('^')) x = x.pow(parseFactor()) // exponentiation
                    return x
                }
            }.parse()
        }

        private fun factorial(x: Double): Double {
            return if (x < 0) throw RuntimeException("factorial: invalid number: $x")
            else if (x == floor(x)) {
                var factorial = 1
                var i = 1
                while (i <= x) {
                    factorial *= i
                    i++
                }
                factorial.toDouble()
            } else throw RuntimeException("factorial: invalid number: $x")
        }
    }
}

internal class Adv {
    fun prism(): String {
        val sc = Scanner(System.`in`)
        print("side a: ")
        val a = sc.nextDouble()
        print("side b: ")
        val b = sc.nextDouble()
        print("side c: ")
        val c = sc.nextDouble()
        val volume = a * b * c
        val surface = 2 * (a * b + b * c + a * c)
        return "volume: $volume\nsurface: $surface"
    }

    fun cube(): String {
        val sc = Scanner(System.`in`)
        print("side a: ")
        val a = sc.nextDouble()
        val volume = a.pow(3.0)
        val surface = 6 * a.pow(2.0)
        return "volume: $volume\nsurface: $surface"
    }

    fun quadratic(): String {
        val sc = Scanner(System.`in`)
        print("a*x^2 + b*x + c = 0\na: ")
        val a = sc.nextDouble()
        print("b: ")
        val b = sc.nextDouble()
        print("c: ")
        val c = sc.nextDouble()
        val discr = b * b - 4 * a * c
        return if (discr < 0) "no root" else if (discr == 0.0) {
            val x = -b / (2 * a)
            "one root: x = $x"
        } else {
            val sqrtD = sqrt(discr)
            val x1 = (-b + sqrtD) / (2 * a)
            val x2 = (-b - sqrtD) / (2 * a)
            "two roots: x1 = $x1 ; x2 = $x2"
        }
    }

    fun ruleOf3(): String {
        val sc = Scanner(System.`in`)
        print("a ... b\nc ... x\na:")
        val a = sc.nextDouble()
        print("b: ")
        val b = sc.nextDouble()
        print("c: ")
        val c = sc.nextDouble()
        println("normal or inverse proportionality? (n/i)")
        return when (sc.next()[0]) {
            'n' -> {
                val x = b * c / a
                "x = $x (normal proportionality)"
            }
            'i' -> {
                val x = a * b / c
                "x = $x (inverse proportionality)"
            }
            else -> "Err_incorrect_input"
        }
    }

    fun convert(): String {
        val sc = Scanner(System.`in`)
        println("Unit type: (acc angle area data length speed time)")
        val type = sc.next()
        print("number: ")
        val a = sc.nextDouble()
        val x: Double
        val unit: String
        val to: String
        return when (type) {
            "acc" -> {
                println("unit: (m/s^2 ft/s^2 g cm/s^2)")
                unit = sc.next()
                when (unit) {
                    "m/s^2" -> {
                        println("convert to: (ft/s^2 g cm/s^2)")
                        to = sc.next()
                        x = when (to) {
                            "ft/s^2" -> a / 0.3048
                            "g" -> a / 9.8066
                            "cm/s^2" -> a / 0.01
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "ft/s^2" -> {
                        println("convert to: (m/s^2 g cm/s^2)")
                        to = sc.next()
                        x = when (to) {
                            "m/s^2" -> a / 3.2808
                            "g" -> a / 32.174
                            "cm/s^2" -> a / 0.0328
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "g" -> {
                        println("convert to: (m/s^2 ft/s^2 cm/s^2)")
                        to = sc.next()
                        x = when (to) {
                            "m/s^2" -> a * 9.0866
                            "ft/s^2" -> a * 32.174
                            "cm/s^2" -> a * 980.665
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "cm/s^2" -> {
                        println("convert to: (m/s^2 ft/s^2 g)")
                        to = sc.next()
                        x = when (to) {
                            "m/s^2" -> a / 100
                            "ft/s^2" -> a / 30.48
                            "g" -> a / 980.665
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "angle" -> {
                println("unit: (rad deg min sec)")
                unit = sc.next()
                when (unit) {
                    "rad" -> {
                        println("convert to: (deg min sec)")
                        to = sc.next()
                        x = when (to) {
                            "deg" -> a * 57.2958
                            "min" -> a * 3437.7468
                            "sec" -> a * 206264.8062
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "deg" -> {
                        println("convert to: (rad min sec)")
                        to = sc.next()
                        x = when (to) {
                            "rad" -> a / 57.2958
                            "min" -> a * 60
                            "sec" -> a * 3600
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "min" -> {
                        println("convert to: (rad deg sec)")
                        to = sc.next()
                        x = when (to) {
                            "rad" -> a / 3437.7468
                            "deg" -> a / 60
                            "sec" -> a * 60
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "sec" -> {
                        println("convert to: (rad deg min)")
                        to = sc.next()
                        x = when (to) {
                            "rad" -> a / 206264.8062
                            "deg" -> a * 3600
                            "min" -> a * 60
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "area" -> {
                println("unit: (m^2 km^2 ft^2 yd^2 mi^2)")
                unit = sc.next()
                when (unit) {
                    "m^2" -> {
                        println("convert to: (km^2 ft^2 yd^2 mi^2)")
                        to = sc.next()
                        x = when (to) {
                            "km^2" -> a / 1000000
                            "ft^2" -> a / 0.0929
                            "yd^2" -> a / 0.8361
                            "mi^2" -> a / 2589988.1103
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "km^2" -> {
                        println("convert to: (m^2 ft^2 yd^2 mi^2)")
                        to = sc.next()
                        x = when (to) {
                            "m^2" -> a * 1000000
                            "ft^2" -> a * 10763910.4167
                            "yd^2" -> a * 1195990.0463
                            "mi^2" -> a / 2.59
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "ft^2" -> {
                        println("convert to: (m^2 km^2 yd^2 mi^2)")
                        to = sc.next()
                        x = when (to) {
                            "m^2" -> a / 10.7639
                            "km^2" -> a / 10763910.4167
                            "yd^2" -> a / 9
                            "mi^2" -> a / 27878400
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "yd^2" -> {
                        println("convert to: (m^2 km^2 ft^2 mi^2)")
                        to = sc.next()
                        x = when (to) {
                            "m^2" -> a / 1.196
                            "km^2" -> a / 1195990.0463
                            "ft^2" -> a * 9
                            "mi^2" -> a / 3097600
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "mi^2" -> {
                        println("convert to: (m^2 km^2 ft^2 yd^2)")
                        to = sc.next()
                        x = when (to) {
                            "m^2" -> a * 2589988.1103
                            "km^2" -> a / 0.3861
                            "ft^2" -> a * 27878400
                            "yd^2" -> a * 3097600
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "data" -> {
                println("unit: (Mb Gb MB GB)")
                unit = sc.next()
                when (unit) {
                    "Mb" -> {
                        println("convert to: (Gb MB GB)")
                        to = sc.next()
                        x = when (to) {
                            "Gb" -> a / 1000
                            "MB" -> a / 8
                            "GB" -> a / 8000
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "Gb" -> {
                        println("convert to: (Mb MB GB)")
                        to = sc.next()
                        x = when (to) {
                            "Mb" -> a * 1000
                            "MB" -> a * 125
                            "GB" -> a / 8
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "MB" -> {
                        println("convert to: (Mb Gb GB)")
                        to = sc.next()
                        x = when (to) {
                            "Mb" -> a * 8
                            "Gb" -> a / 125
                            "GB" -> a / 1000
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "GB" -> {
                        println("convert to: (Mb Gb MB)")
                        to = sc.next()
                        x = when (to) {
                            "Mb" -> a * 8000
                            "Gb" -> a * 8
                            "MB" -> a * 1000
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "length" -> {
                println("unit: (m km in ft mi)")
                unit = sc.next()
                when (unit) {
                    "m" -> {
                        println("convert to: (km in ft mi)")
                        to = sc.next()
                        x = when (to) {
                            "km" -> a / 1000
                            "in" -> a * 39.3701
                            "ft" -> a * 3.2808
                            "mi" -> a / 1609.344
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "km" -> {
                        println("convert to: (m in ft mi)")
                        to = sc.next()
                        x = when (to) {
                            "m" -> a * 1000
                            "in" -> a * 39370.0787
                            "ft" -> a * 3280.8399
                            "mi" -> a / 1.6093
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "in" -> {
                        println("convert to: (m km ft mi)")
                        to = sc.next()
                        x = when (to) {
                            "m" -> a / 39.3701
                            "km" -> a / 39370.0787
                            "ft" -> a / 12
                            "mi" -> a / 63360
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "ft" -> {
                        println("convert to: (m km in mi)")
                        to = sc.next()
                        x = when (to) {
                            "m" -> a / 3.2808
                            "km" -> a / 3280.8399
                            "in" -> a * 12
                            "mi" -> a / 5280
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "mi" -> {
                        println("convert to: (m km in ft)")
                        to = sc.next()
                        x = when (to) {
                            "m" -> a * 1609.344
                            "km" -> a * 1.6093
                            "in" -> a * 63360
                            "ft" -> a * 5280
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "speed" -> {
                println("unit: (m/s km/h ft/s mi/h)")
                unit = sc.next()
                when (unit) {
                    "m/s" -> {
                        println("convert to: (km/h ft/s mi/h)")
                        to = sc.next()
                        x = when (to) {
                            "km/h" -> a * 3.6
                            "ft/s" -> a * 3.2808
                            "mi/h" -> a * 2.2369
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "km/h" -> {
                        println("convert to: (m/s ft/s mi/h)")
                        to = sc.next()
                        x = when (to) {
                            "m/s" -> a / 3.6
                            "ft/s" -> a / 1.0973
                            "mi/h" -> a / 1.6093
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "ft/s" -> {
                        println("convert to: (m/s km/h mi/h)")
                        to = sc.next()
                        x = when (to) {
                            "m/s" -> a / 3.2808
                            "km/h" -> a * 1.0973
                            "mi/h" -> a / 1.4667
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "mi/h" -> {
                        println("convert to: (m/s km/h ft/s)")
                        to = sc.next()
                        x = when (to) {
                            "m/s" -> a / 2.2369
                            "km/h" -> a * 1.6093
                            "ft/s" -> a * 1.4667
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            "time" -> {
                println("unit: (ms sec min hr)")
                unit = sc.next()
                when (unit) {
                    "ms" -> {
                        println("convert to: (sec min hr)")
                        to = sc.next()
                        x = when (to) {
                            "sec" -> a / 1000
                            "min" -> a / 60000
                            "hr" -> a / 3600000
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "sec" -> {
                        println("convert to: (ms min hr)")
                        to = sc.next()
                        x = when (to) {
                            "ms" -> a * 1000
                            "min" -> a / 60
                            "hr" -> a / 3600
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "min" -> {
                        println("convert to: (ms sec hr)")
                        to = sc.next()
                        x = when (to) {
                            "ms" -> a * 60000
                            "sec" -> a * 60
                            "hr" -> a / 60
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    "hr" -> {
                        println("convert to: (ms sec min)")
                        to = sc.next()
                        x = when (to) {
                            "ms" -> a * 3600000
                            "sec" -> a * 3600
                            "min" -> a * 60
                            else -> {
                                return "Err_incorrect_input"
                            }
                        }
                    }

                    else -> {
                        return "Err_incorrect_input"
                    }
                }
                "$a $unit = $x $to"
            }

            else -> {
                "Err_incorrect_input"
            }
        }
    }

    fun bodies(): String {
        val sc = Scanner(System.`in`)
        println("Body type: (prism cube)")
        val functionName = sc.next() // Replace with the name of the function you want to execute
        return try {
            val clazz: Class<*> =
                Adv::class.java // Replace with the class name where your functions are defined
            val method = clazz.getDeclaredMethod(functionName)
            method.invoke(clazz.getDeclaredConstructor().newInstance()) as String
        } catch (e: Exception) {
            e.toString()
        }
    }
}