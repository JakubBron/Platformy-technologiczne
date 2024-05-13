using System;
using System.Collections.Generic;
using System.Text;

namespace Lab10
{
    public class Engine : IComparable
    {
        public double displacement { get; set; }
        public double horsePower { get; set; }
        public string model { get; set; }

        public Engine()
        {

        }

        public Engine(double Displacement, double HorsePower, string Model)
        {
            displacement = Displacement;
            horsePower = HorsePower;
            model = Model;
        }

        public int CompareTo(object obj)
        {
            return horsePower.CompareTo(obj);
        }

        public override string ToString()
        {
            return $"{model},  {horsePower}, {displacement}";
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            Engine otherEngine = (Engine)obj;
            return (displacement == otherEngine.displacement && horsePower == otherEngine.horsePower && model == otherEngine.model);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                int hash = 17;
                hash = hash * 23 + displacement.GetHashCode();
                hash = hash * 23 + horsePower.GetHashCode();
                hash = hash * 23 + (model != null ? model.GetHashCode() : 0);
                return hash;
            }
        }
    }
}
