using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class UsuarioRep : Repositorio<Usuario>, IUsuarioRep
    {
        public UsuarioRep(EFContext context) : base(context)
        {

        }

    }
}
