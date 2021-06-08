using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HomePets.Data
{
    public class ServicoContratadoRep : Repositorio<ServicoContratado>, IServicoContratadoRep
    {
        public ServicoContratadoRep(EFContext context) : base(context)
        {

        }

        public ServicoContratado ObterServicoContratado(int Id)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Id == Id)
                .Select(s => new
                {
                    s,
                    s.Pet,
                    s.Servico,
                    s.Servico.Usuario,
                    s.UsuarioDonoPet,
                }).SingleOrDefault()?.s;
        }


        public IEnumerable<ServicoContratado> ObterServicosRelacionados(int UsuarioId)
        {
            return AsQueryable().Where(p => !p.Deleted && (p.UsuarioDonoPetId == UsuarioId || p.Servico.UsuarioId == UsuarioId))
                .Select(s => new
                {
                    s,
                    s.Pet,
                    s.Servico,
                    s.Servico.Usuario,
                    s.UsuarioDonoPet,
                }).AsEnumerable().Select(s => s.s);

        }

    }
}
