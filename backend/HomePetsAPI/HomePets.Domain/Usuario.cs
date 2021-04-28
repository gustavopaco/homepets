using System;

namespace HomePets.Domain
{
    public class Usuario : PersistentData
    {
        public Usuario()
        {

        }

        [Column("UsuarioId")]
        public override int Id { get; set; }
        public string Email { get; set; }
        public string Senha { get; set; }
        public string Nome { get; set; }
        public TipoUsuario Tipo { get; set; }
    }
}
