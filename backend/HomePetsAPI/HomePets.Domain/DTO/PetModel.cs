﻿using System;

namespace HomePets.Domain
{
    public class PetModel
    {
        public int id { get; set; }
        public string nome { get; set; }
        public string raca { get; set; }
        public string sexo { get; set; }
        public int tipoPet { get; set; }

    }
}
