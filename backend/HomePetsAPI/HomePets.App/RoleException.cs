﻿using System;
using System.Collections.Generic;
using System.Runtime.Serialization;
using System.Security.Permissions;

namespace HomePets.App
{
    [Serializable]
    public class RoleException : Exception
    {
        private readonly string resourceName;
        private readonly IList<string> validationErrors;

        public RoleException()
        {

        }

        public RoleException(string message) : base(message)
        {

        }

        public RoleException(string message, Exception innerException)
            : base(message, innerException)
        {
        }

        public RoleException(string message, string resourceName, IList<string> validationErrors)
            : base(message)
        {
            this.resourceName = resourceName;
            this.validationErrors = validationErrors;
        }

        public RoleException(string message, string resourceName, IList<string> validationErrors, Exception innerException)
            : base(message, innerException)
        {
            this.resourceName = resourceName;
            this.validationErrors = validationErrors;
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        // Constructor should be protected for unsealed classes, private for sealed classes.
        // (The Serializer invokes this constructor through reflection, so it can be private)
        protected RoleException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
            this.resourceName = info.GetString("ResourceName");
            this.validationErrors = (IList<string>)info.GetValue("ValidationErrors", typeof(IList<string>));
        }

        public string ResourceName
        {
            get { return this.resourceName; }
        }

        public IList<string> ValidationErrors
        {
            get { return this.validationErrors; }
        }

        [SecurityPermissionAttribute(SecurityAction.Demand, SerializationFormatter = true)]
        public override void GetObjectData(SerializationInfo info, StreamingContext context)
        {
            if (info == null)
            {
                throw new ArgumentNullException("info");
            }

            info.AddValue("ResourceName", this.ResourceName);

            // Note: if "List<T>" isn't serializable you may need to work out another
            //       method of adding your list, this is just for show...
            info.AddValue("ValidationErrors", this.ValidationErrors, typeof(IList<string>));

            // MUST call through to the base class to let it save its own state
            base.GetObjectData(info, context);
        }

    }
}
