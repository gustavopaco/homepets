using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HomePets.Domain
{
    public class Servico : PersistentData
    {
        public Servico()
        {

        }

        [Column("ServicoId")]
        public override int Id { get; set; }

        public string Nome { get; set; }

        public double Preco { get; set; }
        
        public int TipoPreco { get; set; }



        public int UsuarioId { get; set; }
        public Usuario Usuario { get; set; }
    }
}
