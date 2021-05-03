using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HomePets.Domain
{
    public class Usuario : PersistentData
    {
        public Usuario()
        {

        }

        [Column("UsuarioId")]
        public override int Id { get; set; }
        
        [StringLength(200)]
        public string Email { get; set; }
        public string Senha { get; set; }
        public string Nome { get; set; }
        public TipoUsuario Tipo { get; set; }
    }
}
