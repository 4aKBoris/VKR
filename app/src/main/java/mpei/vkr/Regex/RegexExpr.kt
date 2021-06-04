@file:Suppress("PackageName")

package mpei.vkr.Regex

internal class RegexExpr {
    val regLittle = Regex("[a-z]+")
    val regBig = Regex("[A-Z]+")
    val regSpecial = Regex("[!@#$%^&*]+")
}