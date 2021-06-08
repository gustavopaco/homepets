using System;

namespace HomePets.Domain
{
    public class ServiceDescriptionModel
    {
        public int id { get; set; }
        public string nomeServico { get; set; }
        public double preco { get; set; }
        public int tipoPreco { get; set; }

        public string nomePrestador { get; set; }
    }
}
