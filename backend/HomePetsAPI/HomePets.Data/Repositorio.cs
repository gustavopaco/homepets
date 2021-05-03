using HomePets.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Text;
using System.Threading;
using System.Threading.Tasks;



namespace HomePets.Data
{
    public abstract class Repositorio<TData> : IRepositorio<TData>
        where TData : PersistentData, new()
    {
        //TODO : enviar para classe PersistentDataExpressions
        private Expression<Func<TData, bool>> WhereNotDeleted => p => !p.Deleted;


        #region Construtor

        protected readonly EFContext _context;

        public Repositorio(EFContext context)
        {
            _context = context;
        }

        #endregion

        #region Crud

        public void Add(TData entity)
        {
            _context.Set<TData>().Add(entity);
        }
        public void AddRange(IEnumerable<TData> entities)
        {
            _context.Set<TData>().AddRange(entities);
        }

        public void Attach(TData entity)
        {
            _context.Set<TData>().Attach(entity);
        }

        public void Remove(TData entity)
        {
            _context.Set<TData>().Remove(entity);
        }

        //public IEnumerable<TData> RemoveRange(IEnumerable<TData> entities)
        //{
        //    return _context.Set<TData>().RemoveRange(entities);
        //}

        #endregion

        #region Querys

        //public DbEntityEntry Entry(object entity)
        //{

        //}

        //public DbEntityEntry<TData> Entry<TData>(TData entity) where TData : class
        //{

        //}

        //public void Entry()
        //{
        //    _context.Entry
        //}


        public IQueryable<TData> Where(IQueryable<TData> query, params Expression<Func<TData, bool>>[] wheres)
        {
            foreach (var where in wheres.Where(w => w != null))
            {
                query = query.Where(where);
            }

            return query;
        }


        public IQueryable<TData> AsQueryable(bool FilterDeleted = true) //o padrão será exibir somente os não excluidos.
        {
            if (FilterDeleted)
                return _context.Set<TData>().Where(WhereNotDeleted).AsQueryable();
            else
                return _context.Set<TData>().AsQueryable();
        }

        public TData Find(params object[] keyValues)
        {
            return _context.Set<TData>().Find(keyValues);
        }

        public Task<TData> FindAsync(System.Threading.CancellationToken cancellationToken, params object[] keyValues)
        {
            return _context.Set<TData>().FindAsync(cancellationToken, keyValues);
        }

        public Task<TData> FindAsync(params object[] keyValues)
        {
            return _context.Set<TData>().FindAsync(keyValues);
        }

        public IEnumerable<TData> WhereWithInclude(System.Linq.Expressions.Expression<Func<TData, bool>> where, params System.Linq.Expressions.Expression<Func<TData, object>>[] includeProperties)
        {
            IQueryable<TData> Set = _context.Set<TData>();

            foreach (var include in includeProperties)
                Set = Set.Include(include);

            return Set.Where(where);
        }

        public TData FirstWithInclude(System.Linq.Expressions.Expression<Func<TData, bool>> where, params System.Linq.Expressions.Expression<Func<TData, object>>[] includeProperties)
        {
            IQueryable<TData> Set = _context.Set<TData>();

            foreach (var include in includeProperties)
                Set = Set.Include(include);

            return Set.Where(where).First();
        }

        public TData FirstOrDefaultWithInclude(System.Linq.Expressions.Expression<Func<TData, bool>> where, params System.Linq.Expressions.Expression<Func<TData, object>>[] includeProperties)
        {
            IQueryable<TData> Set = _context.Set<TData>();

            foreach (var include in includeProperties)
                Set = Set.Include(include);

            return Set.Where(where).FirstOrDefault();
        }

        public TData SingleWithInclude(System.Linq.Expressions.Expression<Func<TData, bool>> where, params System.Linq.Expressions.Expression<Func<TData, object>>[] includeProperties)
        {
            IQueryable<TData> Set = _context.Set<TData>();

            foreach (var include in includeProperties)
                Set = Set.Include(include);

            return Set.Single(where);
        }

        public TData SingleOrDefaultWithInclude(System.Linq.Expressions.Expression<Func<TData, bool>> where, params System.Linq.Expressions.Expression<Func<TData, object>>[] includeProperties)
        {
            IQueryable<TData> Set = _context.Set<TData>();

            foreach (var include in includeProperties)
                Set = Set.Include(include);

            return Set.SingleOrDefault(where);
        }
        #endregion


        //#region Dipose

        //public void Dispose()
        //{
        //    if (_context != null)
        //        _context.Dispose();
        //}

        //#endregion

    }
}
