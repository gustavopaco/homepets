using System;

namespace HomePets.Domain
{
    public class RegisterModel
    {
        public string cpf { get; set; }
        public string nome { get; set; }
        public string telefone { get; set; }
        public string email { get; set; }
        public string password { get; set; }

        public string datanascimento { get; set; }
        public string genero { get; set; }
    }

}
