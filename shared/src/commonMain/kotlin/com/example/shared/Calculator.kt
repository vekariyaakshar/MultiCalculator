package com.example.shared

class Calculator {
    fun add(left: Int, right: Int): Int {
        return left + right
    }

    fun subtract(left: Int, right: Int): Int {
        return left - right
    }

    fun multiply(left: Int, right: Int): Int {
        return left * right
    }

    fun divide(left: Int, right: Int): Int {
        if (right == 0) throw IllegalArgumentException("Division by zero is not allowed")
        return left / right
    }
}