using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace HomePets.Data
{
    public class ServicoRep : Repositorio<Servico>, IServicoRep
    {
        public ServicoRep(EFContext context) : base(context)
        {

        }

        public Servico ObterServico(int Id)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Id == Id)
                .Select(s => new
                {
                    s,
                }).SingleOrDefault()?.s;
        }


        public IEnumerable<Servico> ObterServicos(int UsuarioId)
        {
            return AsQueryable().Where(p => !p.Deleted && p.UsuarioId == UsuarioId)
                .Select(s => new
                {
                    s,
                }).ToList().Select(s => s.s);

        }


        public IEnumerable<Servico> ObterServicosDisponiveis(string query)
        {
            return AsQueryable().Where(p => !p.Deleted && p.Nome.Contains(query))
                .Select(s => new
                {
                    s,
                    s.Usuario,
                }).ToList().Select(s => s.s);

        }
    }
}
