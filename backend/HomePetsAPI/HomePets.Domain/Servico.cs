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
    }
}
