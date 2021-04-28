using System;

namespace HomePets.Domain
{
    public interface IRepositorio<TEntity> where TEntity : PersistentData
    {
        void Add(TEntity entity);



        TEntity FirstWithInclude(Expression<Func<TEntity, bool>> where, params Expression<Func<TEntity, object>>[] includeProperties);
        TEntity SingleWithInclude(Expression<Func<TEntity, bool>> where, params Expression<Func<TEntity, object>>[] includeProperties);

        TEntity FirstOrDefaultWithInclude(Expression<Func<TEntity, bool>> where, params Expression<Func<TEntity, object>>[] includeProperties);
        TEntity SingleOrDefaultWithInclude(Expression<Func<TEntity, bool>> where, params Expression<Func<TEntity, object>>[] includeProperties);





        void AddRange(IEnumerable<TEntity> entities);
        void Attach(TEntity entity);
        void Remove(TEntity entity);
        IEnumerable<TEntity> RemoveRange(IEnumerable<TEntity> entities);


        IEnumerable<TEntity> WhereWithInclude(Expression<Func<TEntity, bool>> where, params System.Linq.Expressions.Expression<Func<TEntity, object>>[] includeProperties);
        TEntity Find(params object[] keyValues);
        Task<TEntity> FindAsync(params object[] keyValues);
        Task<TEntity> FindAsync(CancellationToken cancellationToken, params object[] keyValues);


        IQueryable<TEntity> AsQueryable(bool FilterDeleted = true);

    }

}