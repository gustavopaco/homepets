using System;

namespace HomePets.Domain
{
    public class ServiceContractModel
    {
        public int id { get; set; }

        public int idPet { get; set; }
        public int idPrestador { get; set; }
        public int idTomador { get; set; }
        public int idServico { get; set; }


        public string nomePet { get; set; }
        public string nomePrestador { get; set; }
        public string nomeTomador { get; set; }
        public string nomeServico { get; set; }

        public double preco { get; set; }
        public int tipoPreco { get; set; }
        public double valorTotal { get; set; }
        public DateTime dataExecucao { get; set; }
    }
}
