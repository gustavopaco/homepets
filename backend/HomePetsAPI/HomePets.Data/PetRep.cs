using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;


namespace HomePets.Data
{
    public class PetRep : Repositorio<Pet>, IPetRep
    {
        public PetRep(EFContext context) : base(context)
        {

        }

        public Pet ObterPet(int Id)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Id == Id)
                .Select(s => new
                {
                    s,
                }).SingleOrDefault()?.s;
        }


        public IEnumerable<Pet> ObterPets(int UsuarioId)
        {
            return AsQueryable().Where(p => !p.Deleted && p.UsuarioId == UsuarioId)
                .Select(s => new
                {
                    s,
                }).ToList().Select(s => s.s);

        }
    }
}
