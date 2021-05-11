using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;


namespace HomePets.Data
{
    public class PetRep : Repositorio<Pet>, IPetRep
    {
        public PetRep(EFContext context) : base(context)
        {

        }

    }
}
