using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HomePets.Domain
{
    public class Pet : PersistentData
    {
        public Pet()
        {

        }

        [Column("PetId")]
        public override int Id { get; set; }

        public string Nome { get; set; }

        public string Raca { get; set; }

        public string Sexo { get; set; }

        public int Tipo { get; set; }


        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; }
    }
}
