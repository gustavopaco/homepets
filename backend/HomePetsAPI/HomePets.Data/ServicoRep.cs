using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class ServicoRep : Repositorio<Servico>, IServicoRep
    {
        public ServicoRep(EFContext context) : base(context)
        {

        }

    }
}
