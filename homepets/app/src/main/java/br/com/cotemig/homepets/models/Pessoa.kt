package br.com.cotemig.homepets.models

   class Pessoa : User(){
       private var nome: String = ""
       private var cpf: String = ""


       fun getNome() : String{
           return nome
       }

       fun setNome(nome : String){
           this.nome = nome
       }

       fun getCpf() : String{
           return cpf
       }

       fun setCpf(cpf : String){
           this.cpf = cpf
       }

       override fun equals(other: Any?): Boolean {
           if (this === other) return true
           if (other !is Pessoa) return false
           if (!super.equals(other)) return false

           if (nome != other.nome) return false
           if (cpf != other.cpf) return false

           return true
       }

       override fun hashCode(): Int {
           var result = super.hashCode()
           result = 31 * result + nome.hashCode()
           result = 31 * result + cpf.hashCode()
           return result
       }

       override fun toString(): String {
           return "Pessoa(nome='$nome', cpf='$cpf')"
       }


   }





