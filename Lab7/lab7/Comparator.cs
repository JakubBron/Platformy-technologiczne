using System;
using System.Collections.Generic;
using System.Text;

namespace Lab7
{
    [Serializable]
    public class Comparator : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            if (x.Length == y.Length)
            {
                return string.Compare(x, y, StringComparison.Ordinal);
            }
            return x.Length > y.Length ? 1 : -1;
        }
    }
}
