using System;

namespace HomePets.Domain
{
    public class Usuario
    {
        public Usuario()
        {

        }


        public int UsuarioId { get; set; }
        public string Login { get; set; }
        public string Senha { get; set; }
        public string Nome { get; set; }
    }
}
