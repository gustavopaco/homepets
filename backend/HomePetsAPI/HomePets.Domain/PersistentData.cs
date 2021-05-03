using System;
using System.ComponentModel.DataAnnotations;

namespace HomePets.Domain
{  
    public abstract class PersistentData
    {
        public PersistentData()
        {

        }

        public abstract int Id { get; set; }

        public bool Deleted { get; set; }

        [Timestamp]
        public byte[] Timestamp { get; set; }

    }

}