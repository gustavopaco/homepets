using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace HomePets.Domain
{
    public class ServicoContratado : PersistentData
    {
        public ServicoContratado()
        {

        }

        [Column("ServicoContratadoId")]
        public override int Id { get; set; }


        public double ValorTotal { get; set; }


        public DateTime DataExecucao { get; set; }


        public int ServicoId { get; set; }
        public Servico Servico { get; set; }


        public int PetId { get; set; }
        public Pet Pet { get; set; }


        public int UsuarioDonoPetId { get; set; }
        public Usuario UsuarioDonoPet { get; set; }
    }
}
