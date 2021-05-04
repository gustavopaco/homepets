using System;

namespace HomePets.Domain
{
    public class ServiceModel
    {
        public string email { get; set; }
        public int id { get; set; }
        public string nomeServico { get; set; }
        public double preco { get; set; }
        public int tipoPreco { get; set; }

    }
}
