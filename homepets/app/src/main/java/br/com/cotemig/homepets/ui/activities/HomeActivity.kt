package br.com.cotemig.homepets.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.cotemig.homepets.R
import br.com.cotemig.homepets.models.Pessoa
import br.com.cotemig.homepets.models.User

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    var p = Pessoa()
        p.nome = "Gustavo"
        p.cpf = "107.775.376-48"
        Toast.makeText(this,"Email: " + p.getmail(),Toast.LENGTH_LONG).show()
        Toast.makeText(this,"Nome: " + p.nome + "\nCPF: " + p.cpf,Toast.LENGTH_LONG).show()

        p.setmail("gustavopaco@gmail.com")
        Toast.makeText(this,"Nome: " + p.getmail(),Toast.LENGTH_LONG).show()




    }
}