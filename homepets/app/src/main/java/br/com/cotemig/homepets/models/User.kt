package br.com.cotemig.homepets.models

open class User{

    protected var email: String = ""
    protected var senha: String = ""
    protected var stats: Int = 0

    fun getemail() : String{
        return email
    }

    fun setemail(email: String){
        this.email = email
    }

    fun getsenha() : String{
        return senha
    }

    fun setsenha(senha: String){
        this.senha = senha
    }

    fun getTipoUsuario() : Int{
        return stats
    }

    fun setTipoUsuario(stats : Int){
        this.stats = stats
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (email != other.email) return false
        if (senha != other.senha) return false
        if (stats != other.stats) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + senha.hashCode()
        result = 31 * result + stats
        return result
    }

    override fun toString(): String {
        return "User(email='$email', senha='$senha', stats=$stats)"
    }


}