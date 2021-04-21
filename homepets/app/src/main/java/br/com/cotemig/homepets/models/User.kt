package br.com.cotemig.homepets.models

open class User{

    protected var email: String = ""
    protected var senha: String = ""

    fun getmail() : String{
        return email
    }

    fun setmail(s: String){
        email = s
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (email != other.email) return false
        if (senha != other.senha) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + senha.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(email='$email', senha='$senha')"
    }
}