using System;
using System.Collections.Generic;

namespace HomePets.Domain
{
    public interface IPetRep : IRepositorio<Pet>
    {
        Pet ObterPet(int Id);
        IEnumerable<Pet> ObterPets(int UsuarioId);
    }
}